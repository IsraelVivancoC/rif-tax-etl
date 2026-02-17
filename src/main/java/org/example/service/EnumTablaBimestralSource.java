package org.example.service;

import org.example.model.RangoFiscal;
import java.util.List;

public enum EnumTablaBimestralSource implements TablaBimestralSource {
    INSTANCE;

    @Override
    public List<RangoFiscal> getRangos(int bimestre) {
        return switch (bimestre) {
            case 1 -> List.of(
                    new RangoFiscal(0.01, 1289.16, 0.00, 1.92),
                    new RangoFiscal(1289.17, 10941.84, 24.76, 6.40),
                    new RangoFiscal(10941.85, 19229.32, 642.52, 10.88),
                    new RangoFiscal(19229.33, 22350.62, 1544.70, 16.00),
                    new RangoFiscal(22350.63, 26754.30, 2044.10, 17.92),
                    new RangoFiscal(26754.31, 54020.34, 2833.18, 21.36),
                    new RangoFiscal(54020.35, 85116.30, 8658.04, 23.52),
                    new RangoFiscal(85116.31, 162446.40, 15972.76, 30.00),
                    new RangoFiscal(162446.41, 216595.20, 39171.74, 32.00),
                    new RangoFiscal(216595.21, 649785.60, 56499.34, 34.00),
                    new RangoFiscal(649785.61, 9999999.99, 203784.06, 35.00)
            );
            case 2 -> List.of(
                    new RangoFiscal(0.01, 2578.32, 0.00, 1.92),
                    new RangoFiscal(2578.33, 21883.68, 49.52, 6.40),
                    new RangoFiscal(21883.69, 38458.64, 1285.04, 10.88),
                    new RangoFiscal(38458.65, 44701.24, 3089.40, 16.00),
                    new RangoFiscal(44701.25, 53508.60, 4088.20, 17.92),
                    new RangoFiscal(53508.61, 108040.68, 5666.36, 21.36),
                    new RangoFiscal(108040.69, 170232.60, 17316.08, 23.52),
                    new RangoFiscal(170232.61, 324892.80, 31945.52, 30.00),
                    new RangoFiscal(324892.81, 433190.40, 78343.48, 32.00),
                    new RangoFiscal(433190.41, 1299571.20, 112998.68, 34.00),
                    new RangoFiscal(1299571.21, 9999999.99, 407567.84, 35.00)
            );
            default -> throw new IllegalArgumentException("Bimestre " + bimestre + " no soportado.");
        };
    }
}