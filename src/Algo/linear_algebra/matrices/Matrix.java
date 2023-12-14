package Algo.linear_algebra.matrices;

import Algo.linear_algebra.vectors.VectorOperators;

import java.util.*;

public class Matrix implements MatrixOperator, VectorOperators<Matrix> {
    private Matrix last; // this is the very last matrix state from transformations
    private static Map<String, Double> draft = new HashMap<>();
    public static ArrayList<String> STEPS;//list of steps that appeared in the process.
    public static ArrayList<Matrix> MATS;//list of MATRICES that appeared in the process.
    private String matrixText;
    private double[][] original; // entries
    private double[][] entries;
    private Matrix T; // transpose
    private Matrix[][] C; // cofactors
    private Matrix I; // inverse
    private Matrix H; // inverse
    private double[] mainDiagonal;
    private double[] secondDiagonal;/*    secondDiagonal = new Double[n];
        for (int i = 0, j = n-1; i < n && j > 0; i++, j--) mainDiagonal[i] = entries[i][j];*/
    private int rows;
    private int cols;
    private double D;
    private double tr; // trace
    private double coefficient;
    private Character label; // A, B, C...
    private boolean onDraft = false;
    public final Matrix then = this; // return current matrix

    public static Matrix fromShape(int rows, int cols){
        return new Matrix(rows, cols);
    }

    public static Matrix fromEntries(double[][] entries){
        return new Matrix(Matrix.copyOf(entries));
    }

    public static Matrix fromStrEntries(String entries){
        Matrix m = new Matrix(entries);
        findDeterminant(m, 1);
        return m;
    }

    private Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.original = new double[rows][cols];
        this.entries = new double[rows][cols];
        this.D = 0.0;
        this.coefficient = 1.0;
    }

    private Matrix(double[][] entries){
        this(entries.length, entries[0].length);
        this.original = Matrix.copyOf(entries);
        this.entries = Matrix.copyOf(entries);
    }

    private Matrix(String matrix) {
        if (isValid(matrix)) {
            this.matrixText = matrix;
            this.D = Double.POSITIVE_INFINITY;
            readSize();//set matrix size
            fillEntries();//fill 2dArray
        }
    }

    /**a matrix should be at least 4 chars long in a non-spaced string:
     //  (1) [a][b]    => 1X2 => 1 row and 2 cols given as a,b
     //  (2) [a]       => 2X1 => 2 rows and 1 col given as a'\n'b
     [b]
     */
    public static boolean isValid(String matrix) {

        return matrix.length() >= 4;
    }

    public static double[] vector(int size, double value){
        double[] v = new double[size];
        if(value != 0) Arrays.fill(v, value);
        return v;
    }

    public static double[] getVector(double[][] m, int index, int axis){ // axis => 'r' for row and 'c' for col
        switch (axis){
            case 0: return m[index];
            case 1:
            default: return Matrix.transpose(m)[index];
        }
    }

    public Matrix normalize(){
        for (int i = 0; i < rows; i++)
            entries[i] = normalize(entries[i]);
        return this;
    }

    /*
    * Returns a matrix for each column vector normalized.
    *  --      --       |--             --|
    * | x1 ... y1|      |x1/|X| ... y1/|Y||
    * | x2 ... y2|  =>  |x2/|X| ... y2/|Y||
    * | x3 ... y3|      |x3/|X| ... y3/|Y||
    * | --     --|      |--             --|
    * */
    public static double[] normalize(double...vector){
        double norm = 0.0;
        for (double e : vector) // sum squared
            norm += Math.pow(e, 2);
        norm = Math.sqrt(norm); // norm of ith vector
        for (int i = 0; i < vector.length; i++) vector[i] /= norm;
        return vector;
    }

    /*
     * Given vector 3 vectors x, y, z
     * x = is taken as orthogonal by default, so X = x
     *           X^T . y
     * Y = y -  --------X    = y - <y, qX>qX
     *           X^T . X
     *             1
     * where qX = ----X
     *             |x|
     *
     *           X^T . z        Y^T . z
     *  Z = z - --------X -  ---------Y  = z - <z, qX>qX - <z, qY >qY
     *           X^T . X       Y^T . Y
     *             1
     *  where qX = ----X
     *             |x|
     *
     * */
    public static double[][] orthoNormalize(double[]...matrix){ // matrix
        // get columns instead of rows
        // assert all the vectors have the same dimension

        double[][] Q = transpose(matrix); // matrix to contain orthonormal columns
        //the first vector is already orthogonal
        Q[0] = normalize(Q[0]); // normalize first vector
        double dotp; //dot product
        double[] qx, y, Y;

        for(int i = 1; i < Q.length; i++){
            y = Q[i];
            for (int j = 0; j < i; j++) {
                qx = Q[j]; // Q[j] (0--j<i) are normalized
                dotp = dot(y, qx);
                Y = sum(y, multiply(dotp, qx), -1); // q0: previous normalized vector
                Q[i] = normalize(Y);
            }
        }
        return Q;
    }

    public static double dot(double[] v, double[] w){
        assert v.length == w.length;
        double d = 0.0;
        for (int i = 0; i < v.length; i++)
            d += v[i] * w[i];
        return d;
    }

    public static double[] sum(double[] v, double[] w, int sign){
        assert v.length == w.length;
        double[] sum = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            sum[i] = v[i] + sign * w[i];
        }
        return sum;
    }

    public static Matrix inverseByAdjugate(Matrix matrix) {

        if (!matrix.isSquare()) return null;

        /*  INVERSE METHOD */

        // step 1: find the determinant of the matrix
        Matrix.findDeterminant(matrix, 2); // method of echelon form

        //step 2: find the adjugate matrix
        Matrix adjugate = Matrix.adjugate(matrix);

        // determinant of Inverse matrix: |A^-1| = 1/|A|
        adjugate.D = 1.0 / matrix.D; //

        //step 5: calculate the inverse = 1/|A| * adj(A)

        return adjugate.multiplyBy(1.0 / matrix.D);
    }

    public static Matrix adjugate(Matrix matrix) {
        Matrix adjugate = Matrix.cofactor(matrix);
        adjugate.entries = Matrix.transpose(adjugate.getEntries());
        return adjugate;
    }

    public static Matrix cofactor(Matrix matrix) {
        int rows = matrix.rows, cols = matrix.cols;
        Matrix cofactors = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Matrix cofactor = cofactor(matrix, i, j);
                Matrix.findDeterminant(cofactor, 1);
                cofactors.entries[i][j] = cofactor.D;
            }
        }
        /*Reciprocal Determinant or determinant of matrix formed by cofactors
         * D' = D^(n-1) */
//        cofactors.D = Math.pow(matrix.D, rows - 1);
        return cofactors;
    }
    public static Matrix cofactor(Matrix M, int crossRow, int crossCol) {
        /** Return the cofactor of the matrix when row=0 and col chosen are crossed*/
        assert crossRow <= M.rows && crossCol <= M.cols;

        Matrix cofactor = new Matrix(M.rows - 1, M.cols - 1);
        int ind = 0;

        for (int i = 0; i < M.rows; i++) {//row 0 crossed by default
            if (i != crossRow) {
                for (int j = 0; j < M.cols; j++) {//starting
                    if (j != crossCol) {
                        cofactor.entries[ind / cofactor.cols][ind % cofactor.cols] = M.entries[i][j] * getCofactorSign(i, j);
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

    public Matrix transpose(Matrix matrix){
        assert matrix != null;
        matrix.entries = Matrix.transpose(matrix.entries);
        return this;
    }
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        double[][] transpose = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                transpose[j][i] = matrix[i][j];
        return transpose;
    }

    public static void solveForSize2(Matrix M) {
        M.setD((M.getEntries()[0][0] * M.getEntries()[1][1]) - (M.getEntries()[0][1] * M.getEntries()[1][0]));

        //view
        STEPS.add("\uD83D\uDD21 = " + M.getCoefficient() + "((" + M.getEntries()[0][0] + " * " + M.getEntries()[1][1]
                + ") - (" + M.getEntries()[0][1] + " * " + M.getEntries()[1][0] + ")) = " + M.getD());
    }

/*    public static Fraction[][] doublesToFractions(double[][] matrixBody) {
        int rows = matrixBody.length, cols = matrixBody[0].length;

        Fraction[][] fbody = new Fraction[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fbody[i][j] = new Fraction(matrixBody[i][j]);
            }
        }
        return fbody;
    }*/

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private void fillEntries() {
        //matrix should be at least 4 chars like "a, b;"

        int start = 0;
        int end;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (j == cols - 1) {
                    //last col of non-last rows end with new line '\n'
                    if (i < rows - 1)
                        end = matrixText.indexOf("\n", start);
                        //last col of last row ends with no char
                    else
                        end = matrixText.length();
                } else
                    end = matrixText.indexOf(",", start);

                String stringNum = matrixText.substring(start, end);
                if (stringNum.isEmpty()) stringNum = "0";
                try {
                    this.entries[i][j] = Double.parseDouble(stringNum);
                } catch (NumberFormatException nfe) {
                    return;//body was not filled
                }
                start = end + 1;
            }
        }
    }

    private void readSize() {
        int rows = 1;//each row is ended by a '\n' except the last one
        int cols = 1;//each col is ended by a ',' except the last one
        for (int i = 1; i < matrixText.length(); i++) {
            if (matrixText.charAt(i) == '\n') rows++;

            //all columns are same for every row
            if (matrixText.charAt(i) == ',' && rows == 1) cols++;
        }
        this.rows = rows;
        this.cols = cols;
        this.entries = new double[rows][cols];
    }

    public boolean isSquare() {
        return (this.rows == this.cols);
    }

    //compute diagonals elements
    public double[] getMainDiagonal() {
        mainDiagonal = new double[Math.min(rows, cols)];//minSquareMatrixSize
        for (int i = 0, j = 0; i < mainDiagonal.length; i++, j++)
            if(onDraft) mainDiagonal[i] = last.entries[i][j];
            else mainDiagonal[i] = entries[i][j];
        return mainDiagonal;
    }

    public double[] getSecondDiagonal() {
        secondDiagonal = new double[Math.min(rows, cols)];//minSquareMatrixSize
        for (int i = 0, j = cols - 1; i < secondDiagonal.length; i++, j--) secondDiagonal[i] = entries[i][j];
        return secondDiagonal;
    }

    public Character getLabel() {
        return label;
    }

    public void setLabel(Character label) {
        this.label = label;
    }

    @Override
    public Matrix plus(Matrix matrix) {
        //Size of A should equal size of B
        if ((this.rows != matrix.rows) || (this.cols != matrix.cols)) return null;

        Matrix sum = new Matrix(this.rows, this.cols);

        for (int i = 0; i < sum.rows; i++)
            for (int j = 0; j < sum.cols; j++)
                sum.entries[i][j] = this.entries[i][j] + matrix.entries[i][j];

        return sum;
    }

    @Override
    public Matrix minus(Matrix matrix) {
        //Size of A should equal size of B
        if ((this.rows != matrix.rows) || (this.cols != matrix.cols)) return null;

        Matrix sum = new Matrix(this.rows, this.cols);

        for (int i = 0; i < sum.rows; i++)
            for (int j = 0; j < sum.cols; j++)
                sum.entries[i][j] = this.entries[i][j] - matrix.entries[i][j];

        return sum;
    }

    @Override
    public Matrix dot(Matrix matrix) {
        //cols of A should equal rows of B
        assert this.rows == matrix.rows && this.cols == matrix.cols;

        STEPS = new ArrayList<>();//list of steps that appeared in the process.

        //result matrix size is rows of A and cols of B
        Matrix prod = new Matrix(this.rows, matrix.cols);
        double m1, m2;
        int j;
        //cols of A == rows of B
        for (int i = 0; i < prod.getRows(); i++) {

            // view
            String temp = "Row" + (i + 1) + " x Col";

            for (j = 0; j < prod.getCols(); j++) {
                //view
                temp += (j + 1) + " ➠ ";

                for (int k = 0; k < this.cols; k++) {//'k' keeps track of the corresponding col in A and row in B
                    prod.entries[i][j] += entries[i][k] * matrix.entries[k][j];

                    // view
                    temp += "(" + entries[i][k] + ") * " + "(" + matrix.entries[k][j] + ")" + ((k < cols - 1) ? " + " : "");
                }

                // view
                STEPS.add(temp + " = " + prod.entries[i][j]);
            }
        }
        return prod;
    }

    @Override
    public double[] multiplyRowBy(int row, double multiplier) {
        double[] multipliedRow = new double[this.entries[0].length];

        for (int i = 0; i < multipliedRow.length; i++)//for all columns
            multipliedRow[i] = this.entries[row][i] * multiplier;

        return multipliedRow;
    }

    public static double[] multiply(double m, double...vector){
        double[] p = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            p[i] = vector[i] * m;
        return p;
    }

    @Override
    public boolean isUpperTriangular() {
        return true;
    }

    public Matrix multiplyBy(double... values) {
        //result matrix size is rows of A and cols of B
        double[][] prod = onDraft? last.entries : this.entries;
        //cols of A == rows of B
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                for (double v : values) prod[i][j] *= v;

        return onDraft? last : this;
    }

    public double productOf(double...values){
        double product = 1.0;
        for (double v: values) product *= v;
        return product;
    }
    @Override
    public boolean equalsTo(Matrix matrix) {
        if ((this.rows != matrix.rows) || (this.cols != matrix.cols)) return false;

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                if (!Objects.equals(this.entries[i][j], matrix.entries[i][j])) return false;

        return true;
    }

    @Override
    public Matrix negate() {

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.entries[i][j] *= -1;

        return this;
    }

    public Matrix with(Require...R){
        for (Require v : R)
            switch (v) {
                case D: case DETERMINANT:
                    this.D = findDeterminant(this.entries); break;
                case T: case TRANSPOSE:
                    this.T = transpose(this); break;
                case I: case INVERSE:
                    this.I = invert(); break;
                case H: case HESSIAN:
                    break;
            }
        return this;
    }

    @Override
    public double modulus() {
        //vector modulus <=>
        return this.D;
    }

    public double getD() {
        return D;
    }

    public void setD(double D) {
        this.D = D;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public boolean mainDiagonalHasZeros() {
        for (Double c : this.getMainDiagonal())
            if (c == 0) return true;
        return false;
    }

    public double[][] getEntries() {
        return this.entries;
    }

    public static double findDeterminant(double[][] entries){
        LUDecomposition.set(entries);
        return LUDecomposition.getD();
    }

    public static void findDeterminant(Matrix matrix, int method) {
        STEPS = new ArrayList<>();//list of steps that appeared in the process.
        MATS = new ArrayList<>();//list of MATRICES that appeared in the process.
        switch (method) {
            case 0:
                DetByCofactors.findDeterminant(matrix);
                break;
            case 1:
            default:
                LUDecomposition.set(matrix.entries);
                matrix.setD(LUDecomposition.getD());
                break;
        }
    }
    public void permuteRows(int row1, int row2) {
        double[] tempRow = this.getEntries()[row1];
        this.getEntries()[row1] = this.getEntries()[row2];
        this.getEntries()[row2] = tempRow;

        this.negate();
    }

    public void permuteCols(int col1, int col2) {
        for (int i = 0; i < this.getRows(); i++)
            this.getEntries()[i][col1] += (this.getEntries()[i][col2] - (this.getEntries()[i][col2] = this.getEntries()[i][col1]));

        this.negate();
    }

    public Matrix declare(String var, double val){
        draft.put(var, val);
        return this;
    }

    public double valueOf(String var) {
        return draft.get(var);
    }
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            text.append("   |");

            for (int j = 0; j < this.cols; j++)
                text.append("   ").append(this.entries[i][j]).append("   ");

            text.append("|\n");
        }
        return text.toString();
    }

    /* IN-PLACE METHODS: MODIFY THE CURRENT MATRIX AND GIVE CHAINING OF OTHER CHANGES */

    public Matrix invert(){

        Matrix inverse = Matrix.inverseByAdjugate(this);
        assert inverse != null;
        inverse.D = 1.0 / this.D;

        return inverse;
    }

    public Matrix getT(){
        return this.T;
    }

    public Matrix getI(){
        return this.I;
    }

    public Matrix getH(){
        return this.H;
    }

    public Matrix T(){
        //check also whether T, I, H ... required
        if(onDraft){
            last.entries = Matrix.transpose(last.entries);
            return last;
        }
        else {
            this.entries = Matrix.transpose(this.entries);
            this.rows = entries.length;
            this.cols = entries[0].length;
            return this;
        }
        //determinant remains unchanged
    }

    public Matrix computeCofactors() {
        if(onDraft) {
            last.entries = Matrix.cofactor(last).entries;
            return last;
        }
        else {
            Matrix c = Matrix.cofactor(this);
            this.entries = c.entries;
            this.D = c.D;
            return this;
        }
    }

    public Matrix toEchelonForm(){
        if(onDraft) {
            LUDecomposition.set(last.entries);
            last.entries = LUDecomposition.getEchelonForm();
            return last;
        } else {
            LUDecomposition.set(this.entries);
            this.entries = LUDecomposition.getEchelonForm();
            return this;
        }
        //determinant remains unchanged
    }

    public Matrix onDraft(){
        this.onDraft = true;
        if(last == null) {
            last = Matrix.fromEntries(original);
        }
        return last;
    }

    public Matrix onPaper(){
        this.onDraft = false;
        return this;
    }

    public static void print(double[][] array, String label){
        System.out.println("v---------------------------v\n" + label + "\nv---------------------------v");

        for(double[] row : array){
            System.out.print("|");
            for(double e : row)
                System.out.print(e + "    ");
            System.out.println("|\n");
        }
    }

    public static double[][] copyOf(double[][] entries){
        int rows = entries.length, cols = entries[0].length;
        double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(entries[i], 0, copy[i], 0, cols);
        return copy;
    }

}