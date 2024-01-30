import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    public MyFrame() {
        setLayout(new MigLayout());

        JPanel panel = new JPanel(new MigLayout());

        JLabel rowLabel = new JLabel("Строк:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        JSpinner colSpinner = new JSpinner();

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку итогов:");
        addRowHeaderCheckbox.setSelected(true);

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец итогов:");
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

        JCheckBox roundResultCheckbox = new JCheckBox("Округлить результат:");
        JSpinner precisionSpinner = new JSpinner(new SpinnerNumberModel(2, 0, Integer.MAX_VALUE, 1));

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Добавляем компоненты на панель с использованием MigLayout-констрейнтов
        panel.add(rowLabel, "align trailing");
        panel.add(rowSpinner, "width 300!");
        panel.add(colLabel, "align trailing");
        panel.add(colSpinner, "width 300!, wrap");
        panel.add(addRowHeaderCheckbox, "span 2");
        panel.add(addColHeaderCheckbox, "span 2, wrap");
        panel.add(addSummaryRowCheckbox, "span 2");
        panel.add(summaryOptionsComboBoxRow);
        panel.add(formulaTextFieldRow, "width 300!, wrap");
        panel.add(addSummaryColumnCheckbox, "span 2");
        panel.add(summaryOptionsComboBoxColumn);
        panel.add(formulaTextFieldColumn, "width 300!, wrap");
        panel.add(roundResultCheckbox, "span 2");
        panel.add(new JLabel("Количество знаков после запятой:"), "align trailing");
        panel.add(precisionSpinner, "width 50!, wrap");

        panel.add(closeButton, "span 2, align center");

        // Добавляем панель на JFrame
        add(panel, "wrap, grow");

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
