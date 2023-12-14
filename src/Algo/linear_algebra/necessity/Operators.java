package Algo.linear_algebra.necessity;

import net.objecthunter.exp4j.operator.Operator;

import java.util.ArrayList;
import java.util.List;

public class Operators {

    private static Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
        @Override
        public double apply(double... args) {
            final int arg = (int) args[0];
            if ((double) arg != args[0]) {
                throw new IllegalArgumentException("Operand for factorial has to be an integer");
            }
            if (arg < 0) {
                throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
            }
            double result = 1;
            for (int i = 1; i <= arg; i++) {
                result *= i;
            }
            return result;
        }
    };

    public static List<Operator> getOperators(String s) {
        List<Operator> operators = new ArrayList<>();
        if (s.contains("!"))
            operators.add(factorial);
        return operators;
    }

}
