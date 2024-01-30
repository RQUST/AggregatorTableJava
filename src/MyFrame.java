import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setLayout(new MigLayout());

        JLabel rowLabel = new JLabel("Rows:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Columns:");
        JSpinner colSpinner = new JSpinner();

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Добавляем компоненты с использованием MigLayout-констрейнтов
        add(rowLabel, "align trailing");
        add(rowSpinner, "width 300!");
        add(colLabel, "align trailing");
        add(colSpinner, "width 300!, wrap");
        add(closeButton, "span 2, align center");

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Для корректного завершения приложения
        setVisible(true);
    }
}
