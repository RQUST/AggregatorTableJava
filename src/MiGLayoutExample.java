import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class MiGLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MiG Layout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JButton("Button 1"));
        panel.add(new JButton("Button 2"));
        panel.add(new JButton("Button 3"));

        frame.getContentPane().add(panel);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
