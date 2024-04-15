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

        updateTableHeadersAndFooters();
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

    public void updateTableHeadersAndFooters() {
        // Обновление верхнего заголовка таблицы
        if (isTopHeader) {
            int n = 1;
            // Проход по каждому столбцу, начиная с tableStartCol и заканчивая colData - 1
            for (int i = tableStartCol; i < colData; i++) {
                // Установка последовательного номера в первой строке каждого столбца
                data[0][i] = n;
                n++;
            }
            // Первая строка становится нередактируемой
            setRowEditable(0, false);
        }

        // Обновление левого заголовка таблицы
        if (isLeftHeader) {
            int n = 1;
            // Проход по каждой строке, начиная с tableStartRow и заканчивая rowData - 1
            for (int i = tableStartRow; i < rowData; i++) {
                // Установка последовательного номера в первом столбце каждой строки
                data[i][0] = n;
                n++;
            }
            // Первый столбец становится нередактируемым
            setColumnEditable(0, false);
        }

        // Обновление нижнего подвала таблицы
        if (isBottomFooter) {
            // Установка пустой строки в последней строке первого столбца
            data[rowData - 1][0] = "";
            // Если также установлен левый заголовок, устанавливается значение bottomFooterData
            if (isLeftHeader) data[rowData - 1][0] = bottomFooterData;
            // Последняя строка становится нередактируемой
            setRowEditable(rowData - 1, false);
        }

        // Обновление правого подвала таблицы
        if (isRightFooter) {
            // Установка пустой строки в последнем столбце первой строки
            data[0][colData - 1] = "";
            // Если также установлен верхний заголовок, устанавливается значение rightFooterData
            if (isTopHeader) data[0][colData - 1] = rightFooterData;
            // Последний столбец становится нередактируемым
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
            case "СКО":
                return new StandardDeviationAgg(); // добавляем агрегатор для стандартного отклонения
        }
        return null;
    }

    private double setColValInAgg(int columnIndex) {
        // Сброс агрегатора перед началом агрегации новых значений
        colAggregator.reset();

        // Проход по строкам таблицы, начиная с tableStartRow и заканчивая rowData - 1
        for (int rowIndex = tableStartRow; rowIndex < rowData - 1; rowIndex++) {
            Object[] row = data[rowIndex];
            // Проверка, что columnIndex не выходит за пределы длины текущей строки и что значение не равно null
            if (columnIndex < row.length && row[columnIndex] != null) {
                // Преобразование значения в double и добавление его в агрегатор
                colAggregator.addValue(objectToDouble(row[columnIndex]));
            }
        }
        // Возвращение результата агрегации для указанного столбца
        return colAggregator.getResult();
    }

    private double setRowValInAgg(int rowIndex) {
        rowAggregator.reset();

        for (int colIndex = tableStartCol; colIndex < colData - 1; colIndex++) {
            Object col = data[rowIndex][colIndex];
            if (col != null) {
                rowAggregator.addValue(objectToDouble(col));
            }
        }
        return rowAggregator.getResult();
    }

    private void updateFooters() {
        // Обновление нижнего подвала таблицы, если флаг isBottomFooter установлен
        if (isBottomFooter) {
            // Вычисление агрегированных значений для каждого столбца и сохранение результатов в массив rowResult
            for (int i = 0; i < rowResult.length; i++) {
                try {
                    rowResult[i] = setColValInAgg(i);
                } catch (Exception ignored) {
                }
            }
            // Применение округления к каждому элементу массива rowResult и сохранение результата в последнюю строку таблицы
            for (int i = tableStartCol; i < tableEndCol; i++) {
                data[tableEndRow - 1][i] = applyRounding(rowResult[i]);
            }
        }

        // Обновление правого подвала таблицы, если флаг isRightFooter установлен
        if (isRightFooter) {
            // Вычисление агрегированных значений для каждой строки и сохранение результатов в массив colResult
            for (int i = 0; i < colResult.length; i++) {
                try {
                    colResult[i] = setRowValInAgg(i);
                } catch (Exception ignored) {
                }
            }
            // Применение округления к каждому элементу массива colResult и сохранение результата в последний столбец таблицы
            for (int i = tableStartRow; i < tableEndRow; i++) {
                data[i][tableEndCol - 1] = applyRounding(colResult[i]);
            }
        }

        // Установка пустой строки в нижний правый угол таблицы
        data[tableEndRow - 1][tableEndCol - 1] = " ";
    }


    private double objectToDouble(Object obj) {
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
