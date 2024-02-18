package Exam.PrintWordsWithCapital;

//Вывести слова с заглавной буквы

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Task8 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList <String> words = new ArrayList<>();
        String word = "";
        while (in.hasNextLine()) {
            StringTokenizer t = new StringTokenizer(in.nextLine(), " .!?,;:\n\r\t");
            while (t.hasMoreTokens()) {
                word = t.nextToken();
                if (Character.isUpperCase(word.charAt(0))) {
                    words.add(word);
                }
            }
        }
        for (String w : words) {
            System.out.println(w);
        }
    }
}
