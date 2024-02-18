package Exam.FormulasWithData;

import java.util.*;

// Во входных данных считать ряды чисел и посчитать для них параметры.
// В случае неправильных данных, сообщить об этом и исключить из обработки.

public class Task2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int row = 0;
        ArrayList<Data> data = new ArrayList<Data>();
        while (in.hasNextLine()) {
            String str = in.nextLine();
            StringTokenizer t = new StringTokenizer(str, " \t\r\n");
            ArrayList<Double> nums = new ArrayList<>();
            double num;
            while (t.hasMoreTokens()) {
                try {
                    num = Double.parseDouble(t.nextToken());
                }
                catch (NumberFormatException e) {
                    System.out.printf("Row #%d has incorrect data.", row);
                    row++;
                    continue;
                }
                nums.add(num);
            }
            Data d = new Data(nums, row);
            d.compute();
            data.add(d);
            row++;
        }
        Collections.sort(data);
        for (Data d : data) {
            System.out.println(d.print());
        }
    }


}


