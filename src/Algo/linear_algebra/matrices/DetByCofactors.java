package Algo.linear_algebra.matrices;

import static Algo.linear_algebra.matrices.Matrix.MATS;
import static Algo.linear_algebra.matrices.Matrix.STEPS;

public class DetByCofactors {

    private static char matLabel = 'A';
    private static char firstCofactorOfCurrentMatrix = 'A';
    public static double[] MAT_COEFS; // coefficients of matrices that appear as steps in the process

    public static void findDeterminant(Matrix M) {
        if (!M.isSquare()) return;//Only square MATRICES have determinant.

        //size N=1x1 is an invalid matrix
        int N = M.getRows();
        if (N == 2) {
            Matrix.solveForSize2(M);
            return;
        }
        //from N >= 3
        Matrix[] cofactors = new Matrix[N];//get n MATRICES of size N-1 from M of size N
        M.setLabel(matLabel);
        M.setD(0.0);
        int SIZE = N - 1;

        //VIEW

        StringBuilder temp = new StringBuilder((firstCofactorOfCurrentMatrix++)+"->" + N + " COFACTORS("+SIZE+"x" + SIZE +"):\t");
        for (int i = 0; i <N; i++)
            temp.append(++matLabel).append("-> Crossing R1").append(" and C").append(i + 1).append("; ");//default crossRow = 0 or 0+1
        STEPS.add(temp.toString());

        STEPS.add("$" + MATS.size());//signal presence of MATRICES => cofactors have all same size and have to be printed in a row.
        STEPS.add((char)(M.getLabel() + 1) <= 'B'?" The development of the mat" + (char)(M.getLabel() + 1) + " is given, others will follow the pattern.":"");
        for (int i = 0; i <= SIZE; i++) {
            //keep row=0 as default
            cofactors[i] = getCofactor(M, 0, i);
            assert cofactors[i] != null;
            cofactors[i].setCoefficient(getCofactorSign(0, i) * M.getEntries()[0][i]);
            //VIEW
            MATS.add(cofactors[i]);//from Mxx to '\n' tells that we should get matrix at index=xx
//            Matrix.PROCESS.append("\n(" + cofactors[i].getCoefficient() + ") x \nM" +(MATS.size()-1) + "\n" + cofactors[i]).append(i < N - 1? "+":"\n");//M = Matrix
        }
        //repeat process until the deepest layer
        for (int i = 0; i <= SIZE; i++) {
            findDeterminant(cofactors[i]);/*this line if not commented will make the code perform depth first*/
            cofactors[i].setD(cofactors[i].getD() * cofactors[i].getCoefficient());
            M.setD(M.getD() + cofactors[i].getD());
        }
    }

    public static Matrix getCofactor(Matrix M, int crossRow, int crossCol) {
        /**Return the cofactor of the matrix when row=0 and col chosen are crossed*/
        if (crossRow >= M.getRows() || crossCol >= M.getCols()) return null;

        Matrix cofactor = Matrix.fromShape(M.getRows() - 1, M.getCols() - 1);
        int ind = 0;

        for (int i = 0; i < M.getRows(); i++) {//row 0 crossed by default
            if (i != crossRow) {
                for (int j = 0; j < M.getCols(); j++) {//starting
                    if (j != crossCol) {
                        cofactor.getEntries()[ind / cofactor.getCols()][ind % cofactor.getCols()] = M.getEntries()[i][j];
                        ind++;
                    }
                }
            }
        }
        return cofactor;
    }

    public static int getCofactorSign(int row, int col) {
        return (int) Math.pow(-1.0, (double) (row + col));
    }
}