import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Enumeration;

// Объявление класса MainUi, который расширяет класс JFrame и реализует интерфейс OptionsCallback
public class MainUi extends JFrame implements DataTableOptions.OptionsCallback {
    /*
     * Ключевое слово final в Java используется для обозначения, что переменная,
     * метод или класс не может быть изменен или переопределен после инициализации.
     * */
    // Создание экземпляра окна настроек и панели таблицы, а также GridBagConstraints
    private final DataTableOptions optionsTableWindow = new DataTableOptions();
    private final JPanel tablePanel;
    private final GridBagConstraints gbc;

    // Конструктор класса MainUi
    public MainUi() {
        // Вызов конструктора суперкласса JFrame с указанием заголовка окна
        super("ComplexTable");

        // Получение экземпляра Toolkit и определение размеров экрана
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dimension = tk.getScreenSize();

        // Задание размеров окна и его позиции на экране
        int windowDimWidth = 600;
        int windowDimHeight = 500;

        /*Этот код устанавливает размеры и положение окна на основе размеров экрана и указанных размеров окна.
        - `dimension.width / 2 - windowDimWidth / 2`: Это выражение вычисляет горизонтальное положение окна так,
        чтобы оно было размещено по центру экрана. Оно вычисляет половину ширины экрана (`dimension.width / 2`) и вычитает
        из нее половину ширины окна (`windowDimWidth / 2`), чтобы центрировать окно по горизонтали.

        - `dimension.height / 2 - windowDimHeight / 2`: Это выражение аналогично первому, но для вертикального положения окна.
        Оно вычисляет половину высоты экрана (`dimension.height / 2`) и вычитает из нее половину высоты окна (`windowDimHeight / 2`),
        чтобы центрировать окно по вертикали.

        - `windowDimWidth` и `windowDimHeight`: Эти значения представляют ширину и высоту окна соответственно.
        */
        this.setBounds(dimension.width / 2 - windowDimWidth / 2, dimension.height / 2 - windowDimHeight / 2, windowDimWidth, windowDimHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Установка шрифта по умолчанию для всего приложения
        setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 18));

        // Создание и настройка менюбара
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem menuItem = new JMenuItem("Старт");
        JMenuItem cancelMenuItem = new JMenuItem("Отмена");
        fileMenu.add(menuItem);
        fileMenu.add(cancelMenuItem);
        menuBar.setLayout(new BorderLayout());
        menuBar.setPreferredSize(new Dimension(windowDimWidth, 25));
        menuBar.add(fileMenu, BorderLayout.WEST);
        setJMenuBar(menuBar);

        cancelMenuItem.addActionListener(e -> dispose());

        // Обработчик события нажатия на пункт меню "Старт"
        menuItem.addActionListener(e -> {
            JFrame parentFrame = MainUi.this;
            DataTableOptions optionsWindow = new DataTableOptions();
            optionsWindow.setOptionsCallback(MainUi.this);
            JDialog dialog = new JDialog(parentFrame, "Options", true);
            dialog.setResizable(false);

            /*
             * В диалоговых окнах Swing контентная панель (contentPane) представляет область, в которой размещаются компоненты,
             * создаваемые для диалогового окна. Добавление панели с настройками optionsWindow на контентную панель dialog
             * позволяет отображать содержимое этой панели внутри диалогового окна, что делает настройки доступными
             *  для пользователя через это диалоговое окно.
             * */
            dialog.getContentPane().add(optionsWindow);

            dialog.pack();

            /*
            * `setLocationRelativeTo` - это метод, который устанавливает положение диалогового окна относительно другого компонента или окна.
            * Когда вы вызываете этот метод с компонентом `parentFrame`, то диалоговое окно будет отображаться относительно этого компонента,
            * при этом диалоговое окно будет центрировано относительно компонента `parentFrame`.
            *
            * В данном коде `dialog.setLocationRelativeTo(parentFrame);` устанавливает положение диалогового окна относительно главного окна
            * приложения (`parentFrame`). Таким образом, когда диалоговое окно открывается, оно будет центрировано относительно главного окна.
            * */
            dialog.setLocationRelativeTo(parentFrame);

            dialog.setVisible(true);
        });

        // Создание панели таблицы с использованием GridBagLayout
        tablePanel = new JPanel(new GridBagLayout());

        // Установка отступов для панели таблицы
        int tablePadding = 10;
        tablePanel.setBorder(new EmptyBorder(tablePadding, tablePadding, tablePadding, tablePadding));

        /// Настройка GridBagConstraints для панели таблицы
        /*
        * Этот код создает объект `GridBagConstraints` и задает параметры для размещения компонентов в `GridBagLayout`.
        *
        * - `gbc.fill = GridBagConstraints.BOTH;`: Это устанавливает режим заполнения компонентов в ячейках сетки. `GridBagConstraints.BOTH` указывает,
        * что компонент будет заполнять доступное пространство как по горизонтали, так и по вертикали.
        * - `gbc.weightx = 1.0;`: Это устанавливает относительный вес (weight) компонентов по горизонтали. Значение 1.0 указывает,
        * что компоненты будут равномерно распределяться по горизонтали.
        * - `gbc.weighty = 1.0;`: Это устанавливает относительный вес (weight) компонентов по вертикали. Значение 1.0 указывает, что компоненты
        * будут равномерно распределяться по вертикали.
        *
        * Эти параметры используются менеджером компоновки `GridBagLayout` для определения того, как компоненты будут распределены и заполнены в сетке.
        * Установка `fill` в `GridBagConstraints.BOTH` позволяет компонентам заполнять доступное пространство в обоих направлениях, а `weightx` и `weighty`
        * определяют, как компоненты будут масштабироваться по горизонтали и вертикали соответственно.
        * */
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        /// Создание основной панели и добавление на неё панели таблицы
        JPanel mainPanel = new JPanel();

        /*Эта строка кода устанавливает менеджер компоновки (layout manager) для mainPanel. В данном случае используется BoxLayout,
        который располагает компоненты в панели в одну колонку (вертикально).

        mainPanel - это панель, для которой устанавливается менеджер компоновки.
        BoxLayout.Y_AXIS - это константа, указывающая, что компоненты будут располагаться в панели вертикально, то есть в столбец.
        Таким образом, после выполнения этой строки компоненты, добавленные в mainPanel, будут располагаться один под другим по вертикали.
         */
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(tablePanel);
        mainPanel.setPreferredSize(new Dimension(500, 300));

        // Добавление основной панели на контентное панель окна
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }

    // Точка входа в приложение
    public static void main(String[] args) {
        MainUi mu = new MainUi();
    }

    // Обработчик события нажатия на кнопку "Insert" в окне настроек
    @Override
    public void onInsertButtonClicked(int rowData, int colData, int roundingData, boolean isTopHeader, boolean isLeftHeader,
                                      boolean isRightFooter, boolean isBottomFooter, boolean isRoundingCheck,
                                      String rightFooterData, String bottomFooterData) {
        // Удаление всех компонентов с панели таблицы
        tablePanel.removeAll();

        // Создание модели таблицы с заданными параметрами
        MyTableModel tableModel = new MyTableModel(rowData, colData, roundingData, isTopHeader, isLeftHeader,
                isRightFooter, isBottomFooter, isRoundingCheck, rightFooterData, bottomFooterData);

        // Создание таблицы и отключение её заголовка
        JTable myTable = new JTable(tableModel);

        /*
        * Этот вызов метода устанавливает заголовок таблицы (JTable) в значение null, что означает отключение отображения заголовка у таблицы*/
        myTable.setTableHeader(null);

        // Добавление таблицы на панель таблицы с прокруткой
        tablePanel.add(new JScrollPane(myTable), gbc);

        // Перерисовка панели таблицы
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    /// Метод для установки шрифта по умолчанию для всего приложения
    // javax.swing.plaf.FontUIResource f - это объект шрифта, который будет установлен в качестве шрифта по умолчанию для компонентов Swing.
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        // UIManager.getDefaults().keys() - это метод, который возвращает перечисление (Enumeration) всех ключей (идентификаторов) их значений по умолчанию в UIManager.
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            // Ключевое слово instanceof в Java используется для проверки, является ли объект экземпляром определенного класса или интерфейса.
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
