import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class MyFrame extends JFrame {
    public MyFrame() {
        super("My Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout());

        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");
        JTextField textField = new JTextField();
        JButton button = new JButton("Button");

        add(label1, "cell 0 0");
        add(textField, "cell 1 0");
        add(label2, "cell 0 1");
        add(button, "cell 1 1");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyFrame());
    }
}
