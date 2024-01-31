import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    public MyFrame() {
        setLayout(new MigLayout());

//        JPanel panel = new JPanel(new MigLayout());
        JPanel panel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 1, fill", "[]"));

        JLabel rowLabel = new JLabel("Строк:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        JSpinner colSpinner = new JSpinner();

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовков");
        addRowHeaderCheckbox.setSelected(true);

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
        addColHeaderCheckbox.setSelected(true);

        JCheckBox addSummaryRowCheckbox = new JCheckBox("Добавить строку итогов:");
        JCheckBox addSummaryColumnCheckbox = new JCheckBox("Добавить столбец итогов:");

        JComboBox<String> summaryOptionsComboBoxRow = new JComboBox<>(new String[]{
                "Сумма",
                "Количество",
                "Среднее",
                "Максимум",
                "Минимум",
                "Сумма квадратов",
                "Формула"
        });

        JComboBox<String> summaryOptionsComboBoxColumn = new JComboBox<>(new String[]{
                "Сумма",
                "Количество",
                "Среднее",
                "Максимум",
                "Минимум",
                "Сумма квадратов",
                "Формула"
        });

        JTextField formulaTextFieldRow = new JTextField();
        JTextField formulaTextFieldColumn = new JTextField();

        JCheckBox roundResultCheckbox = new JCheckBox("Округлить результат с точностью до");
        JSpinner precisionSpinner = new JSpinner(new SpinnerNumberModel(2, 0, Integer.MAX_VALUE, 1));

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Добавляем компоненты на панель с использованием MigLayout-констрейнтов

        JPanel topPanel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 4, fill", "[][][][]"));

        topPanel.add(rowLabel);
        topPanel.add(rowSpinner, "width 200!");
        topPanel.add(colLabel);
        topPanel.add(colSpinner, "width 200!");

        topPanel.add(addRowHeaderCheckbox, "span 2");
        topPanel.add(addColHeaderCheckbox, "span 2");

        JPanel midPanel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 3, fill", "[pref!][][200!]"));

        midPanel.add(addSummaryRowCheckbox);
        midPanel.add(summaryOptionsComboBoxRow);
        midPanel.add(formulaTextFieldRow, "grow");

        midPanel.add(addSummaryColumnCheckbox);
        midPanel.add(summaryOptionsComboBoxColumn);
        midPanel.add(formulaTextFieldColumn, "grow");

        JPanel bottomPanel = new JPanel(new MigLayout("insets 1 1 1 1, wrap 3, fill", "[][][]"));

        bottomPanel.add(roundResultCheckbox);
        bottomPanel.add(precisionSpinner, "width 50!");
        bottomPanel.add(new JLabel(" знаков после запятой"));

        bottomPanel.add(closeButton, "align center, span 4");

        panel.add(topPanel, "grow");
        panel.add(midPanel, "grow");
        panel.add(bottomPanel, "grow");

        // Добавляем панель на JFrame
        add(panel, "wrap, grow");

        setSize(550, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
