import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class StartApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartApplication::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Мое приложение");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JButton("Старт"), "wrap"); // "wrap" переводит на новую строку
        JLabel messageLabel = new JLabel();

        JButton startButton = new JButton("Старт");
        startButton.addActionListener(e -> {
            // Действие при выборе "Старт"
            MyFrame myFrame = new MyFrame();
            panel.add(myFrame);
        });
        panel.add(startButton, "wrap");

        panel.add(messageLabel, "span, grow");

        frame.getContentPane().add(panel);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
