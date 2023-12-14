/*
package Algo.linear_algebra.vectors;


import Algo.linear_algebra.matrices.Matrix;

*/
/*
* Vector by default COLUMN VECTOR
* *//*

public class Vector {
    public double[] C; // C: components
    public final int length;

    private Vector(double[] c){
        this.C = new double[c.length];
        this.length = c.length;
        System.arraycopy(c, 0, this.C, 0, c.length);
    }

    public static Vector zeros(int size){
        return new Vector(new double[size]);
    }

    public static Vector ones(int size){
        return fromValue(size, 1);
    }


    public static Vector fromComponents(double...c){
        return new Vector(c);
    }

    */
/*
        If sign = 1, sum(v, w) = v + v
        If sign = -1, sum(v, w) = v - w
    * *//*

    public static Vector sum(Vector v, Vector w, int sign){
        assert v.length == w.length;
        Vector sum = zeros(v.length);
        for (int i = 0; i < v.length; i++)
            v.C[i] += sign * w.C[i];
        return sum;
    }

    public Vector add(Vector v){
        return sum(this, v, 1);
    }
    public Vector subtract(Vector v){
        return sum(this, v, -1);
    }

    public double dot(Vector v){
        return dot(this, v);
    }

    */
/*
            v.w = v^T . w = transpose(v) * w
    * *//*



    public Vector normalize(){
        return normalize(this);
    }

    public static Vector normalize(Vector vector){
        double norm = 0.0;
        for (double e : vector.C)
            norm += Math.pow(e, 2);

        norm = Math.sqrt(norm);

        for (int i = 0; i < vector.length; i++) vector.C[i] /= norm;

        return vector;
    }


    public static Vector orthogonal(Vector...v){ // 2D array
        // get columns instead of rows

        // assert all the vectors have the same dimension
        //v[0] is already orthogonal
        Vector ortho = Vector.zeros(v[0].length);
        for (int i = 1; i < v.length; i++) { // for each vector
            normalize(v[i-1]);
            v[i] -=
        }
        return ortho;
    }

    public

    public Vector then(){
        return this;
    }
}
*/
