import javax.swing.table.AbstractTableModel;
import java.util.Arrays;

public class MyTableModel extends AbstractTableModel {
    /*
     * Ключевое слово final в Java применяется к переменным, полям класса, параметрам методов и классам. Оно указывает на то,
     * что значение или состояние этого элемента не может быть изменено после его инициализации или присвоения.*/

    private final Object[][] data;
    private final boolean[] rowEditable;
    private final boolean[] columnEditable;

    private final Aggregator rowAggregator;
    private final Aggregator colAggregator;

    /*
    Java ключевое слово static применяется к переменным и методам класса. Когда поле класса (переменная) объявлено как static,
    это означает, что данное поле принадлежит самому классу, а не каждому отдельному экземпляру этого класса. Это также означает,
    что значение этой переменной общее для всех экземпляров класса.*/

    /// Константы для агрегаторов
    private static final String SUM = "Сумма";
    private static final String COUNT = "Количество";
    private int rowData;
    private int colData;
    private int roundingData;
    private boolean isTopHeader;
    private boolean isLeftHeader;
    private boolean isRightFooter;
    private boolean isBottomFooter;
    private boolean isRoundingCheck;
    private String rightFooterData;
    private String bottomFooterData;
    private int tableStartCol = 0;
    private int tableStartRow = 0;
    private int tableEndCol;
    private int tableEndRow;
    private double[] rowResult;
    private double[] colResult;

    public MyTableModel(int rd, int cd, int rod, boolean ith, boolean ilh, boolean irf, boolean ibf, boolean irc,
                        String rfd, String bfd) {

        rowData = rd;
        colData = cd;
        roundingData = rod;
        isTopHeader = ith;
        isLeftHeader = ilh;
        isRightFooter = irf;
        isBottomFooter = ibf;
        isRoundingCheck = irc;
        rightFooterData = rfd;
        bottomFooterData = bfd;

        rowAggregator = invokeAgg(rfd);
        colAggregator = invokeAgg(bfd);

        configureTable();

        data = new Object[rowData][colData];

        rowResult = new double[colData + 1];
        colResult = new double[rowData + 1];

        tableEndRow = rowData;
        tableEndCol = colData;

        rowEditable = new boolean[getRowCount()];
        columnEditable = new boolean[getColumnCount()];
        Arrays.fill(rowEditable, true);
        Arrays.fill(columnEditable, true);

        updateHeaderText();
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        if (data.length > 0) {
            return data[0].length;
        }
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value;

        updateFooters();

        fireTableDataChanged();
    }

    @Override
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

    public void configureTable() {
        if (isTopHeader) {
            rowData++;
            tableStartRow = 1;
        }
        if (isBottomFooter) {
            rowData++;
            tableEndRow = tableEndRow - 1;
        }
        if (isLeftHeader) {
            colData++;
            tableStartCol = 1;
        }
        if (isRightFooter) {
            colData++;
            tableEndCol = tableEndCol - 1;
        }
    }

    public void updateHeaderText() {
        if (isTopHeader) {
            int n = 1;
            for (int i = tableStartCol; i < colData; i++) {
                data[0][i] = n;
                n++;
            }
            setRowEditable(0, false);
        }
        if (isLeftHeader) {
            int n = 1;
            for (int i = tableStartRow; i < rowData; i++) {
                data[i][0] = n;
                n++;
            }
            setColumnEditable(0, false);
        }
        if (isBottomFooter) {
            data[rowData - 1][0] = "";
            if (isLeftHeader) data[rowData - 1][0] = bottomFooterData;
            setRowEditable(rowData - 1, false);
        }
        if (isRightFooter) {
            data[0][colData - 1] = "";
            if (isTopHeader) data[0][colData - 1] = rightFooterData;
            setColumnEditable(colData - 1, false);
        }
    }

    private Aggregator invokeAgg(String agg) {
        switch (agg) {
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
                return new SquaredSumAgg();
        }
        return null;
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

    private void updateFooters() {

        if (isBottomFooter) {
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

        if (isRightFooter) {
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
