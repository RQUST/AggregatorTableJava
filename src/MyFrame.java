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
    private JCheckBox addRowHeaderCheckbox;
    private JCheckBox addColHeaderCheckbox;
    private JCheckBox addSummaryRowCheckbox;
    private JComboBox<String> summaryOptionsComboBoxRow;
    private JCheckBox addSummaryColumnCheckbox;
    private JComboBox<String> summaryOptionsComboBoxColumn;
    private JPanel table_panel;

    public MyFrame(JPanel table_panel) {
        setLayout(new MigLayout());
        this.table_panel = table_panel;

        panel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 1, fill", "[]"));

        JLabel rowLabel = new JLabel("Строк:");
        rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        colSpinner = new JSpinner();

        addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовков");
        addRowHeaderCheckbox.setSelected(true);

        addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
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

        // Увеличиваем количество строк и столбцов на 1, если соответствующие чекбоксы выбраны
        if (addRowHeaderCheckbox.isSelected()) {
            numRows++;
        }
        if (addColHeaderCheckbox.isSelected()) {
            numCols++;
        }

        DefaultTableModel tableModel = new DefaultTableModel(numRows, numCols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                boolean rowHeaderSelected = addRowHeaderCheckbox.isSelected();
                boolean colHeaderSelected = addColHeaderCheckbox.isSelected();

                // Клетка (0, 0) неизменяема только если оба чекбокса выбраны
                if (row == 0 && column == 0 && rowHeaderSelected && colHeaderSelected) {
                    return false;
                }

                if (row == 0) {
                    if (rowHeaderSelected && column == 0) {
                        return true;
                    }
                    return !rowHeaderSelected;
                }
                if (column == 0) {
                    if (colHeaderSelected && row == 0) {
                        return true;
                    }
                    return !colHeaderSelected;
                }
                return true;
            }
        };

        table = new JTable(tableModel);
        table.setTableHeader(null);
        JScrollPane scrollPane = new JScrollPane(table);
        table_panel.removeAll(); // Clear previous content
        table_panel.add(scrollPane, "grow, span 4");  // Add the table to table_panel
        setSummaryCells(numRows, numCols);
        table_panel.revalidate();  // Revalidate table_panel instead of the local panel
        table_panel.repaint();

        // Закрываем окно MyFrame после добавления таблицы
        dispose();
    }

    private void setSummaryCells(int numRows, int numCols) {
        // Пропускаем первую строку (заголовки столбцов), если выбран чекбокс
        if (addRowHeaderCheckbox.isSelected()) {
            for (int i = 1; i < numCols; i++) {
                table.setValueAt(String.valueOf(i), 0, i);
            }
        }

        // Пропускаем первый столбец (заголовки строк), если выбран чекбокс
        if (addColHeaderCheckbox.isSelected()) {
            for (int i = 1; i < numRows; i++) {
                table.setValueAt(String.valueOf(i), i, 0);
            }
        }
    }

}
