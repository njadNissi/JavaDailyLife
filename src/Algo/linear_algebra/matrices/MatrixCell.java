package Algo.linear_algebra.matrices;

public class MatrixCell {
    private final double value;
    private final int row;
    private final int col;

    public MatrixCell(int row, int col, double value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public double value() {
        return value;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    @Override
    public String toString() {
        return "M(" + (row + 1) + ", " + (col + 1) + " = " + value + ')';
    }
}