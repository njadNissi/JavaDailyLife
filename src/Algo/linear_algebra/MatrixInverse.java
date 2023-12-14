package Algo.linear_algebra;

import Algo.linear_algebra.matrices.Matrix;

public class MatrixInverse {

    public static void main(String[] args) throws CloneNotSupportedException {

        double[][] entries = {{2.0, 1.0}, {3.0, 1.0}};
        Matrix matrix = Matrix.fromEntries(entries);

        Matrix.findDeterminant(matrix, 1);
        System.out.println("matrix:\n" + matrix);
        System.out.println("D = " + matrix.getD());
        System.out.println("Cofactor:\n" + Matrix.cofactor(matrix));
        System.out.println("adjugate:\n" + Matrix.adjugate(matrix));
        Matrix inverse = Matrix.inverseByAdjugate(matrix);
        assert inverse != null;
        System.out.println("inverse:\n" + inverse);



    }

}
