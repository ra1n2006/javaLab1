package matrix;

import complex.Complex;
import squareMatrix.SquareMatrix;

public class Matrix {
    protected final String name;
    protected Complex[][] matrix;
    protected int dimx;
    protected int dimy;

    public Matrix(Complex[][] matrix) {
        this.name = "";
        this.matrix = matrix;
        this.dimx = matrix.length;
        this.dimy = matrix[0].length;
    }

    public Matrix(String name, Complex[][] matrix) {
        this.name = name;
        this.matrix = matrix;
        this.dimx = matrix.length;
        this.dimy = matrix[0].length;
    }

    public Complex[][] getMatrix() {
        return matrix;
    }

    public int getDimx() {
        return dimx;
    }

    public int getDimy() {
        return dimy;
    }

    public Complex[][] sum(Matrix matrix1) {
        if (this.dimx != matrix1.dimx || this.dimy != matrix1.dimy) {
            return null;
        }
        Complex[][] res = new Complex[dimx][dimy];
        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < dimy; j++) {
                res[i][j] = Complex.add(matrix1.matrix[i][j], matrix[i][j]);
            }
        }
        return res;
    }
    public Complex[][] multiply(Matrix secondMatrix) {
        Complex[][] matrix1 = secondMatrix.matrix;
        if (dimy != matrix1.length) {
            return null;
        }

        Complex[][] res = new Complex[dimx][matrix1[0].length];

        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                res[i][j] = new Complex(0);
                for (int k = 0; k < dimy; k++) {
                    res[i][j] = Complex.add(
                            res[i][j],
                            Complex.multiply(matrix[i][k], matrix1[k][j])
                    );
                }
            }
        }

        return res;
    }

    public Complex[][] divide(SquareMatrix squareMatrix) {
        Complex det = squareMatrix.determinant(
                0,
                new boolean[squareMatrix.getDimx()]
        );
        if (det.getRealPart() == 0 && det.getImaginaryPart() == 0) {
            return null;
        }

        int n = squareMatrix.getDimx();
        Complex[][] dmatrix = new Complex[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Complex[][] minor = new Complex[n - 1][n - 1];
                int minorRow = 0;
                for (int row = 0; row < n; row++) {
                    if (row == i) {
                        continue;
                    }
                    int minorColumn = 0;
                    for (int column = 0; column < n; column++) {
                        if (column == j) {
                            continue;
                        }
                        minor[minorRow][minorColumn] =
                                squareMatrix.matrix[row][column];
                        minorColumn++;
                    }
                    minorRow++;
                }
                SquareMatrix minorMatrix = new SquareMatrix("", minor);
                Complex value = minorMatrix.determinant(
                        0,
                        new boolean[n - 1]
                );
                if ((i + j) % 2 != 0) {
                    value = Complex.multiply(new Complex(-1), value);
                }

                dmatrix[i][j] = value;
            }
        }

        Complex[][] transposed = new Complex[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposed[j][i] = dmatrix[i][j];
            }
        }
        Complex[][] inverse = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = Complex.divide(transposed[i][j], det);
            }
        }
        return multiply(new Matrix(inverse));
    }

    public Complex[][] transpose() {
        Complex[][] transposed = new Complex[dimy][dimx];
        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < dimy; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    public void printMatrix() {
        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < dimy; j++) {
                System.out.printf(matrix[i][j].convertComplexToNormal() + " ");

            }
            System.out.print("\n");
        }
    }

    public String getName() {
        return name;
    }
}
