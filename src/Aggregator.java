import javax.swing.table.AbstractTableModel;
import java.util.Arrays;

public interface Aggregator {
    void addValue(double value);
    double getResult();
    void reset();
}

// Сумма чисел
class SumAgg implements Aggregator{

    private double res = 0;

    @Override
    public void addValue(double value) {
        res += value;
    }

    @Override
    public double getResult() {
        return res;
    }

    @Override
    public void reset() {
        res = 0d;
    }
}
