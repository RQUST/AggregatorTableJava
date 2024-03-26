import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class TableOptions extends JPanel {
    private TableOptionsCallback callback;

    // Конструктор класса
    public TableOptions() {
        // Устанавливаем layout в конструкторе
        setLayout(new MigLayout("insets 1 1 1 1, wrap 1, fill", "[]"));
        setSize(550, 300);
        setVisible(true);

        JLabel rowLabel = new JLabel("Строк:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        JSpinner colSpinner = new JSpinner();

        /*
        rowSpinner.setValue(5);
        colSpinner.setValue(4);
        */

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовков");
        addRowHeaderCheckbox.setSelected(true);

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
        addColHeaderCheckbox.setSelected(true);

        JCheckBox addSummaryRowCheckbox = new JCheckBox("Добавить строку итогов:");
        JCheckBox addSummaryColumnCheckbox = new JCheckBox("Добавить столбец итогов:");

        JComboBox<Object> summaryOptionsComboBoxRow = new JComboBox<>();
        JComboBox<Object> summaryOptionsComboBoxColumn = new JComboBox<>();

        summaryOptionsComboBoxRow.setEnabled(false);
        summaryOptionsComboBoxColumn.setEnabled(false);

        DefaultComboBoxModel<Object> summaryOptionsRaw = new DefaultComboBoxModel<>();
        summaryOptionsRaw.addElement("Сумма");
        summaryOptionsRaw.addElement("Количество");
        summaryOptionsRaw.addElement("Среднее");
        summaryOptionsRaw.addElement("Максимум");
        summaryOptionsRaw.addElement("Минимум");
        summaryOptionsRaw.addElement("Сумма квадратов");

        DefaultComboBoxModel<Object> summaryOptionsColumn = new DefaultComboBoxModel<>();
        for (int i = 0; i < summaryOptionsRaw.getSize(); i++) {
            summaryOptionsColumn.addElement(summaryOptionsRaw.getElementAt(i));
        }

        summaryOptionsComboBoxRow.setModel(summaryOptionsRaw);
        summaryOptionsComboBoxColumn.setModel(summaryOptionsColumn);

        JButton insertButton = new JButton("Вставить");
        JButton cancelButton = new JButton("Отмена");

    }

    public interface TableOptionsCallback {
        void onButtonClicked(int rowData, int colData, int roundingData, boolean isTopHeader, boolean isLeftHeader,
                             boolean isSummaryRowCheckbox, boolean isaSummaryColumnCheckbox, boolean isRoundingCheck,
                             String rightFooterData, String bottomFooterData);
    }

    public void setOptionsCallback(TableOptionsCallback callback) {
        this.callback = callback;
    }

    public void closeWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);

        /*Выполняется проверка, является ли объект window экземпляром класса Dialog.
        Если это так, то переменной dialog присваивается ссылка на объект window,
        и этот объект можно использовать внутри блока if. Таким образом,
        мы объединяем проверку instanceof и объявление переменной dialog в одной строке.*/
        if (window instanceof Dialog dialog) {
            dialog.dispose();
        }
    }

}
