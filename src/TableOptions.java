import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
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

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовков");
        addRowHeaderCheckbox.setSelected(true);

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
        addColHeaderCheckbox.setSelected(true);

        JCheckBox addSummaryRowCheckbox = new JCheckBox("Добавить строку итогов:");
        JComboBox summaryOptionsComboBoxRow = new JComboBox<>(new String[]{
                "Сумма",
                "Количество",
                "Среднее",
                "Максимум",
                "Минимум",
                "Сумма квадратов",
                "Формула"
        });

        JCheckBox addSummaryColumnCheckbox = new JCheckBox("Добавить столбец итогов:");
        JComboBox summaryOptionsComboBoxColumn = new JComboBox<>(new String[]{
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

        /*Выполняется проверка, является ли объект window экземпляром класса Dialog.
        Если это так, то переменной dialog присваивается ссылка на объект window,
        и этот объект можно использовать внутри блока if. Таким образом,
        мы объединяем проверку instanceof и объявление переменной dialog в одной строке.*/
        if (window instanceof Dialog dialog) {
            dialog.dispose();
        }
    }

}
