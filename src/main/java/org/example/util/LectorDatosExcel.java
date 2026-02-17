package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class LectorDatosExcel {

    public List<FilaContribuyente> leerTodoElLibro(String ruta) throws Exception {
        List<FilaContribuyente> listaCompleta = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(ruta));
             Workbook workbook = new XSSFWorkbook(fis)) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                int bimestreActual = i + 1;

                for (Row row : sheet) {
                    // Saltamos encabezado y filas que tengan la primera celda vacía
                    if (row.getRowNum() == 0 || row.getCell(0) == null
                            || row.getCell(0).getCellType() == CellType.BLANK) continue;

                    listaCompleta.add(new FilaContribuyente(
                            getS(row, 0), // RFC
                            getS(row, 1), // Cliente
                            getN(row, 2), // Ingresos
                            getN(row, 3), // Gastos
                            (int) getN(row, 4), // Año
                            bimestreActual
                    ));
                }
            }
        }
        return listaCompleta;
    }

    // Lee texto sin importar si la celda es numérica o string
    private String getS(Row r, int c) {
        Cell cell = r.getCell(c);
        if (cell == null) return "N/A";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    // Lee números de forma segura
    private double getN(Row r, int c) {
        Cell cell = r.getCell(c);
        if (cell == null || cell.getCellType() == CellType.BLANK) return 0.0;

        // Si la celda tiene una fórmula, evaluamos el resultado numérico
        if (cell.getCellType() == CellType.FORMULA) return cell.getNumericCellValue();

        // Si es texto (por error de formato), intentamos convertirlo a double
        if (cell.getCellType() == CellType.STRING) {
            try { return Double.parseDouble(cell.getStringCellValue()); }
            catch (Exception e) { return 0.0; }
        }

        return cell.getNumericCellValue();
    }

    public record FilaContribuyente(String rfc, String nombre, double ingresos,
                                    double gastos, int anio, int bimestre) {}
}