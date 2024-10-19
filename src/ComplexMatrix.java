import java.util.List;

public class ComplexMatrix {
    private final Complex[][] matrix;
    private final int rows;
    private final int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new Complex[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Complex(0, 0);
            }
        }
    }

    public ComplexMatrix(Complex[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = new Complex[rows][cols];

        // Копирование данных из переданного массива
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public ComplexMatrix(List<List<Complex>> listMatrix) {
        this.rows = listMatrix.size();
        this.cols = listMatrix.get(0).size();
        this.matrix = new Complex[rows][cols];

        // Копирование данных из переданного списка
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = listMatrix.get(i).get(j);
            }
        }
    }

    public void setElement(int row, int col, Complex value) {
        matrix[row][col] = value;
    }

    public Complex getElement(int row, int col) {
        return matrix[row][col];
    }

    public static ComplexMatrix add(ComplexMatrix m1, ComplexMatrix m2) {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера для сложения");
        }

        ComplexMatrix result = new ComplexMatrix(m1.rows, m1.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m1.cols; j++) {
                result.setElement(i, j, Complex.add(m1.getElement(i, j), m2.getElement(i, j)));
            }
        }

        return result;
    }

    public static ComplexMatrix subtract(ComplexMatrix m1, ComplexMatrix m2) {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера для вычитания");
        }

        ComplexMatrix result = new ComplexMatrix(m1.rows, m1.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m1.cols; j++) {
                result.setElement(i, j, Complex.subtract(m1.getElement(i, j), m2.getElement(i, j)));
            }
        }

        return result;
    }

    public static ComplexMatrix multiply(ComplexMatrix m1, ComplexMatrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("Количество столбцов первой матрицы должно совпадать с количеством строк второй матрицы для умножения");
        }

        ComplexMatrix result = new ComplexMatrix(m1.rows, m2.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < m1.cols; k++) {
                    sum = Complex.add(sum, Complex.multiply(m1.getElement(i, k), m2.getElement(k, j)));
                }
                result.setElement(i, j, sum);
            }
        }

        return result;
    }

    public static ComplexMatrix transpose(ComplexMatrix m) {
        ComplexMatrix result = new ComplexMatrix(m.cols, m.rows);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                result.setElement(j, i, m.getElement(i, j));
            }
        }

        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Матрица должна быть квадратной для вычисления детерминанта.");
        }
        return determinant(matrix, rows);
    }

    private Complex determinant(Complex[][] mat, int n) {
        if (n == 1) {
            return mat[0][0];
        }

        Complex det = new Complex(0, 0);
        Complex[][] temp = new Complex[n - 1][n - 1];

        for (int p = 0; p < n; p++) {
            getCofactor(mat, temp, 0, p, n);
            Complex sign = new Complex((p % 2 == 0) ? 1 : -1, 0);
            det = Complex.add(det, Complex.multiply(sign, Complex.multiply(mat[0][p], determinant(temp, n - 1))));
        }
        return det;
    }

    private void getCofactor(Complex[][] mat, Complex[][] temp, int row, int col, int n) {
        int i = 0, j = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (r != row && c != col) {
                    temp[i][j++] = mat[r][c];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public ComplexMatrix inverse() {
        Complex det = determinant();
        if (det.getReal() == 0 && det.getImag() == 0) {
            throw new ArithmeticException("Матрица необратима (детерминант равен нулю).");
        }

        ComplexMatrix adj = adjoin();
        ComplexMatrix inverse = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                inverse.setElement(i, j, Complex.divide(adj.getElement(i, j), det));
            }
        }
        return inverse;
    }

    private ComplexMatrix adjoin() {
        ComplexMatrix adj = new ComplexMatrix(rows, cols);
        if (rows == 1) {
            adj.setElement(0, 0, new Complex(1, 0));
            return adj;
        }

        Complex[][] temp = new Complex[rows - 1][cols - 1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                getCofactor(matrix, temp, i, j, rows);
                Complex sign = new Complex(((i + j) % 2 == 0) ? 1 : -1, 0);
                adj.setElement(j, i, Complex.multiply(sign, determinant(temp, rows - 1)));
            }
        }
        return adj;
    }

    public static ComplexMatrix divide(ComplexMatrix m1, ComplexMatrix m2) {
        return multiply(m1, m2.inverse());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int maxWidth = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String elementStr = matrix[i][j].toString();
                maxWidth = Math.max(maxWidth, elementStr.length());
            }
        }

        String format = "%-" + (maxWidth + 2) + "s";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format(format, matrix[i][j].toString()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}