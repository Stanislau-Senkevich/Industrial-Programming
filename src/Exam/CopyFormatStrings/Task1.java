package Exam.CopyFormatStrings;

// Создать программу, которая копирует из выходного файла и форматирует строки.

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Task1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String str = in.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(str, " \t");
            ArrayList<String> words = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                words.add(tokenizer.nextToken());
            }
            int i = 0;
            String line = "";
            while (i < words.size()) {
                if (line.length() + words.get(i).length() > 80) {
                    System.out.println(line);
                    line = "";
                }
                if (    words.get(i).equals(",") ||
                        words.get(i).equals(".") ||
                        words.get(i).equals("!") ||
                        words.get(i).equals("?") ||
                        words.get(i).equals(";") ||
                        words.get(i).equals(":") ||
                        words.get(i).equals("\"") ||
                        words.get(i).equals("'") ||
                        line.isEmpty()) {
                    line += words.get(i);
                } else {
                    line += " " + words.get(i);
                }
                i++;
            }
            System.out.println(line.replace('\t', ' '));
        }
    }
}