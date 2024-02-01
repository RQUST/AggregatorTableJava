import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    public MyFrame() {
        setLayout(new MigLayout());

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

        JButton insertButton = new JButton("Вставить");
        JButton cancelButton = new JButton("Отмена");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Добавьте здесь код для обработки вставки
                // Например, вызовите метод, который будет выполнять вставку
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрытие диалогового окна при нажатии на "Отмена"
            }
        });

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

        JPanel buttonPanel = new JPanel(new MigLayout("insets 5 5 5 5, wrap 2"));

        buttonPanel.add(insertButton);
        buttonPanel.add(cancelButton);

        panel.add(topPanel, "grow");
        panel.add(midPanel, "grow");
        panel.add(bottomPanel, "grow");
        panel.add(buttonPanel, "align center, span 4");

        add(panel, "wrap, grow");

        setSize(550, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
