/*28. Определить, является ли действительная матрица размера mxn ортонормированной,
т.е. такой, в которой скалярное произведение каждой пары различных строк равно 0,
а скалярное произведение каждой строки на себя равно 1. Вывести на экран соответствующее сообщение.
 */
public class Task28 {
    boolean isOrthonormal(int[][] Ma, int m, int n) {
        int sum = 0;
        for (int i = 0; i < m; ++i) { //проверка на нормированность
            sum = 0;
            for (int p = 0; p < n; ++p)
                sum += Ma[i][p] * Ma[i][p];
            if (sum != 1)
                return false;
        }
        for (int i = 0; i < m - 1; ++i) { //проверка на ортогональность
            for (int j = i + 1; j < m; ++j) {
                sum = 0;
                for (int p = 0; p < n; p++)
                    sum += Ma[i][p] * Ma[j][p];
                if (sum != 0)
                    return false;
            }
        }
        return true;
    }
}