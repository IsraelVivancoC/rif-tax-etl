package org.example.service;

import org.example.model.RangoFiscal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de procesar la lógica fiscal del RIF.
 * Basado en el Anexo 8 de la RMF 2021.
 */
public class CalculadoraRIFService {
    private List<RangoFiscal> tablaBimestre1;

    public CalculadoraRIFService() {
        tablaBimestre1 = new ArrayList<>();

        // Datos extraídos directamente de la imagen del Anexo 8 RMF 2021 (Pág. 15)
        // Formato: limiteInferior, limiteSuperior, cuotaFija, porcentaje
        tablaBimestre1.add(new RangoFiscal(0.01, 1289.16, 0.00, 1.92));
        tablaBimestre1.add(new RangoFiscal(1289.17, 10941.84, 24.76, 6.40));
        tablaBimestre1.add(new RangoFiscal(10941.85, 19229.32, 642.52, 10.88));
        tablaBimestre1.add(new RangoFiscal(19229.33, 22353.24, 1544.20, 16.00));
        tablaBimestre1.add(new RangoFiscal(22353.25, 26762.94, 2044.02, 17.92));
        tablaBimestre1.add(new RangoFiscal(26762.95, 53977.00, 2834.24, 21.36));
        tablaBimestre1.add(new RangoFiscal(53977.01, 85075.16, 8647.16, 23.52));
        tablaBimestre1.add(new RangoFiscal(85075.17, 162422.50, 15961.46, 30.00));
        tablaBimestre1.add(new RangoFiscal(162422.51, 216563.34, 39165.66, 32.00));
        tablaBimestre1.add(new RangoFiscal(216563.35, 649690.02, 56490.72, 34.00));

        // Para el último rango "En adelante", usamos Double.MAX_VALUE como límite superior
        tablaBimestre1.add(new RangoFiscal(649690.03, Double.MAX_VALUE, 203753.80, 35.00));
    }

    /**
     * Calcula el ISR Causado antes de aplicar el estímulo fiscal del RIF.
     * @param utilidad La utilidad fiscal bimestral (Ingresos - Gastos).
     * @return El monto del ISR determinado por la tabla.
     */
    public double calcularISRProvisional(double utilidad) {
        // Validación básica: si no hay utilidad, no hay impuesto
        if (utilidad <= 0) {
            return 0;
        }

        for (RangoFiscal rango : tablaBimestre1) {
            // Buscamos en qué rango cae la utilidad del contribuyente
            if (utilidad >= rango.getLimiteInferior() && utilidad <= rango.getLimiteSuperior()) {

                // Fórmula: ((Utilidad - Límite Inferior) * %) + Cuota Fija
                double excedente = utilidad - rango.getLimiteInferior();
                double impuestoMarginal = excedente * (rango.getPorcentaje() / 100);

                return impuestoMarginal + rango.getCuotaFija();
            }
        }

        return 0;
    }
}