package Algo.linear_algebra.necessity;

public abstract class Function {

    private String name;
    private int argsNo;

    public Function(String name, int argsNo) {

        if (!isValidFunctionName(name))
            System.out.println("should throw an exception");

        this.name = name;
        this.argsNo = argsNo;
    }

    public abstract double operate(double... args);


    public int getArgsNo() {
        return argsNo;
    }

    public void setArgsNo(int argsNo) {
        this.argsNo = argsNo;
    }

    private boolean isValidFunctionName(String name) {

        for (Character c : name.toCharArray())
            if (Token.isOperator(c)) return false;

        return true;
    }
}
