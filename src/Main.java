import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создание комплексных матриц для демонстрации возможностей

        // Матрица 2x2 из массива
        Complex[][] array1 = {
                {new Complex(1, 2), new Complex(3, 4)},
                {new Complex(5, 6), new Complex(7, 8)}
        };
        ComplexMatrix matrix1 = new ComplexMatrix(array1);

        // Матрица 2x2 из списка списков
        ComplexMatrix matrix2 = new ComplexMatrix(List.of(
                List.of(new Complex(2, -1), new Complex(0, 1)),
                List.of(new Complex(1, 0), new Complex(2, 3))
        ));

        // Печать исходных матриц
        System.out.println("Матрица 1:");
        System.out.println(matrix1);
        System.out.println("Матрица 2:");
        System.out.println(matrix2);

        // Сложение матриц
        ComplexMatrix sum = ComplexMatrix.add(matrix1, matrix2);
        System.out.println("Сумма матриц (matrix1 + matrix2):");
        System.out.println(sum);

        // Вычитание матриц
        ComplexMatrix diff = ComplexMatrix.subtract(matrix1, matrix2);
        System.out.println("Разность матриц (matrix1 - matrix2):");
        System.out.println(diff);

        // Умножение матриц
        ComplexMatrix product = ComplexMatrix.multiply(matrix1, matrix2);
        System.out.println("Произведение матриц (matrix1 * matrix2):");
        System.out.println(product);

        // Транспонирование матрицы
        ComplexMatrix transposed = ComplexMatrix.transpose(matrix1);
        System.out.println("Транспонированная матрица 1:");
        System.out.println(transposed);

        // Вычисление детерминанта матрицы
        Complex det = matrix1.determinant();
        System.out.println("Детерминант матрицы 1:");
        System.out.println(det);

        // Обратная матрица
        try {
            ComplexMatrix inverse = matrix1.inverse();
            System.out.println("Обратная матрица 1:");
            System.out.println(inverse);
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Деление матриц (matrix1 / matrix2)
        try {
            ComplexMatrix division = ComplexMatrix.divide(matrix1, matrix2);
            System.out.println("Деление матриц (matrix1 / matrix2):");
            System.out.println(division);
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
