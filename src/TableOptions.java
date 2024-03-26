import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TableOptions extends JPanel {
    private TableOptionsCallback callback;
    private JSpinner rowSpinner;
    private JSpinner colSpinner;
    private JCheckBox addRowHeaderCheckbox;
    private JCheckBox addColHeaderCheckbox;
    private JCheckBox addSummaryRowCheckbox;
    private JCheckBox addSummaryColumnCheckbox;
    private JComboBox<String> summaryOptionsComboBoxRow;
    private JComboBox<String> summaryOptionsComboBoxColumn;
    private JButton insertButton;
    private JButton cancelButton;


    // Конструктор класса
    public TableOptions() {
        // Устанавливаем layout в конструкторе
        setLayout(new MigLayout("insets 1 1 1 1, wrap 1, fill", "[]"));
        setSize(550, 300);
        setVisible(true);

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
