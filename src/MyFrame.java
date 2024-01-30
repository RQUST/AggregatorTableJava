import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setTitle("Диалоговое окно");
        setLayout(new MigLayout("align center"));

        JLabel label = new JLabel("Диалоговое окно");
        add(label, "wrap");

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        setSize(200, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}