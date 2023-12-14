package Algo.calculus;

public class AreaUnderCurve {

    public static final double dx = 1E-4;

    public static void main(String[] args) {
        //System.out.println(calculate(0, 1, Math::log));
       // find("n^5", 2, 7);
    }

    private static double calculate(double a, double b, Function f) {
        double area = 0;
        double modifier = 1;

        if(a > b){
            swapLimits(a, b);
            modifier = -1;
        }
        for(double i = a + dx; i < b; i += dx){
            double distFromA = i - a;//distance from A to i
            area += (dx / 2) * (f.valueOf(a + distFromA) + f.valueOf(a + distFromA - dx));
        }

        return area;
    }

    static void swapLimits(double a, double b) {
        a += (b - (b = a));
    }

}
