package Algo.linear_algebra.necessity;

import net.objecthunter.exp4j.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Functions {
    private static final int MAX_VARS = 3;
    public static Function root = new Function("root", 2) {
        @Override
        public double apply(double... args) {
            return Math.pow(args[1], 1.0 / args[0]);
        }
    };
    public static Function rand = new Function("rand", 2) {
        @Override
        public double apply(double... args) {
            return new Random().doubles(args[0], args[1]).average().getAsDouble();
        }
    };
    public static Function avg;
    private static Function log = new Function("log", 2) {
        @Override
        public double apply(double... args) {
            return Math.log(args[0]) / Math.log(args[1]);
        }
    };

    public static int getVariablesNo(String function) {
        final String vars = "xyz";
        final StringBuilder foundVars = new StringBuilder("");
        int varsNo = 0;

        for (char c : function.toCharArray()) {
            int found = foundVars.indexOf(c + "");

            if (vars.contains(c + "") && found == -1) varsNo++;

            foundVars.append(c + "");
        }

        return varsNo;
    }

    public static List<net.objecthunter.exp4j.function.Function> getFunctions(String s) {
        List<Function> functions = new ArrayList<>();

        if (s.contains("log")) functions.add(log);
        if (s.contains("root")) functions.add(root);
        if (s.contains("rand")) functions.add(rand);

        if (s.contains("avg")) functions.add(avg = new Function("avg", countArgs(s, "avg")) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) sum += arg;
                return sum / args.length;
            }
        });
        return functions;
    }

    public static int countArgs(String exp, String funcName) {
        //+2 from a to g in avg and +1 because the 1st arg comes after (
        int indexOfFirstArg = exp.indexOf('(', exp.indexOf(funcName)) + 1;
        int indexOfLastArg = exp.indexOf(')', indexOfFirstArg) - 1;
        int argsNo = 0;

        for (int i = indexOfFirstArg; i < indexOfLastArg; i++)
            if (exp.charAt(i) == ',') argsNo++;

        return argsNo + 1;
    }

    public static void main(String[] args) {

        System.out.println(getVariablesNo("x+z+a+b+a"));

    }
}
