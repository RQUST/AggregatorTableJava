import java.util.List;

public interface Aggregator {
    void addValue(double value);

    double getResult();

    void reset();
}

// Сумма чисел
class SumAgg implements Aggregator {

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

// Среднее значение
class AverageAgg implements Aggregator {

    private double sum = 0;
    private double count = 0;

    @Override
    public void addValue(double value) {


        sum += value;
        count++;

    }

    @Override
    public double getResult() {
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    @Override
    public void reset() {
        sum = 0d;
        count = 0d;
    }

}

// Количество
class CounterAgg implements Aggregator {

    private double count = 0;

    @Override
    public void addValue(double value) {

        count++;

    }

    @Override
    public double getResult() {
        return count;
    }

    @Override
    public void reset() {
        count = 0d;
    }

}

// Максимальное значение
class MaxAgg implements Aggregator {

    private double maxValue = 0;

    @Override
    public void addValue(double value) {

        if (maxValue < value) {
            maxValue = value;
        }

    }

    @Override
    public double getResult() {
        return maxValue;
    }

    @Override
    public void reset() {
        maxValue = 0d;
    }

}

// Минимальное значение
class MinAgg implements Aggregator {

    private double minValue = 0;

    @Override
    public void addValue(double value) {

        if (minValue > value) {
            minValue = value;
        }

    }

    @Override
    public double getResult() {
        return minValue;
    }

    @Override
    public void reset() {
        minValue = 0d;
    }

}

// Сумма квадратов
class SquaredSumAgg implements Aggregator {

    private double sqdSum = 0;
    private double tmp;

    @Override
    public void addValue(double value) {

        tmp = value;
        sqdSum += tmp * tmp;

    }

    @Override
    public double getResult() {
        return sqdSum;
    }

    @Override
    public void reset() {
        sqdSum = 0d;
        tmp = 0d;
    }

}

class StandardDeviationAgg implements Aggregator {

    private double sum = 0;
    private double sumSquared = 0;
    private int count = 0;

    @Override
    public void addValue(double value) {
        sum += value;
        sumSquared += value * value;
        count++;
    }

    @Override
    public double getResult() {
        if (count < 2) {
            return 0; // Стандартное отклонение определено только для более чем одного значения
        }
        double variance = (sumSquared - (sum * sum) / count) / (count - 1);
        return Math.sqrt(variance);
    }

    @Override
    public void reset() {
        sum = 0;
        sumSquared = 0;
        count = 0;
    }
}