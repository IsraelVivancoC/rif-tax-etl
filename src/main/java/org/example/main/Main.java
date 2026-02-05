package org.example.main;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.service.CalculadoraRIFService; // Importamos el nuevo servicio
import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        // 1. EXTRACT: ruta del archivo
        String excelFilePath = "data_rif.xlsx";

        // Instanciamos nuestro nuevo motor de cálculo basado en el Anexo 8
        CalculadoraRIFService calculadora = new CalculadoraRIFService();

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("\n=== SISTEMA DE PROCESAMIENTO FISCAL RIF (ANEXO 8) ===");
            System.out.printf("%-15s | %-20s | %-12s | %-12s | %-10s%n",
                    "RFC", "CLIENTE", "UTILIDAD", "ISR CAUSADO", "ISR FINAL");
            System.out.println("-----------------------------------------------------------------------------------");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                try {
                    String rfc = row.getCell(0).getStringCellValue();
                    String cliente = row.getCell(1).getStringCellValue();
                    double ingresos = row.getCell(2).getNumericCellValue();
                    double gastos = row.getCell(3).getNumericCellValue();
                    int anio = (int) row.getCell(4).getNumericCellValue();

                    // --- TRANSFORMACIÓN CON LÓGICA REAL ---

                    double utilidad = ingresos - gastos;

                    // 1. Calculamos el ISR Causado usando las tablas del Anexo 8 (Pág. 15 del PDF)
                    double isrCausado = calculadora.calcularISRProvisional(utilidad);

                    // 2. Aplicamos el Beneficio RIF (Estímulo decreciente 10% anual)
                    // Año 1 = 100%, Año 2 = 90% ... Año 10 = 10%
                    double porcentajeReduccion = Math.max(0, 1.10 - (anio * 0.10));
                    double estimuloFiscal = isrCausado * porcentajeReduccion;

                    double isrAPagar = isrCausado - estimuloFiscal;

                    // 3. LOAD: Resultado con precisión contable
                    System.out.printf("%-15s | %-20s | $%-11.2f | $%-11.2f | $%-9.2f%n",
                            rfc, cliente, utilidad, isrCausado, Math.max(0, isrAPagar));

                } catch (Exception e) {
                    System.out.println("Error en fila " + (row.getRowNum() + 1) + ": " + e.getMessage());
                }
            }
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Proceso ETL finalizado: Cálculos realizados según Anexo 8 RMF 2021.");

        } catch (Exception e) {
            System.err.println("CRÍTICO: No se pudo procesar el archivo. Detalles: " + e.getMessage());
        }
    }
}