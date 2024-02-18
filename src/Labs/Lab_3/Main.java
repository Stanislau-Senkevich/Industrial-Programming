package Labs.Lab_3;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

//21.     Ввести с консоли n - размерность матрицы a[n][n]. Задать
//        значения элементов матрицы в интервале значений от -n до n с
//        помощью датчика случайных чисел. Своими главной и побочной
//        диагоналями матрица разбита на четыре треугольные части.
//        Найти среднее арифметическое элементов матрицы в каждом из
//        треугольников без учёта диагональных элементов. Распечатать
//        исходную матрицу и результат.

public class Main {
    public static void main(String[] args) {
        Random rand = new Random((new Date()).getTime());
        System.out.println("Enter positive n: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        while (n < 1) {
            System.out.println("Enter POSITIVE n: ");
            n = in.nextInt();
        }
        in.close();
        int[][] matrix = new int[n][n];
        System.out.println("Matrix:");
        int[] sums = new int[4]; // 0 - up, 1 - right, 2 - down, 3 - left

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rand.nextInt(-n, n);
                System.out.printf("%d\t", matrix[i][j]);
                if (j > i && i < n-j-1) {
                    sums[0] += matrix[i][j];
                } else if (j > i && i > n-j-1) {
                    sums[1] += matrix[i][j];
                } else if (j < i && i > n-j-1) {
                    sums[2] += matrix[i][j];
                } else if (j < i && i < n-j-1) {
                    sums[3] += matrix[i][j];
                }
            }
            System.out.print("\n");
        }

        if (n < 3) {
            System.out.println("There are no elements in <<triangle>> parts of the matrix.");
            return;
        }

        double amount;
        if (n % 2 == 0) {
            amount = (n*n-2*n)/4.;
        } else {
            amount = (n*n-2*n+1)/4.;
        }
        System.out.printf("Up: %f\nRight: %f\nDown: %f\nLeft: %f",
                sums[0]/amount, sums[1]/amount, sums[2]/amount, sums[3]/amount);
    }
}