import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
    MyFrame frame;
    public MyTableModel(MyFrame frame) {
        this.frame = frame;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        boolean rowHeaderSelected = frame.isAddRowHeaderCheckbox();
        boolean colHeaderSelected = frame.isAddColHeaderCheckbox();

        if (rowHeaderSelected && row == 0) {
            return false;
        }

        if (colHeaderSelected && column == 0) {
            return false;
        }

        return true;
    }
}
