import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    private JPanel panel;
    private JTable table;
    private JSpinner rowSpinner;
    private JSpinner colSpinner;
    private JCheckBox addSummaryRowCheckbox;
    private JComboBox<String> summaryOptionsComboBoxRow;
    private JCheckBox addSummaryColumnCheckbox;
    private JComboBox<String> summaryOptionsComboBoxColumn;
    private JPanel table_panel_;

    public MyFrame(JPanel table_panel) {
        setLayout(new MigLayout());
        table_panel_ = table_panel;

        panel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 1, fill", "[]"));

        JLabel rowLabel = new JLabel("Строк:");
        rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        colSpinner = new JSpinner();

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовков");
        addRowHeaderCheckbox.setSelected(true);

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
        addColHeaderCheckbox.setSelected(true);

        addSummaryRowCheckbox = new JCheckBox("Добавить строку итогов:");
        summaryOptionsComboBoxRow = new JComboBox<>(new String[]{
                "Сумма",
                "Количество",
                "Среднее",
                "Максимум",
                "Минимум",
                "Сумма квадратов",
                "Формула"
        });

        addSummaryColumnCheckbox = new JCheckBox("Добавить столбец итогов:");
        summaryOptionsComboBoxColumn = new JComboBox<>(new String[]{
                "Сумма",
                "Количество",
                "Среднее",
                "Максимум",
                "Минимум",
                "Сумма квадратов",
                "Формула"
        });

        JButton insertButton = new JButton("Вставить");
        JButton cancelButton = new JButton("Отмена");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTable();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel topPanel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 4, fill", "[][][][]"));
        topPanel.add(rowLabel);
        topPanel.add(rowSpinner, "width 200!");
        topPanel.add(colLabel);
        topPanel.add(colSpinner, "width 200!");
        topPanel.add(addRowHeaderCheckbox, "span 2");
        topPanel.add(addColHeaderCheckbox, "span 2");

        JPanel firstPanel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 3, fill", "[pref!][][200!]"));
        firstPanel.add(addSummaryRowCheckbox);
        firstPanel.add(summaryOptionsComboBoxRow);
        firstPanel.add(new JTextField(), "grow");
        firstPanel.add(addSummaryColumnCheckbox);
        firstPanel.add(summaryOptionsComboBoxColumn);
        firstPanel.add(new JTextField(), "grow");

        JPanel secondPanel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 3, fill", "[][][]"));
        secondPanel.add(new JCheckBox("Округлить результат с точностью до"));
        secondPanel.add(new JSpinner(new SpinnerNumberModel(2, 0, Integer.MAX_VALUE, 1)), "width 50!");
        secondPanel.add(new JLabel(" знаков после запятой"));

        JPanel buttonPanel = new JPanel(new MigLayout("insets 5 5 5 5, wrap 2", "[][]"));
        buttonPanel.add(insertButton);
        buttonPanel.add(cancelButton);

        panel.add(topPanel, "grow");
        panel.add(firstPanel, "grow");
        panel.add(secondPanel, "grow");
        panel.add(buttonPanel, "align center, span 4");

        add(panel, "wrap, grow");

        setSize(550, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void insertTable() {
        int numRows = (int) rowSpinner.getValue();
        int numCols = (int) colSpinner.getValue();
        String[] columnHeaders = new String[numCols];

        for (int i = 0; i < numCols; i++) {
            columnHeaders[i] = String.valueOf(i + 1);
        }

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, numRows);

        for (int i = 0; i < numRows; i++) {
            tableModel.setValueAt(String.valueOf(i + 1), i, 0);
        }

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table_panel_.add(scrollPane, "grow, span 4");  // Add the table to table_panel_

        setSummaryCells(numRows, numCols);

        table_panel_.revalidate();  // Revalidate table_panel_ instead of the local panel
        table_panel_.repaint();
    }


    private void setSummaryCells(int numRows, int numCols) {
        if (addSummaryRowCheckbox.isSelected()) {
            int summaryRow = numRows;

            String selectedAggregator = summaryOptionsComboBoxRow.getSelectedItem().toString();

            table.setValueAt(selectedAggregator, summaryRow, 0);

            for (int i = 1; i < numCols; i++) {
                table.setValueAt("", summaryRow, i);
                table.getColumnModel().getColumn(i).setCellEditor(null);
            }
        }

        if (addSummaryColumnCheckbox.isSelected()) {
            int summaryCol = numCols;

            String selectedAggregator = summaryOptionsComboBoxColumn.getSelectedItem().toString();

            table.setValueAt(selectedAggregator, 0, summaryCol);

            for (int i = 1; i < numRows; i++) {
                table.setValueAt("", i, summaryCol);
                table.getRowSorter().toggleSortOrder(summaryCol);
            }
        }
    }
}
