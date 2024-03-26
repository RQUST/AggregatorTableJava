import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartApplication {
    public static void StartApp(String[] args) {
        SwingUtilities.invokeLater(StartApplication::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Мое приложение");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout("align center, wrap"));
        JLabel messageLabel = new JLabel();

        TableOptions tableOptions = new TableOptions();
        tableOptions.setOptionsCallback(new TableOptions.TableOptionsCallback() {
            @Override
            public void onButtonClicked(int rowData, int colData, int roundingData, boolean isTopHeader, boolean isLeftHeader, boolean isRightFooter, boolean isBottomFooter, boolean isRoundingCheck, String rightFooterData, String bottomFooterData) {
                // Ваши действия по обработке нажатия кнопки в TableOptions
            }
        });

        panel.add(tableOptions, "wrap");
        panel.add(messageLabel, "span, grow");

        frame.getContentPane().add(panel);

        // Добавление панели меню
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem startMenuItem = new JMenuItem("Старт");

        startMenuItem.addActionListener(e -> {
            // Действие при выборе "Старт"
            MyFrame myFrame = new MyFrame(new JPanel()); // просто пример, замените пустую панель на вашу логику
        });

        fileMenu.add(startMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);  // Центрирование по центру экрана
        frame.setVisible(true);
    }
}
