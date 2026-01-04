import java.util.Scanner;
import java.text.NumberFormat;

public class Main {
    public static void readMatrix (int[][] Ma,int m, int n)
    {
        System.out.println("Введите элементы матрицы");
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j)
                Ma[i][j] = in.nextInt();
        }
    }

    public static void readMatrix (double[][] Ma,int m, int n)
    {
        System.out.println("Введите элементы матрицы");
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j)
                Ma[i][j] = in.nextDouble();
        }
    }

    public static void printMatrix(double[][]Ma,int m, int n) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(1);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(formatter.format(Ma[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Список доступных задач:");
        System.out.println("14. Найти минимальное из чисел, встречающихся в заданной матрице ровно один раз");
        System.out.println("28. Определить, является ли действительная матрица размера mxn ортонормированной");
        System.out.println("42. Соседями элемента aij в матрице назовем элементы akl с i-1<=k<=i+1, j-1<=l<=j+1,(k,l)!=(i,j). Операция сглаживания матрицы дает новую матрицу того же размера, \n" + "каждый элемент которой получается, как среднее арифметическое имеющихся соседей соответствующего элемента исходной матрицы.\n" +"Построить результат сглаживания заданной вещественной матрицы");
        System.out.println();
        System.out.print("Выберите номер задачи, с который желаете поработать: ");
        Scanner in = new Scanner(System.in);
        int num;
        while (true) {
            System.out.print("Выберите номер задачи (14, 28 или 42): ");
            num = in.nextInt();
            if (num == 14 || num == 28 || num == 42) {
                break;
            }
            System.out.println("Введён неверный номер задачи!");
        }
        System.out.println();
        if (num==28)
            System.out.println("Внимание! Ортонормированной может быть только квадратная матрица");
        System.out.println("Введите количество строк матрицы");
        int m = in.nextInt();
        System.out.println("Введите количество столбцов матрицы");
        int n = in.nextInt();
        switch (num)
        {
            case 14:
            {
                int[][] Ma = new int[m][n];
                readMatrix(Ma,m,n);
                Task14 solution = new Task14();
                System.out.println();
                try {
                    System.out.println("Минимальный элемент, встречающийся в матрице 1 раз - " + solution.findMin(Ma, m, n));
                }
                catch (IllegalStateException e)
                {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 28:
            {
                if (n!=m)
                {
                    System.out.println();
                    System.out.println("Матрица не может быть ортонормированной, т.к. не квадратная");
                    break;
                }
                int[][] Ma = new int[m][n];
                readMatrix(Ma,m,n);
                Task28 solution2 = new Task28();
                System.out.println();
                if (solution2.isOrthonormal(Ma, m, n))
                    System.out.println("Матрица ортонормированная");
                else
                    System.out.println("Матрица не ортонормированная");
                break;
            }
            case 42:
            {
                double[][] Ma = new double[m][n];
                readMatrix(Ma,m,n);
                Task42 solution3 = new Task42();
                double[][] Res = solution3.Smoothing(Ma,m,n);
                System.out.println();
                System.out.println("Результат \"сглаживания\" матрицы:");
                printMatrix(Res,m,n);
                break;
            }
        }
    }
}
