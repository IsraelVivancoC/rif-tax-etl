package org.example.service;

import org.example.model.RangoFiscal;
import java.util.List;

public interface TablaBimestralSource {
    List<RangoFiscal> getRangos(int bimestre);
}