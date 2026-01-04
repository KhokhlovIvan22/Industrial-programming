/*42. Соседями элемента ajj в матрице назовем элементы aki с i-1<=k<=i+1, j-1<=l<=j+1,(k,l)!=(i,j).
Операция сглаживания матрицы дает новую матрицу того же размера, каждый элемент которой получается,
как среднее арифметическое имеющихся соседей соответствующего элемента исходной матрицы.
Построить результат сглаживания заданной вещественной матрицы.
* */
public class Task42 {
    public static double[][] Smoothing(double[][] Ma, int m, int n) {
        double[][] Res = new double[m][n];
        double sum;
        int neighboursAmount;
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j) {
                sum = 0.0;
                neighboursAmount = 0;
                for (int cur_row = i - 1; cur_row <= i + 1; ++cur_row) {
                    for (int cur_col = j - 1; cur_col <= j + 1; ++cur_col) {
                        if (cur_row == i && cur_col == j) continue;
                        if (cur_row >= 0 && cur_row < m && cur_col >= 0 && cur_col < n) {
                            sum += Ma[cur_row][cur_col];
                            neighboursAmount++;
                        }
                    }
                }
                Res[i][j] = sum / neighboursAmount;
            }
        return Res;
    }
}