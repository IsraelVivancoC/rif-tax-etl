package org.example.main;

import org.example.service.*;
import org.example.util.LectorDatosExcel;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. SETUP: Inicializamos nuestras piezas
            LectorDatosExcel lector = new LectorDatosExcel();

            // Usamos la instancia única (Singleton) de nuestro Enum
            CalculadoraRIFService calc = new CalculadoraRIFService(EnumTablaBimestralSource.INSTANCE);

            // 2. EXTRACT: Leemos todas las pestañas (Bimestre 1, 2, etc.)
            var todosLosDatos = lector.leerTodoElLibro("data_rif.xlsx");

            // 3. HEADER: Encabezado del reporte
            System.out.println("\n=== SISTEMA ETL FISCAL: REPORTE RIF 2021 ===");
            System.out.printf("%-15s | %-5s | %-12s | %-12s | %-10s%n",
                    "RFC", "BIM", "UTILIDAD", "ISR BRUTO", "ISR NETO");
            System.out.println("-".repeat(75));

            // 4. TRANSFORM & LOAD: Procesamos cada fila del Excel
            for (var f : todosLosDatos) {
                double utilidad = f.ingresos() - f.gastos();

                // El bimestre viene determinado por la posición de la pestaña en el Excel
                double isrBruto = calc.calcularISR(utilidad, f.bimestre());

                // Cálculo de reducción RIF según el año de tributación
                double descuento = Math.max(0, 1.10 - (f.anio() * 0.10));
                double isrNeto = isrBruto * (1 - descuento);

                // Imprimimos los resultados con formato de moneda
                System.out.printf("%-15s | %-5d | $%-11.2f | $%-11.2f | $%-9.2f%n",
                        f.rfc(),
                        f.bimestre(),
                        utilidad,
                        isrBruto,
                        Math.max(0, isrNeto));
            }

            System.out.println("-".repeat(75));
            System.out.println("Proceso finalizado con éxito. Datos procesados: " + todosLosDatos.size());

        } catch (Exception e) {
            // Captura errores de archivo no encontrado, formato de Excel o lógica fiscal
            System.err.println("CRÍTICO: " + e.getMessage());
            e.printStackTrace(); // Útil para depurar si algo truena internamente
        }
    }
}