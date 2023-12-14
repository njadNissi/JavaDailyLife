package Algo.linear_algebra.necessity;

import java.util.ArrayList;
import java.util.List;

public class ListCalculator {

    public static void main(String[] args) {
        String input = "2*((2-4)+(5+4))";
        //input = "2*2-4+5+4";
        input = "5+80-7";
        System.out.println(evaluate(input));
        //System.out.println( Character.digit('2', Character.MAX_RADIX));
    }

    public static double evaluate(String expression) {
        double result = 0;
        String operation = "";
        List<Character> openBrackets = new ArrayList<Character>();
        List<Character> closeBrackets = new ArrayList<Character>();
        StringBuilder innerInput = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char spot = expression.charAt(i);
            if (openBrackets.isEmpty()) {
                if (Character.isDigit(spot)) {
                    if (operation == "" && result == 0) {
                        result = Character.digit(spot, Character.MAX_RADIX);
                        continue;
                    } else if (operation != "") {
                        result = calculateWithOperation(operation, Character.digit(spot, Character.MAX_RADIX), result);
                        continue;
                    }
                }
                // if the input is operation then we must set the operation in order
                // to be taken into consideration again ..
                if (spot == '+' || spot == '-' || spot == '*' || spot == '/') {
                    operation = Character.toString(spot);
                    continue;
                }
            }
            if (spot == '(') {
                // set operation to be empty in order to evaluate the
                // operations inside the brackets ..
                openBrackets.add(spot);
                continue;
            }
            if (spot == ')') {
                closeBrackets.add(spot);
                if (openBrackets.size() == closeBrackets.size()) {
                    openBrackets.remove((Character) '(');
                    closeBrackets.remove((Character) ')');
                    double evalResult = evaluate(innerInput.toString());
                    result = calculateWithOperation(operation, evalResult, result);
                    innerInput.setLength(0);
                }
                if (openBrackets.size() > closeBrackets.size()) {
                    continue;
                }
            } else {
                innerInput.append(spot);
            }
        }
        return result;
    }

    private static double calculateWithOperation(String operation, double inputChar, double output) {
        switch (operation) {
            case "+":
                output += inputChar;
                break;
            case "-":
                output -= inputChar;
                break;
            case "*":
                output *= inputChar;
                break;
            case "/":
                output /= inputChar;
                break;
            default:
                break;
        }
        return output;
    }
}