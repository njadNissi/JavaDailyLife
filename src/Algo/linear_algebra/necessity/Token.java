package Algo.linear_algebra.necessity;

public class Token {

    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVIDE = '/';
    public static final char OPEN_PARENTHESIS = '(';
    public static final char CLOSE_PARENTHESIS = ')';
    public static final char PERCENT = '%';
    public static final char POWER = '^';
    public static final char E = 'e';
    public static final char DOT = '.';
    public static final char PI = 'π';


    public static boolean isDigit(char ch) {
        return (ch >= '0' && ch <= '9')
                || ch == DOT
                || ch == E
                || ch == PI; //to help construct each char of the string number;
    }

    public static boolean isVariable(char ch) {
        return (ch == 'x' || ch == 'y' || ch == 'z');
    }

    public static boolean isLiteral(char ch) {
        //literal is part of function name
        return ((ch >= 'A' && ch <= 'W') || (ch >= 'a' && ch <= 'w'));
    }

    public static boolean isOperator(char ch) {
        return ch == MULTIPLY
                || ch == DIVIDE
                || ch == PLUS
                || ch == MINUS
                || ch == OPEN_PARENTHESIS
                || ch == CLOSE_PARENTHESIS
                || ch == POWER
                || ch == PERCENT
                ;
    }

}
