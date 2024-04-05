import javax.swing.*;

public class StartApplication {
    public void StartApp(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Your Application Title");
            TableOptions tableOptions = new TableOptions();
            DataTableModel tableModel = new DataTableModel(5, 4, 2, true,
                    true, true, true, true, "Сумма", "Среднее");
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            // Создаем меню и добавляем пункт "Рассчитать"
            JMenuBar menuBar = new JMenuBar();
            JMenu tableMenu = new JMenu("Таблица");
            JMenuItem calculateMenuItem = new JMenuItem("Рассчитать");
            calculateMenuItem.setEnabled(false); // Изначально дизейблим
            calculateMenuItem.addActionListener(e -> {
                // Здесь добавьте логику для расчета итогов
                // Например, можно вызвать метод расчета из DataTableModel
            });
            tableMenu.add(calculateMenuItem);
            menuBar.add(tableMenu);
            frame.setJMenuBar(menuBar);

            // Добавляем слушатель событий для отслеживания изменений в таблице
            tableModel.addTableModelListener(e -> {
                // Проверяем, есть ли данные в таблице
                if (tableModel.getRowCount() > 0 && tableModel.getColumnCount() > 0) {
                    calculateMenuItem.setEnabled(true); // Если есть, активируем пункт меню
                } else {
                    calculateMenuItem.setEnabled(false); // Иначе дизейблим
                }
            });

            frame.getContentPane().add(scrollPane);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
