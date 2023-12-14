package Algo.linear_algebra.matrices.tests;

import Algo.linear_algebra.matrices.Matrix;
import Algo.linear_algebra.matrices.QRDecomposition;
import Algo.linear_algebra.matrices.Require;
import org.junit.Test;

import java.util.Arrays;
import static Algo.linear_algebra.matrices.Require.T;

/*
    after a call on OnDraft, we should call onPaper if we need the previous version of the matrix before doing some computations on draft.
    example:
        A.transpose() changes A to A_transpose.
        A.onDraft().action() returns the new version of A after the action but A doesn't change.
        A.onPaper().action() performs the action on A so A changes.
        A.action1().then().action2()  accumulates all the actions on A, A changes.
        A.action1().then().action2()  will return the new version of A after all the actions, but A is conserved.
* */
public class SyntaxTest {
    double[][] entries = {{1, 2, 3}, {2, 2, 1}, {3, 4, 3}};
    Matrix A = Matrix.fromEntries(entries).with(Require.D, T); // the matrix has its determinant computed by default
    Matrix B = Matrix.fromEntries(new double[][]{{5, -1, 3}, {0, 1, 6}, {2, 1, -1}});
    Matrix C = Matrix.fromEntries(new double[][]{{1, 0, 1}, {1, 2, 0}, Matrix.vector(3, -1)}).with(Require.D, T); // the matrix has its determinant computed by default

    @Test
    public void rene(){
        System.out.println(Arrays.toString(A.plus(B).minus(A.multiplyBy(2)).getSecondDiagonal()));
    }

    @Test
    public void determinant() {
        assert A.isSquare();

        //out-of-place changes
        System.out.println("ON DRAFT: |_A|="+ A.onDraft().toEchelonForm().productOf(A.onDraft().getMainDiagonal()));
        System.out.println("A=\n" + A); // A in echelonForm

        //in-place changes
        System.out.println("ON-PAPER: |_A|="+ A.onPaper().toEchelonForm().productOf(A.getMainDiagonal()));
        System.out.println("A=\n" + A); //initial A
    }
    @Test
    public void inverse() {
        assert C.isSquare();    // Ensure square-matrix
        System.out.println("_A:\n" + C);
        C.declare("d", C.getD())
                .onDraft()
                .computeCofactors().T()
                .multiplyBy(1.0/ C.valueOf("d"));
        System.out.println("_A:\n" + C);
        System.out.println("_A:\n" + C.onDraft());
    }
    @Test
    public void inverse_determinant() {
        assert A.isSquare();    // Ensure square-matrix

      /**  A.onDraft().declare("d", A.toEchelonForm().then.productOf(A.getMainDiagonal())) //compute determinant
        .then.onPaper().computeCofactors().then.transpose().then.multiplyBy(1.0/ A.valueOf("d"));//compute inverse

        System.out.println("_A:\n" + A); // inverse matrix*/

        System.out.println(Matrix.inverseByAdjugate(A));/*  A.onDraft().declare("d", A.toEchelonForm().then.productOf(A.getMainDiagonal())) //compute determinant
        .then.onPaper().computeCofactors().then.transpose().then.multiplyBy(1.0/ A.valueOf("d"));//compute inverse

        System.out.println("_A:\n" + A); // inverse matrix*/
    }
    @Test
    public void onDraftTest(){ // make changes and preserve the original matrix
        System.out.println("original:\n" + A);//before transformations
        System.out.println("transpose:\n" + A.onDraft().T());
        System.out.println("original:\n" + A);//after transformations

//        System.out.println("transpose:\n" + A.transpose());
        System.out.println("transpose:\n" + A.onPaper().T());
        System.out.println("original:\n" + A);//after transformations
    }

    @Test
    public void test(){
        double[][] values = {{5.0, -1.0, 2.0}, {1.0, -1.0, 0.0}, {3.0, 3.0, 1.0}};
        Matrix B = Matrix.fromEntries(values);
//        B.onDraft().plus(A).then.computeCofactors().then.getMainDiagonal());

        System.out.println("Before change:\n" + B.onDraft().T());
        System.out.println("After change:\n"+ B.onPaper().T());
    }

/*//       System.out.println("A*_A:\n" + A.dot(_A));
//        System.out.println("_A*A:\n" + _A.dot(A));
        System.out.println("|A|="+A.getD() + " vs " + "|_A|="+_A.getD());*/

    @Test
    public void QR(){
        double[][] m = {{0, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        Matrix[] QR = QRDecomposition.decompose(Matrix.fromEntries(m));
        System.out.println("Q:\n" + QR[0]);
        System.out.println("R:\n" + QR[1]);
    }

    @Test
    public void exam(){
//        System.out.println(Objects.requireNonNull(Matrix.inverseByAdjugate(C)).dot(C));
        System.out.println();



    }
}
