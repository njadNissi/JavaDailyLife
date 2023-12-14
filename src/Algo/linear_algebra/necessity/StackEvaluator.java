package Algo.linear_algebra.necessity;

import java.util.HashMap;
import java.util.Stack;

import static Algo.linear_algebra.necessity.ExpressionConverter.*;
import static Algo.linear_algebra.necessity.Token.*;


public class StackEvaluator {
    private static String expression;
    private static final Stack<Double> numberStack;//list of numbers used in the expression
    private static HashMap<String, Function> functions;//list of functions used in the expression
    private static final HashMap<Character, Double> variables;//list of variables used in the expression

    static {
        //working with postfix notation doesn't require operations sorting.
        numberStack = new Stack<>();//for Doubles, this stack created by me
        //the postfix expression obtained from the infix
        StringBuilder postfix = new StringBuilder("");
        functions = new HashMap<>();
        variables = new HashMap<>();
    }

    public static void setExpression(String expression) {
        StackEvaluator.expression = expression;
    }

    public static double evaluate() {
        String pfe = infixToPostfix(StackEvaluator.expression);//pfe = postFixExpression

        for (int i = 0; i < pfe.length(); i++) {
            char spot = pfe.charAt(i);

            if (isDigit(spot)) {//If digit found, read the whole number
                switch (spot) {
                    case E:
                        numberStack.push(Math.E);
                        break;
                    case PI:
                        numberStack.push(Math.PI);
                        break;
                    default: {
                        int numLastIndex = numLastInd(i, pfe);//get index of last digit of expected number starting from i.

                        try {//case expression has no operators
                            numberStack.push(Double.parseDouble(pfe.substring(i, numLastIndex + 1)));
                        } catch (NumberFormatException e) {
                            /**No way to compute this calculation due to index out of range*/
                        } finally {
                            numberStack.push(Double.parseDouble(pfe.substring(i, numLastIndex)));
                        }

                        i = numLastIndex - 1;//-1 to cancel the iteration increment
                    }
                }
            } else if (isVariable(spot)) {

                Double value = variables.get(spot);
                //System.out.println(spot +" : " + value);

                if (null == value) {
                    //case of analytic expression
                } else {
                    //arithmetic expression, just substitute by the corresponding value
                    numberStack.push(value);
                }

            } else if (isOperator(spot)) {
                //special case 1 => change this to modulus a % b
                if (spot == PERCENT)
                    numberStack.push(numberStack.pop() / 100);
                else
                    try {
                        if (numberStack.size() > 1)
                            numberStack.push(Arithmetic.calculate(numberStack.pop(), numberStack.pop(), spot));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
            }
            //case of blank char or space
        }
        return numberStack.peek();
    }

    private static int numLastInd(int initIndex, String str) {
        for (int i = initIndex + 1; i < str.length(); i++)
            if (!isDigit(str.charAt(i)))
                return i;
        return initIndex + 1;
    }

    public static void setFunctions(HashMap<String, Function> functions) {
        StackEvaluator.functions = functions;
    }

    public static void setVariables(Double x, Double y, Double z) {
        //applied for 3 variables, can be extended to further more.
        StackEvaluator.variables.put('x', x);
        StackEvaluator.variables.put('y', y);
        StackEvaluator.variables.put('z', z);
    }

    public static void main(String[] args) {
        /*String exp = "2( 5 + 7)(4 - 2)";
        System.out.println("Expression = " + exp);

        exp = exp.replace(" ", "");
        System.out.println("format1 = " + exp);

        exp = insertIntendedOperation(exp);
        System.out.println("format2 = " + exp);

        System.out.println("postFix = " + infixToPostfix(exp));

        System.out.println("result = " + evaluate(exp));*/

    }
}