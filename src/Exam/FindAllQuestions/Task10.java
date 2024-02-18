package Exam.FindAllQuestions;

//Найти все вопросительные предложения и вывести каждое с новой строки

import java.util.ArrayList;
import java.util.Scanner;

public class Task10 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        for (String line : lines) {
            processLine(line);
        }
    }

    public static void processLine(String line) {
        int pos = 0;
        while (true) {
            pos = line.indexOf('?', pos);
            if (pos == -1) {
                break;
            }
            int j = pos - 1;
            while (j > 0 && !isEnd(line.charAt(j))) {
                j--;
            }
            if (j == 0 && !isEnd(line.charAt(0))) {
                j--;
            }
            System.out.println(line.substring(j+1, pos+1).trim());
            pos++;
        }
    }

    public static boolean isEnd(char c) {
        return c == '.' || c == '?' || c == '!';
    }
}
