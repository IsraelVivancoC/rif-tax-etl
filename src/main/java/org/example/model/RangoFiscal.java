package org.example.model;

public class RangoFiscal {
    private final double limiteInferior;
    private final double limiteSuperior;
    private final double cuotaFija;
    private final double porcentaje;

    public RangoFiscal(double li, double ls, double cf, double p) {
        this.limiteInferior = li;
        this.limiteSuperior = ls;
        this.cuotaFija = cf;
        this.porcentaje = p;
    }

    // Getters
    public double getLimiteInferior() { return limiteInferior; }
    public double getLimiteSuperior() { return limiteSuperior; }
    public double getCuotaFija() { return cuotaFija; }
    public double getPorcentaje() { return porcentaje; }
}