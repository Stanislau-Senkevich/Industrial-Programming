package Exam.PrintCitates;

// Вывести цитаты

import java.util.ArrayList;
import java.util.Scanner;

public class Task7 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList <String> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        for (String line : lines) {
            processLine(line);
        }
    }

    public static void processLine(String line) {
        boolean q = false;
        int start = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '"' && !q) {
                q = true;
                start = i;
            } else if (line.charAt(i) == '"') {
                System.out.println(line.substring(start, i+1));
                q = false;
            }
        }
    }
}

