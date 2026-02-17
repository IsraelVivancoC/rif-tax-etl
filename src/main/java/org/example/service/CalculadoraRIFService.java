package org.example.service;

import org.example.model.RangoFiscal;
import java.util.List;

public class CalculadoraRIFService {
    private final TablaBimestralSource tableSource;

    public CalculadoraRIFService(TablaBimestralSource tableSource) {
        this.tableSource = tableSource;
    }

    public double calcularISR(double utilidad, int bimestre) {
        List<RangoFiscal> tabla = tableSource.getRangos(bimestre);

        RangoFiscal rango = tabla.stream()
                .filter(r -> utilidad >= r.getLimiteInferior() && utilidad <= r.getLimiteSuperior())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Utilidad fuera de rango"));

        double excedente = utilidad - rango.getLimiteInferior();
        double impuestoMarginal = excedente * (rango.getPorcentaje() / 100);

        return impuestoMarginal + rango.getCuotaFija();
    }
}