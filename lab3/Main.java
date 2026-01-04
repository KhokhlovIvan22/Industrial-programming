import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите текст. Признак конца ввода - пустая строка");
        ArrayList<String> text = new ArrayList<String>();
        String line;
        while (true)
        {
            line = in.nextLine();
            if (line.isEmpty()) break;
            text.add(line);
        }
        ReversedPairs solution = new ReversedPairs();
        HashSet<String> result = solution.reversedPairs(text);
        System.out.println();
        if (result.isEmpty())
            System.out.println("Не найдено пар слов-обращений");
        else
            for (String word: result) {
            System.out.println(word.toLowerCase() + " - " + solution.reverse(word).toLowerCase());
        }
    }
}
