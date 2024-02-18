package Exam.ReplaceWithCapitalLetters;

import java.util.ArrayList;
import java.util.Scanner;

// Заменить буквы на заглавные в восклицательных предложениях.

public class Task9 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        for (String line : lines) {
            System.out.println(processLine(line));
        }
    }

    public static String processLine(String line) {
        int pos = 0;
        StringBuilder sb = new StringBuilder(line);
        while (pos < line.length()) {
            pos = line.indexOf('!', pos);
            if (pos != -1) {
                int j = pos-1;
                while (j >= 0 && line.charAt(j) != '.' && line.charAt(j) != '?' && line.charAt(j) != '!') {
                    sb.replace(j, j+1,
                            Character.toString(Character.toUpperCase(line.charAt(j))));
                    j--;
                }
                pos++;
            } else {
                break;
            }
        }
        return sb.toString();
    }
}
