package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        // 1. EXTRACT: ruta del archivo
        String excelFilePath = "data_rif.xlsx";

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Accedemos a la primera hoja del Excel
            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("\n=== SISTEMA DE PROCESAMIENTO FISCAL RIF ===");
            System.out.printf("%-15s | %-20s | %-12s | %-10s%n", "RFC", "CLIENTE", "UTILIDAD", "ISR FINAL");
            System.out.println("-----------------------------------------------------------------------");

            // 2. TRANSFORM: Recorremos las filas para procesar los datos
            for (Row row : sheet) {
                // Saltamos el encabezado (fila 0)
                if (row.getRowNum() == 0) continue;

                try {
                    // Extraer datos de las celdas por índice (A=0, B=1, C=2, D=3, E=4)
                    String rfc = row.getCell(0).getStringCellValue();
                    String cliente = row.getCell(1).getStringCellValue();
                    double ingresos = row.getCell(2).getNumericCellValue();
                    double gastos = row.getCell(3).getNumericCellValue();
                    int anio = (int) row.getCell(4).getNumericCellValue();

                    // Lógica de Transformación (Cálculo de Impuestos)
                    double utilidad = ingresos - gastos;
                    double tasaBaseISR = 0.20; // 20% de tasa ejemplo
                    double isrPrevio = utilidad * tasaBaseISR;

                    // Beneficio RIF: Descuento que baja 10% cada año
                    double factorDescuento = Math.max(0, 1.10 - (anio * 0.10));
                    double isrAPagar = isrPrevio * (1 - factorDescuento);

                    // 3. LOAD: Mostramos el resultado formateado en consola
                    System.out.printf("%-15s | %-20s | $%-11.2f | $%-9.2f%n",
                            rfc, cliente, utilidad, Math.max(0, isrAPagar));

                } catch (Exception e) {
                    System.out.println("Error en fila " + (row.getRowNum() + 1) + ": " + e.getMessage());
                }
            }
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Proceso ETL finalizado con éxito.");

        } catch (Exception e) {
            System.err.println("CRÍTICO: No se pudo procesar el archivo. Detalles: " + e.getMessage());
            System.err.println("Asegúrate de que 'data_rif.xlsx' esté en la carpeta raíz y cerrado.");
        }
    }
}