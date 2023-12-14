package Algo.linear_algebra.necessity;

import java.util.Stack;

import static Algo.linear_algebra.necessity.Token.*;

public class ExpressionConverter {
    private static Stack<Character> operatorStack;
    private static StringBuilder postfix;

    public static String infixToPostfix(String infix) {
        /**a space is added to separate operands from operators.
         to conserve the whole number 25 + 7 - 71
         */
        infix = infix.replace(" ", "");//format exp. to min length necessary
        infix = insertIntendedOperation(infix);//add * before or after parenthesis
        operatorStack = new Stack<>();
        postfix = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            char spot = infix.charAt(i);

            switch (spot) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '%': {
                    postfix.append(' ');
                    handleOperator(spot);
                    break;
                }
                case '(':
                    operatorStack.push(spot);
                    break;
                case ')':
                    handleRightParen();
                    break;
                default:
                    postfix.append(spot);
                    break;
            }
        }

        while (!operatorStack.isEmpty())//remaining operators
            postfix.append(operatorStack.pop());

        return postfix.toString();
    }

    private static void handleRightParen() {
        while (!operatorStack.empty()) {
            char op = operatorStack.pop();
            if (op == '(') break;
            else postfix.append(op);
        }
    }

    private static void handleOperator(char spotOp) {
        while (!operatorStack.isEmpty()) {
            char topOp = operatorStack.pop();
            if (topOp == '(') {
                operatorStack.push(topOp);
                break;
            } else {
                if (precedence(topOp) < precedence(spotOp)) {
                    /** 25 + 12 * 9 => Addition not performed until we find out
                     * the next operator which * and then decide by comparing both ops.*/
                    operatorStack.push(topOp);
                    break;
                } else postfix.append(topOp);
            }
        }
        operatorStack.push(spotOp);
    }

    private static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '(':
            case ')':
                return 3;
            case '%':
                return 4;
            case '^':
                return 5;
            default:
                return -1;
        }
    }

    /**
     * Parenthesis can represent multiplication: it's an INTENDED OPERATOR,
     * so if not explicitly written a*(b+c) or (a+b)*c
     * then according to appropriate case add it.
     * <p>
     * the same logic is used for variables such as 2X = 2*x
     */
    public static String insertIntendedOperation(String s) {
        StringBuilder sb = new StringBuilder(s);

        for (int i = 1; i < s.length(); i++) {

            char c = s.charAt(i);

            if (isDigit(sb.charAt(i - 1))) {

                if (c == OPEN_PARENTHESIS)
                    sb.insert(i, MULTIPLY);
                if (Token.isVariable(c)) {
                    //to avoid any confusions such as 2*-5, let's write 2*(-5)

                }
            } else if (i < s.length() - 1
                    && sb.charAt(i) == CLOSE_PARENTHESIS
                    && (isDigit(sb.charAt(i + 1))
                    || sb.charAt(i + 1) == OPEN_PARENTHESIS)) {

                sb.insert(i + 1, MULTIPLY);
            }
        }

        System.out.println("exp = " + sb.toString());
        return sb.toString();
    }

}
