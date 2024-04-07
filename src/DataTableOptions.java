import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class DataTableOptions extends JPanel {

    private OptionsCallback callback;

    public DataTableOptions() {
        super();

        setLayout(new MigLayout("insets 8 8 4 8, wrap 4, fill", "[pref!][230!]0[pref!][fill]", ""));
        setPreferredSize(new Dimension(640, 340));

        JLabel rowLable = new JLabel("Строк: ");
        JLabel colLable = new JLabel("Столбцов: ");
        JLabel roundingText = new JLabel(" знаков после запятой ");

        JCheckBox topHeader = new JCheckBox("Добавить строку заголовков");
        JCheckBox leftHeader = new JCheckBox("Добавить столбец заголовков");
        JCheckBox rightFooter = new JCheckBox("Добавить столбец итогов:");
        JCheckBox bottomFooter = new JCheckBox("Добавить строку итогов:");
        JCheckBox roundingCheck = new JCheckBox("Округлять результат с точностью до ");

        SpinnerModel rowModel = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerModel colModel = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerModel roundingModel = new SpinnerNumberModel(0, 0, 100, 1);

        JSpinner rowValue = new JSpinner(rowModel);
        JSpinner colValue = new JSpinner(colModel);
        JSpinner roundingValue = new JSpinner(roundingModel);

        rowValue.setValue(5);
        colValue.setValue(4);
        roundingValue.setValue(2);
        roundingValue.setEnabled(false);

        JComboBox<Object> rightFooterCombo = new JComboBox<>();
        JComboBox<Object> bottomFooterCombo = new JComboBox<>();

        rightFooterCombo.setEnabled(false);
        bottomFooterCombo.setEnabled(false);

        DefaultComboBoxModel<Object> footerModel = new DefaultComboBoxModel<>();
        footerModel.addElement("Сумма");
        footerModel.addElement("Количество");
        footerModel.addElement("Среднее");
        footerModel.addElement("Максимум");
        footerModel.addElement("Минимум");
        footerModel.addElement("Сумма квадратов");

        DefaultComboBoxModel<Object> footerModel_2 = new DefaultComboBoxModel<>();
        for (int i = 0; i < footerModel.getSize(); i++) {
            footerModel_2.addElement(footerModel.getElementAt(i));
        }

        rightFooterCombo.setModel(footerModel);
        bottomFooterCombo.setModel(footerModel_2);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new MigLayout("nogrid, gap 20"));

        JButton insButton = new JButton("Вставить");
        JButton cancelButton = new JButton("Отмена");

        buttonsPanel.add(insButton);
        buttonsPanel.add(cancelButton);

        insButton.addActionListener(e -> {
            if (callback != null) {
                int rowData = (int) rowValue.getValue();
                int colData = (int) colValue.getValue();
                int roundingData = (int) roundingValue.getValue();
                boolean isTopHeader = topHeader.isSelected();
                boolean isLeftHeader = leftHeader.isSelected();
                boolean isRightFooter = rightFooter.isSelected();
                boolean isBottomFooter = bottomFooter.isSelected();
                boolean isRoundingCheck = roundingCheck.isSelected();
                String rightFooterData = (String) rightFooterCombo.getSelectedItem();
                String bottomFooterData = (String) bottomFooterCombo.getSelectedItem();
                callback.onInsertButtonClicked(rowData, colData, roundingData, isTopHeader, isLeftHeader, isRightFooter,
                        isBottomFooter, isRoundingCheck, rightFooterData, bottomFooterData);
                closeWindow();
            }
        });

        cancelButton.addActionListener(e -> closeWindow());

        roundingCheck.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            roundingValue.setEnabled(isSelected);
        });

        rightFooter.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            rightFooterCombo.setEnabled(isSelected);
        });

        bottomFooter.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            bottomFooterCombo.setEnabled(isSelected);
        });

        add(rowLable);
        add(rowValue);
        add(colLable);
        add(colValue, "growx");

        add(topHeader, "span 2");
        add(leftHeader, "span 2");

        add(bottomFooter, "span 2");
        add(bottomFooterCombo, "span 2, growx");

        add(rightFooter, "span 2");
        add(rightFooterCombo, "span 2, growx");

        JPanel roundingPanel = new JPanel(new MigLayout("insets 0 0 0 0, wrap 3", "[pref!][60][pref!]", ""));
        roundingPanel.add(roundingCheck);
        roundingPanel.add(roundingValue);
        roundingPanel.add(roundingText);
        add(roundingPanel, "span, growx");

        add(new JSeparator(), "span, growx");

        add(buttonsPanel, "span, alignx right");

        setVisible(true);

    }

    public interface OptionsCallback {
        void onInsertButtonClicked(int rowData, int colData, int roundingData, boolean isTopHeader, boolean isLeftHeader,
                                   boolean isRightFooter, boolean isBottomFooter, boolean isRoundingCheck,
                                   String rightFooterData, String bottomFooterData);
    }

    public void setOptionsCallback(OptionsCallback callback) {
        this.callback = callback;
    }

    public void closeWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof Dialog) {
            Dialog dialog = (Dialog) window;
            dialog.dispose();
        }
    }

}
