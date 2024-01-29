import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class MyFrame extends JFrame {
    public MyFrame() {
        super("My Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout());

        JLabel rowLabel = new JLabel("Rows:");
        JSpinner rowSpinner = new JSpinner();

        JLabel colLabel = new JLabel("Columns:");
        JSpinner colSpinner = new JSpinner();

        JButton button = new JButton("Button");

        add(rowLabel, "cell 0 0");
        add(rowSpinner, "cell 1 0, gapbottom 10"); // Removed "wrap"

        add(colLabel, "cell 0 1, gaptop 10");
        add(colSpinner, "cell 1 1, gapbottom 10"); // Removed "wrap"

        add(button, "cell 0 2 2 1, grow, gapbottom 10"); // Removed "wrap"

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyFrame());
    }
}
