package Algo.calculus;

/**
 * The approach is the area under the curve function y within [a, b]. y = f(x)
 * */
public class DefiniteIntegral {

    public static final double dx = 1E-4;

    public static void main(String[] args) {
        find("n^5", 2, 7);
    }

    private static void find(String exp, double a, double b){
        int e = Integer.parseInt("" + exp.charAt((exp.lastIndexOf("^") + 1)));
        System.out.println(e);
        //print indefinite
        System.out.println(getIndefinite(exp.charAt(0), e));

        //print definite
        System.out.println(getDefinitePrim(e, a, b));
    }
    private static String getIndefinite(char x, int n){
        return (x + "^" + (n+1) + "/" + (n+1));
    }

    private static double getDefinitePrim(int n, double a, double b){
        int e_d = n + 1;
        double r = Math.pow(b, e_d) / e_d;
        double l = Math.pow(a, e_d) / e_d;

        return r - l;
    }
//
//    private char getVar(String exp){
//        for(char c : exp){
//            if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
//                return c;
//        }
//        return ' ';
//    }
}
