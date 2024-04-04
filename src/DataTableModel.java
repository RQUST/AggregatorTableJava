import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.Arrays;

public class DataTableModel extends AbstractTableModel {
    private Object[][] data;
    private boolean[] rowEditable;
    private boolean[] columnEditable;
    private int rowData;
    private int colData;
    public int roundingData;
    private boolean isRowHeader;
    private boolean isColHeader;
    private boolean isSummaryColumn;
    private boolean isSummaryRow;
    public boolean isRoundingCheck;
    private String footerRow;
    private String footerColumn;
    private int tableStartCol = 0;
    private int tableStartRow = 0;
    private int tableEndCol;
    private int tableEndRow;
    private Aggregator rowAggregator;
    private Aggregator colAggregator;
    private double[] rowResult;
    private double[] colResult;

    public DataTableModel(int rowData, int colData, int roundingData, boolean isRowHeader, boolean isColHeader,
                      boolean isSummaryColumn, boolean isSummaryRow, boolean isRoundingCheck,
                      String footerRow, String footerColumn) {
        this.rowData = rowData;
        this.colData = colData;
        this.roundingData = roundingData;
        this.isRowHeader = isRowHeader;
        this.isColHeader = isColHeader;
        this.isSummaryColumn = isSummaryColumn;
        this.isSummaryRow = isSummaryRow;
        this.isRoundingCheck = isRoundingCheck;
        this.footerRow = footerRow;
        this.footerColumn = footerColumn;

        rowAggregator = invokeAgg(footerRow);
        colAggregator = invokeAgg(footerColumn);

        configureTable();

        data = new Object[rowData][colData];

        rowResult = new double[colData + 1];
        colResult = new double[rowData + 1];

        tableEndRow = rowData;
        tableEndCol = colData;

        rowEditable = new boolean[getRowCount()];
        columnEditable = new boolean[getColumnCount()];
        // По умолчанию все строки и столбцы редактируемые
        Arrays.fill(rowEditable, true);
        Arrays.fill(columnEditable, true);

        updateHeaderText();
    }

    private Aggregator invokeAgg(String aggregator) {
        switch (aggregator) {
            case "Сумма":
                return new SumAgg();
            case "Количество":
                return new CounterAgg();
            case "Среднее":
                return new AverageAgg();
            case "Максимум":
                return new MaxAgg();
            case "Минимум":
                return new MinAgg();
            case "Сумма квадратов":
                return new SqdSumAgg();
        }
        return null;
    }

    public void configureTable() {
        if (isRowHeader) {
            rowData++;
            tableStartRow = 1;
        }
        if (isColHeader) {
            colData++;
            tableStartCol = 1;
        }
        if (isSummaryRow) {
            rowData++;
            tableEndRow = tableEndRow - 1;
        }
        if (isSummaryColumn) {
            colData++;
            tableEndCol = tableEndCol - 1;
        }
    }

    public void updateHeaderText() {
        if (isRowHeader) {
            int n = 1;
            for (int i = tableStartCol; i < colData; i++) {
                data[0][i] = "Столбец " + n;
                n++;
            }
            setRowEditable(0, false);
        }
        if (isColHeader) {
            int n = 1;
            for (int i = tableStartRow; i < rowData; i++) {
                data[i][0] = "Строка " + n;
                n++;
            }
            setColumnEditable(0, false);
        }
        if (isSummaryRow) {
            data[rowData - 1][0] = "";
            if (isRowHeader) data[rowData - 1][0] = footerRow;
            setRowEditable(rowData - 1, false);
        }
        if (isSummaryColumn) {
            data[0][colData - 1] = "";
            if (isColHeader) data[0][colData - 1] = footerColumn;
            setColumnEditable(colData - 1, false);
        }
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return data[0].length;
    }

    public boolean isCellEditable(int row, int column) {
        return rowEditable[row] && columnEditable[column];
    }

    public void setRowEditable(int rowIndex, boolean editable) {
        if (rowIndex >= 0 && rowIndex < rowEditable.length) {
            rowEditable[rowIndex] = editable;
        }
    }

    public void setColumnEditable(int columnIndex, boolean editable) {
        if (columnIndex >= 0 && columnIndex < columnEditable.length) {
            columnEditable[columnIndex] = editable;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value;
        updateFooters();
        fireTableDataChanged();
    }

    private void updateFooters() {
        if (isSummaryRow) {
            for (int i = 0; i < rowResult.length; i++) {
                try {
                    rowResult[i] = setColValInAgg(i);
                } catch (Exception ignored) {
                }
            }
            for (int i = tableStartCol; i < tableEndCol; i++) {
                data[tableEndRow - 1][i] = applyRounding(rowResult[i]);
            }
        }

        if (isSummaryColumn) {
            for (int i = 0; i < colResult.length; i++) {
                try {
                    colResult[i] = setRowValInAgg(i);
                } catch (Exception ignored) {
                }
            }
            for (int i = tableStartRow; i < tableEndRow; i++) {
                data[i][tableEndCol - 1] = applyRounding(colResult[i]);
            }
        }
        data[tableEndRow - 1][tableEndCol - 1] = " ";
    }

    private double setColValInAgg(int columnIndex) {
        colAggregator.reset();
        for (int rowIndex = tableStartRow; rowIndex < rowData - 1; rowIndex++) {
            Object[] row = data[rowIndex];
            if (columnIndex < row.length && row[columnIndex] != null) {
                colAggregator.addValue(objToDbl(row[columnIndex]));
            }
        }
        return colAggregator.getResult();
    }

    private double setRowValInAgg(int rowIndex) {
        rowAggregator.reset();
        for (int colIndex = tableStartCol; colIndex < colData - 1; colIndex++) {
            Object col = data[rowIndex][colIndex];
            if (col != null) {
                rowAggregator.addValue(objToDbl(col));
            }
        }
        return rowAggregator.getResult();
    }

    private double objToDbl(Object obj) {
        if (obj == null) {
            return 0.0;
        }

        String str = obj.toString();

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private double applyRounding(double value) {
        if (isRoundingCheck) {
            return Math.round(value * Math.pow(10, roundingData)) / Math.pow(10, roundingData);
        } else {
            return value;
        }
    }
}
