package Labs.Lab_2;

import java.util.*;

//16. Каждая входная строка представляет собой слова, разделенные
//        одним или несколькими пробелами и знаками препинания. Для
//        каждой входной строки: найти и напечатать анаграммы (пара
//        слов такая, что при прочтении одного слова из пары в
//        обратном направлении образуется другое слово пары, например:
//        бар, раб).

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String s = in.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(s, " ,.!?;:()");
            ArrayList<String> words = new ArrayList<String>();
            while (tokenizer.hasMoreTokens()) {
                words.add(tokenizer.nextToken());
            }
            boolean anagramWasFound = false;
            for (int i = 0; i < words.size() - 1; i++) {
                for (int j = i + 1; j < words.size(); j++) {
                    if (isAnagram(words.get(i), words.get(j))) {
                        anagramWasFound = true;
                        System.out.printf("(%s, %s) ", words.get(i), words.get(j));
                    }
                }
            }
            if (!anagramWasFound) {
                System.out.print("No anagrams were found.\n");
            } else {
                System.out.print("\n");
            }

        }
    }

    private static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sLet = s.toCharArray();
        char[] tLet = t.toCharArray();
        Arrays.sort(sLet);
        Arrays.sort(tLet);
        for (int i = 0; i < sLet.length; i++) {
            if (sLet[i] != tLet[i]) {
                return false;
            }
        }
        return true;
    }
}