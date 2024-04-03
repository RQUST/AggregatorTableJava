import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableOptions extends JPanel {
    private TableOptionsCallback callback;
    private JPanel table_panel_;

    // Конструктор класса
    public TableOptions(JPanel table_panel) {
        table_panel_ = table_panel;
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
                boolean isTopHeader = addRowHeaderCheckbox.isSelected();
                boolean isLeftHeader = addColHeaderCheckbox.isSelected();
                boolean isRightFooter = addSummaryColumnCheckbox.isSelected();
                boolean isBottomFooter = addSummaryRowCheckbox.isSelected();
                boolean isRoundingCheck = false;
                String rightFooterData = (String) summaryOptionsComboBoxRow.getSelectedItem();
                String bottomFooterData = (String) summaryOptionsComboBoxColumn.getSelectedItem();
                callback.onButtonClicked(rowData, colData, roundingData, isTopHeader, isLeftHeader,
                        isRightFooter, isBottomFooter, isRoundingCheck, rightFooterData, bottomFooterData);
                closeWindow();
            }
        });

        cancelButton.addActionListener(e -> closeWindow());

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
        add(insertButton);
        add(cancelButton);
    }

    public interface TableOptionsCallback {
        void onButtonClicked(int rowData, int colData, int roundingData, boolean isTopHeader, boolean isLeftHeader,
                             boolean isRightFooter, boolean isBottomFooter, boolean isRoundingCheck,
                             String rightFooterData, String bottomFooterData);
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
