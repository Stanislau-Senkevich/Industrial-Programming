package Labs.Lab_5;

import java.util.Arrays;

public class Test {
    static void sortAndPrint(Payment[] pays) {
        System.out.println("Sorted in natural order");
        Arrays.sort(pays);
        System.out.print(Payment.format());
        System.out.println();
        for (Payment p : pays) {
            System.out.println(Payment.format(p));
        }
    }

    static void sortAndPrint(Payment[] pays, int sortBy) {
        System.out.println("Sorted by: " + Payment.getSortByName(sortBy));
        Arrays.sort(pays, Payment.getComparator(sortBy));
        System.out.print(Payment.format());
        System.out.println();
        for (Payment p : pays) {
            System.out.println(Payment.format(p));
        }
    }

    public static void main(String[] args) {
        try {
            Payment[] pays = new Payment[4];
            pays[0] = new Payment("Senkevich|Stanislau|Sergeevich|2022|10000|22|18|10|12.5");
            pays[1] = new Payment("Senkevich|Sergey|Anatolievich|2005|100000|24|18|12|5");
            pays[2] = new Payment("Abramov|Mihail|Ivanovich|2006|100|24|18|10|4");
            pays[3] = new Payment("Niliforov|Gennadiy|Alekseevich|2001|5|24|18|0|20");
            for (Payment p : pays) {
                p.countWithheldSum();
                p.countAccruedSum();
            }

            sortAndPrint(pays);
            for (int i = 0; i < 11; i++) {
                sortAndPrint(pays, i);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }
}