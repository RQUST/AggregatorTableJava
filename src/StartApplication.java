import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Создаем фрейм
        JFrame frame = new JFrame("Мое приложение");

        // Устанавливаем операцию закрытия фрейма
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем панель меню
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Создаем меню "Файл"
        JMenu fileMenu = new JMenu("Файл");

        // Создаем пункт "Старт" в меню "Файл"
        JMenuItem startMenuItem = new JMenuItem("Старт");
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действие при выборе "Старт"
                JOptionPane.showMessageDialog(frame, "Вы выбрали 'Старт'");
            }
        });

        // Добавляем пункт "Старт" в меню "Файл"
        fileMenu.add(startMenuItem);

        // Добавляем меню "Файл" в панель меню
        menuBar.add(fileMenu);

        // Устанавливаем размеры фрейма
        frame.setSize(400, 300);

        // Делаем фрейм видимым
        frame.setVisible(true);
    }
}
