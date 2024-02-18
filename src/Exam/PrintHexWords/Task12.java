package Exam.PrintHexWords;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Вывести слова, состоящие только из цифр, в шестнадцатиричной форме

public class Task12 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> words = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            StringTokenizer t = new StringTokenizer(line, " !;:.,\"'?/");
            while(t.hasMoreTokens()) {
                String word = t.nextToken();
                if (onlyDigits(word)) {
                    words.add(word);
                }
            }
        }
        for (String w : words) {
            System.out.println(Long.toHexString(Long.parseLong(w)));
        }
    }

    public static boolean onlyDigits(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isDigit(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
