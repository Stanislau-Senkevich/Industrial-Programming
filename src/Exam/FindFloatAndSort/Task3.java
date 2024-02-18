package Exam.FindFloatAndSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Найти во входных данных литералы типа float
// и вывести их вместе с номером строки,
// упорядочив по убыванию

public class Task3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int row = 1;
        ArrayList<Data> nums = new ArrayList<>();
        while (in.hasNextLine()) {
            String str = in.nextLine();
            StringTokenizer t = new StringTokenizer(str, " \n\r");
            float num;
            while (t.hasMoreTokens()) {
                try {
                    num = Float.parseFloat(t.nextToken());
                } catch (NumberFormatException e) {
                    continue;
                }
                nums.add(new Data(num, row));
            }
            row++;
        }
        Collections.sort(nums, Collections.reverseOrder());
        for (Data f : nums) {
            System.out.printf("Num: %f\tRow: %d\n", f.num, f.row);
        }

    }
}
