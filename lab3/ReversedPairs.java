import java.util.ArrayList;
import java.util.HashSet;

public class ReversedPairs {
        String reverse (String a) {
            String c = new String("");
            for (int i=a.length()-1;i>=0;--i)
            {
                c=c+a.charAt(i);
            }
            return c;
        }

        boolean isReverse (String a, String b) {
            return a.equalsIgnoreCase(reverse(b));
        }

        HashSet<String> reversedPairs(ArrayList<String> text) {
            ArrayList<String> words = new ArrayList<String>();
            for (String line: text)
            {
                String[]lineWords=line.split("[^\\p{L}]+");
                for (String word: lineWords)
                {
                    if (!word.isEmpty() && word!=null)
                    words.add(word);
                }
            }
            HashSet<String> suitable = new HashSet<String>();
            for (int i=0;i<words.size()-1;++i)
            {
                for (int j=i+1;j< words.size();++j)
                {
                    if (isReverse(words.get(i),words.get(j)))
                        suitable.add(words.get(i));
                }
            }
            return suitable;
        }
}