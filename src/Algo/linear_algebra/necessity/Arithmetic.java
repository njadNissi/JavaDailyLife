package Algo.linear_algebra.necessity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Hashtable;
import net.objecthunter.exp4j.function.Function;

public class Arithmetic {

    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVIDE = '/';
    public static final char POWER = '^';
    private static final DecimalFormat format = new DecimalFormat("#.###");

    static {
        format.setRoundingMode(RoundingMode.FLOOR);
    }

    public static Double calculate(Double a, Double b, char op) {
        switch (op) {
            case PLUS:
                return a + b;
            case MINUS:
                return b - a;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                return b / a;
            case POWER:
                return Math.pow(b, a);
            default:
                return 0.0;
        }
    }

    /**
     * this function receives the table of functions, and uses the funcName
     * to search for a function and returns the result of applying the arguments
     * to that function.
     */
    public static Double calculate(Hashtable<String, Function> functions, String funcName, double... args) {

        Function function = functions.get(funcName);

        if (null == function) return Double.NEGATIVE_INFINITY;

        return function.apply(args);

    }

    public static void formatNumTo3decimals(Double[][] numbers) {
        int rows = numbers.length, cols = numbers[0].length;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                numbers[i][j] = Double.parseDouble(format.format(numbers[i][j]));
    }

}
