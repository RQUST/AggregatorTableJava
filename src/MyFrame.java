import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setLayout(new MigLayout());

        // Создаем панель и устанавливаем для нее MigLayout
        JPanel panel = new JPanel(new MigLayout());

        JLabel rowLabel = new JLabel("Строк:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Столбцов:");
        JSpinner colSpinner = new JSpinner();

        JCheckBox addRowHeaderCheckbox = new JCheckBox("Добавить строку заголовка");
        addRowHeaderCheckbox.setSelected(true); // Установка в выбранное состояние по умолчанию

        JCheckBox addColHeaderCheckbox = new JCheckBox("Добавить столбец заголовков");
        addColHeaderCheckbox.setSelected(true); // Установка в выбранное состояние по умолчанию

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Добавляем компоненты на панель с использованием MigLayout-констрейнтов
        panel.add(rowLabel, "align trailing");
        panel.add(rowSpinner, "width 300!");
        panel.add(colLabel, "align trailing");
        panel.add(colSpinner, "width 300!, wrap");
        panel.add(addRowHeaderCheckbox, "span 2");
        panel.add(addColHeaderCheckbox, "span 2, wrap");
        panel.add(closeButton, "span 2, align center");

        // Добавляем панель на JFrame
        add(panel, "wrap, grow");

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
