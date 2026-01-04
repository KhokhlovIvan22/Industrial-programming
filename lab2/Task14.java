//14. Найти минимальное из чисел, встречающихся в заданной матрице ровно один раз.
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Task14 {
    int findMin(int[][]Ma,int m, int n) {
        ArrayList<Integer> uniques = new ArrayList<Integer>();
        for (int i=0;i<m;++i)
        {
            for (int j=0;j<n;++j) {
                if (uniques.contains(Ma[i][j]))
                    uniques.remove(Integer.valueOf(Ma[i][j]));
                else
                    uniques.add(Ma[i][j]);
            }
        }
        if (uniques.isEmpty()) {
            throw new IllegalStateException("В матрице нет элементов, встречающихся ровно один раз");
        }
        return Collections.min(uniques);
    }
}
