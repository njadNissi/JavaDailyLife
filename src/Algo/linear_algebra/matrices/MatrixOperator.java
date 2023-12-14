package Algo.linear_algebra.matrices;

public interface MatrixOperator {

    public double[] multiplyRowBy(int row, double multiplier);

    public boolean isUpperTriangular();

}