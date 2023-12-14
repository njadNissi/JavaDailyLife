/*
package Algo.linear_algebra.matrices;

import static Algo.linear_algebra.matrices.Matrix.MATS;
import static Algo.linear_algebra.matrices.Matrix.STEPS;

public class DetByUpperTriangular {

    */
/**
     * 3 gold rules
     * (1) Convert all the elements of the 1srt col to zeros, then move to next;
     * (2) For a given column, first the 1st row and then move on.
     * (3) Rows operation are made for element at col=j with row=j
     * Example: for N=4 matrix: 1=>1:0 -> 2:0 -> 2:1 -> 3:0 -> 3:1 -> 3:2
     *//*


    public static void findDeterminant(Matrix M) {
        if (!M.isSquare()) return;

        // view
        MATS.add(M);

        if (M.getRows() == 2) {
            Matrix.solveForSize2(M);
            return;
        }
        makeUpperTriangular(M);

        if (M.isUpperTriangular()) {
            M.setD(productOfDiagonalElements(M));

            //view
            STEPS.add("M is an upper triangular matrix. Its determinant is given by product of elements of the main diagonal. |Δ| = " + M.getD());
        }
    }

    public static Matrix makeUpperTriangular(Matrix M) {

       */
/* //we start making zero from the second row=1 and col=0
        Double[] zeros = getCellsUnderDiagonal(M);

        for (MatrixCell c : zeros) {//following the cells order for # rule 1 and 2
            if (c.value() != 0) {//if an element under the diagonal is not zero
                MatrixCell targetElement = new MatrixCell(c.col(), c.col(), M.getEntries()[c.col()][c.col()]);//rule #3 use Col1 for row num.
                double multiplier = targetElement.value() / c.value();

                M.getEntries()[c.row()] = M.multiplyRowBy(c.row(), multiplier);
                char operation;
                if ((c.value() >= 0 && targetElement.value() >= 0) || (c.value() < 0 && targetElement.value() < 0)) {
                    subtractRows(M, c.row(), targetElement.row());
                    operation = '-';
                } else {
                    addRows(M, c.row(), targetElement.row());
                    operation = '+';
                }

                // view
                STEPS.add("ROW" + (c.row() + 1) + " ==> " + Math.ceil(multiplier) + " x ROW" + (c.row() + 1)
                        + " / " + c.value() + " " + operation + " ROW " + (targetElement.row() + 1) +
                        " to make a zero from element " + c);
                MATS.add(M);
            }
        }*//*

        return M;
    }

    public static double productOfDiagonalElements(Matrix M) {
        MatrixCell[] mainDiagonal = M.getMainDiagonal();
        double product = mainDiagonal[0].value();

        STEPS.add(product + "");

        for (int i = 1; i < mainDiagonal.length; i++) {
            product *= mainDiagonal[i].value();

            //view
            STEPS.add(" x " + mainDiagonal[i]);
        }

        return product;
    }

    public static boolean isUpperTriangular(Matrix M) {
        MatrixCell[] cellsUnderDiagonal = getCellsUnderDiagonal(M);
        for (MatrixCell c : cellsUnderDiagonal)
            if (c.value() != 0) return false;
        return true;
    }

    public static MatrixCell[] getCellsUnderDiagonal(Matrix M) {
        */
/**get a matrix M and return list of all elements to transform into zeros
         * for row = i there are i elements to set to zero
         * for a square matrix of size N, there are N sum|0-N (i)*//*


        MatrixCell[] zeros = new MatrixCell[sum(0, M.getRows(), 1)];
        int pos = 0;
        //At row zero there always zero elements to set to zero.
        for (int i = 1; i < M.getRows(); i++)
            for (int j = 0; j < i; j++)
                zeros[pos++] = new MatrixCell(i, j, M.getEntries()[i][j]);
        return zeros;
    }

    private static int sum(int start, int end, int step) {
        //sum of series from i=a to i=b for value=X
        int sum = 0;
        for (int i = start; i < end; i += step)
            sum += i;
        return sum;
    }

    public static void addRows(Matrix M, int row1, int row2) {
        for (int i = 0; i < M.getCols(); i++)
            M.getEntries()[row1][i] += M.getEntries()[row2][i];
    }

    public static void subtractRows(Matrix M, int row1, int row2) {
        for (int i = 0; i < M.getCols(); i++)
            M.getEntries()[row1][i] -= M.getEntries()[row2][i];
    }

    public static void addCols(Matrix M, int cols1, int col2) {
        for (int i = 0; i < M.getRows(); i++)
            M.getEntries()[i][cols1] += M.getEntries()[i][col2];
    }

    public static void subtractCols(Matrix M, int cols1, int col2) {
        for (int i = 0; i < M.getRows(); i++)
            M.getEntries()[i][cols1] -= M.getEntries()[i][col2];
    }

    public static void main(String[] args) {
//        Matrix M = new Matrix("1, 2, -3, 4\n"+
//                              "2, 3, 1, 0\n"   +
//                              "0, -1, -3, 1\n"+
//                              "5, 1, 0, 2"
//        );
        Matrix M = Matrix.createFromStrEntries(
                "2, -3, 1    \n" +
                        "2, 0, -1" +
                        "\n  1, 4, 5"
        );
        findDeterminant(M);
        System.out.println(M.getD());
    }
}*/
