package squareMatrix;

import matrix.Matrix;
import complex.Complex;
public class SquareMatrix extends Matrix {
    public SquareMatrix(String name, Complex[][] matrix) {
        super(name, matrix);
    }

    public Complex determinant(int leftTop, boolean[] excluded) {
        Complex s = new Complex(0);
        if (leftTop == dimx - 1) {
            for (int i = 0; i < dimx; i++) {
                if (!excluded[i]) {
                    return matrix[leftTop][i];
                }
            }
        }

        int pos = 0;
        for  (int i = 0; i < dimx; i++) {
            if (excluded[i] == true) continue;
            Complex sign = new Complex((pos) % 2 == 0 ? 1 : -1);
            excluded[i] = true;
            s = Complex.add(s, Complex.multiply(determinant(leftTop+1, excluded), Complex.multiply(sign, matrix[leftTop][i])));
            excluded[i] = false;
            pos++;
        }
        return s;
    }
}
