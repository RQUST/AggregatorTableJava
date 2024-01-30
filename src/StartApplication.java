import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartApplication::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Мое приложение");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout("align center, wrap"));  // Центрирование по горизонтали и перенос на новую строку
        JLabel messageLabel = new JLabel();

        JButton startButton = new JButton("Старт");
        startButton.addActionListener(e -> {
            // Действие при выборе "Старт"
            MyFrame myFrame = new MyFrame();
        });

        panel.add(startButton, "wrap");
        panel.add(messageLabel, "span, grow");
        frame.getContentPane().add(panel);

        // Добавление панели меню
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem startMenuItem = new JMenuItem("Старт");

        startMenuItem.addActionListener(e -> {
            MyFrame myFrame = new MyFrame();
        });

        fileMenu.add(startMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);  // Центрирование по центру экрана
        frame.setVisible(true);
    }
}
