package Algo.linear_algebra.matrices;

import java.util.Objects;

/*
    QR factorization or decomposition
    gram-Schmidt method
    given a nxn matrix A = Q.R
    Q: nxn matrix with orthonormal vectors
    R: nxn upper triangular matrix

    * vectors are by default Column vectors
    - ORTHOGONAL: v2...vn should be orthogonal to 'v1' => vi . vj = 0 (&=90)
    - ORTHONORMAL: a vector 'v' can be normalized => v' = v / norm2(a)  ->{modulus(v')=1}
    - orthogonal vector v2~ = v2 - <v2, v1~>.v1~
    - Q = [q1 q2 ... qn] = [v1' v2'...vn']
    -
* */
public class QRDecomposition {

    public double[] orthogonalVectors;

    public static Matrix[] decompose(Matrix A){
        assert A != null;
        Matrix[] QR = new Matrix[2];
        QR[0] = Matrix.fromEntries(Matrix.orthoNormalize(A.getEntries()));
        QR[1] = Objects.requireNonNull(Matrix.inverseByAdjugate(QR[0])).dot(A);
        return QR;
    }

}
