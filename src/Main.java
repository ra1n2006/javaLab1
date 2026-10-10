import matrix.Matrix;
import complex.Complex;
import squareMatrix.SquareMatrix;
import java.util.ArrayList;
import java.util.Scanner;


class MatrixList extends ArrayList<Matrix> {
    public MatrixList() {
        super();
    }

    public Matrix findMatrixByName(String name) {
        for (Matrix matrix : this) {
            if (matrix.getName().equals(name)) {
                return matrix;
            }
        }
        return null;
    }

    public void printAll() {
        System.out.println("созданные матрицы: ");
        for (Matrix matrix : this) {
            System.out.printf(matrix.getName() + " ");
        }
        System.out.println();
    }
}

public class Main {

    private static void printCmds() {
        System.out.println("Доступные команды: задать матрицу, умножить матрицы, " +
                "сложить матрицы, посчитать определитель, транспонировать матрицу, разделить матрицы");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MatrixList matrixList = new MatrixList();
        String cmd = "";

        do {
            printCmds();
            System.out.println("Введите команду");
            cmd = sc.nextLine();

            switch (cmd) {
                case "задать матрицу": {
                    System.out.println("введите имя матрицы");
                    String name = sc.nextLine();

                    System.out.println("введите число строк матрицы");
                    int dimx = sc.nextInt();

                    System.out.println("введите число столбцов матрицы");
                    int dimy = sc.nextInt();
                    sc.nextLine();

                    Complex[][] matrix = new Complex[dimx][dimy];

                    System.out.println("введите элементы матрицы");
                    for (int i = 0; i < dimx; i++) {
                        String[] row = sc.nextLine().split(" ");
                        for (int j = 0; j < dimy; j++) {
                            matrix[i][j] = Complex.convertStringToComplex(row[j]);
                        }
                    }

                    if (dimx != dimy) {
                        Matrix m = new Matrix(name, matrix);
                        matrixList.add(m);
                    } else {
                        SquareMatrix m = new SquareMatrix(name, matrix);
                        matrixList.add(m);
                    }

                    System.out.println("Матрица успешно сохранена");
                    break;
                }

                case "умножить матрицы": {
                    System.out.println("напишите названия матриц, которые хотите умножить");
                    System.out.println("созданные матрицы:");
                    matrixList.printAll();

                    System.out.println("первая матрица:");
                    String name1 = sc.nextLine();

                    System.out.println("вторая матрица:");
                    String name2 = sc.nextLine();

                    Matrix mx1 = matrixList.findMatrixByName(name1);
                    Matrix mx2 = matrixList.findMatrixByName(name2);

                    if (mx1 == null || mx2 == null) {
                        System.out.println("Одна из матриц не найдена");
                        break;
                    }

                    Complex[][] multiplyResult = mx1.multiply(mx2.getMatrix());

                    if (multiplyResult == null) {
                        System.out.println("Матрицы нельзя перемножить");
                        break;
                    }

                    new Matrix(multiplyResult).printMatrix();
                    break;
                }

                case "сложить матрицы": {
                    System.out.println("напишите названия матриц, которые хотите сложить");
                    System.out.println("созданные матрицы:");
                    matrixList.printAll();

                    System.out.println("Первая матрица:");
                    String name1 = sc.nextLine();

                    System.out.println("Вторая матрица:");
                    String name2 = sc.nextLine();

                    Matrix mx1 = matrixList.findMatrixByName(name1);
                    Matrix mx2 = matrixList.findMatrixByName(name2);

                    if (mx1 == null || mx2 == null) {
                        System.out.println("Одна из матриц не найдена");
                        break;
                    }

                    Complex[][] sumResult = mx1.sum(mx2);

                    if (sumResult == null) {
                        System.out.println("Матрицы нельзя сложить");
                        break;
                    }

                    new Matrix(sumResult).printMatrix();
                    break;
                }

                case "посчитать определитель": {
                    System.out.println("напишите название матрицы, у которой надо вычислить определитель");
                    matrixList.printAll();

                    String name = sc.nextLine();
                    Matrix foundMatrix = matrixList.findMatrixByName(name);

                    if (foundMatrix instanceof SquareMatrix) {
                        SquareMatrix mx = (SquareMatrix) foundMatrix;
                        System.out.println(mx.determinant(0, new boolean[mx.getDimx()]).convertComplexToNormal());
                    } else {
                        System.out.println("Определитель можно вычислить только у квадратной матрицы");
                    }
                    break;
                }

                case "транспонировать матрицу": {
                    System.out.println("напишите название матрицы, которую нужно транспонировать");
                    matrixList.printAll();

                    String name = sc.nextLine();
                    Matrix mx = matrixList.findMatrixByName(name);

                    if (mx == null) {
                        System.out.println("Матрица не найдена");
                        break;
                    }

                    new Matrix(mx.transpose()).printMatrix();
                    break;
                }

                case "разделить матрицы": {
                    System.out.println("напишите названия матриц, которые хотите разделить");
                    System.out.println("созданные матрицы:");
                    matrixList.printAll();

                    System.out.println("первая матрица:");
                    String name1 = sc.nextLine();

                    System.out.println("вторая матрица:");
                    String name2 = sc.nextLine();

                    Matrix mx1 = matrixList.findMatrixByName(name1);
                    Matrix mx2 = matrixList.findMatrixByName(name2);

                    if (mx1 != null && mx2 instanceof SquareMatrix) {
                        Complex[][] divideResult = mx1.divide((SquareMatrix) mx2);

                        if (divideResult == null) {
                            System.out.println("Деление невозможно");
                            break;
                        }

                        new Matrix(divideResult).printMatrix();
                    } else {
                        System.out.println("Деление невозможно");
                    }
                    break;
                }

                default:
                    if (!cmd.equals("закончить")) {
                        System.out.println("Неизвестная команда");
                    }
                    break;
            }
        } while (!cmd.equals("закончить"));
    }
}