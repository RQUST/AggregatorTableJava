import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TableOptions extends JPanel {
    private TableOptionsCallback callback;

    public TableOptions() {
        // Устанавливаем layout в конструкторе
        setLayout(new MigLayout("insets 1 1 1 1, wrap 1, fill", "[]"));
        setSize(550, 300);
        setVisible(true);

        JLabel rowLabel = new JLabel("Строк:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        JSpinner colSpinner = new JSpinner();

        rowSpinner.setValue(5);
        colSpinner.setValue(4);

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовков");
        addRowHeaderCheckbox.setSelected(true);

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
        addColHeaderCheckbox.setSelected(true);

        JCheckBox addSummaryRowCheckbox = new JCheckBox("Добавить строку итогов:");
        JCheckBox addSummaryColumnCheckbox = new JCheckBox("Добавить столбец итогов:");

        JComboBox<String> summaryOptionsComboBoxRow = new JComboBox<>();
        JComboBox<String> summaryOptionsComboBoxColumn = new JComboBox<>();

        summaryOptionsComboBoxRow.setEnabled(false);
        summaryOptionsComboBoxColumn.setEnabled(false);

        DefaultComboBoxModel<String> summaryOptionsRaw = new DefaultComboBoxModel<>();
        summaryOptionsRaw.addElement("Сумма");
        summaryOptionsRaw.addElement("Количество");
        summaryOptionsRaw.addElement("Среднее");
        summaryOptionsRaw.addElement("Максимум");
        summaryOptionsRaw.addElement("Минимум");
        summaryOptionsRaw.addElement("Сумма квадратов");

        DefaultComboBoxModel<String> summaryOptionsColumn = new DefaultComboBoxModel<>();
        for (int i = 0; i < summaryOptionsRaw.getSize(); i++) {
            summaryOptionsColumn.addElement(summaryOptionsRaw.getElementAt(i));
        }

        summaryOptionsComboBoxRow.setModel(summaryOptionsRaw);
        summaryOptionsComboBoxColumn.setModel(summaryOptionsColumn);

        JCheckBox roundingCheck = new JCheckBox("Округлять результат с точностью до ");
        SpinnerModel roundingModel = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner roundingValue = new JSpinner(roundingModel);
        roundingValue.setValue(2);
        roundingValue.setEnabled(false);

        JButton insertButton = new JButton("Вставить");
        JButton cancelButton = new JButton("Отмена");

        insertButton.addActionListener(e -> {
            if (callback != null) {
                int rowData = (int) rowSpinner.getValue();
                int colData = (int) colSpinner.getValue();
                int roundingData = (int) roundingValue.getValue();
                boolean isRowHeader = addRowHeaderCheckbox.isSelected();
                boolean isColHeader = addColHeaderCheckbox.isSelected();
                boolean isSummaryColumn = addSummaryColumnCheckbox.isSelected();
                boolean isSummaryRow = addSummaryRowCheckbox.isSelected();
                boolean isRoundingCheck = roundingCheck.isSelected();
                String footerRow = (String) summaryOptionsComboBoxRow.getSelectedItem();
                String footerColumn = (String) summaryOptionsComboBoxColumn.getSelectedItem();
                callback.onButtonClicked(rowData, colData, roundingData, isRowHeader, isColHeader,
                        isSummaryColumn, isSummaryRow, isRoundingCheck, footerRow, footerColumn);
                closeWindow();
            }
        });

        cancelButton.addActionListener(e -> closeWindow());

        roundingCheck.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            roundingValue.setEnabled(isSelected);
        });

        addSummaryRowCheckbox.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            summaryOptionsComboBoxRow.setEnabled(isSelected);
        });

        addSummaryColumnCheckbox.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            summaryOptionsComboBoxColumn.setEnabled(isSelected);
        });

        add(rowLabel);
        add(rowSpinner);
        add(colLabel);
        add(colSpinner);
        add(addRowHeaderCheckbox);
        add(addColHeaderCheckbox);
        add(addSummaryRowCheckbox);
        add(summaryOptionsComboBoxRow);
        add(addSummaryColumnCheckbox);
        add(summaryOptionsComboBoxColumn);
        add(roundingCheck);
        add(roundingValue);
        add(insertButton);
        add(cancelButton);
    }

    public interface TableOptionsCallback {
        void onButtonClicked(int rowData, int colData, int roundingData, boolean isRowHeader, boolean isColHeader,
                             boolean isSummaryColumn, boolean isSummaryRow, boolean isRoundingCheck,
                             String footerRow, String footerColumn);
    }

    public void setOptionsCallback(TableOptionsCallback callback) {
        this.callback = callback;
    }

    public void closeWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);

        if (window instanceof Dialog dialog) {
            dialog.dispose();
        }
    }
}
