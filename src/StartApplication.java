import net.miginfocom.swing.MigLayout;
import javax.swing.*;
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

        JTable table = new JTable();
        JPanel table_panel = new JPanel(new MigLayout("align center, wrap"));
        table_panel.add(new JScrollPane(table)); // Добавляем JScrollPane для JTable

        panel.add(messageLabel, "span, grow");
        panel.add(table_panel);
        frame.getContentPane().add(panel);

        // Добавление панели меню
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem startMenuItem = new JMenuItem("Старт");

        startMenuItem.addActionListener(e -> {
            TableOptions myFrame = new TableOptions();
        });

        fileMenu.add(startMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);  // Центрирование по центру экрана
        frame.setVisible(true);
    }
}
