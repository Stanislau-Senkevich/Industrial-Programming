package Labs.Lab_4;

import java.io.IOException;
import java.util.Scanner;

//10.     Определить класс «Дробь» в виде пары m/n. Создать несколько
//        конструкторов, определить арифметические операции, операции сравнения. Объявить
//        массив из k дробей, ввести/вывести значения для массива дробей.

public class Test {

    public static final int k = 5;
    public Test() {};
    public static void main(String[] args) {
        boolean resBool, ansBool;

        Fraction[] fracs = new Fraction[k];
        Fraction f1 = new Fraction();
        Fraction f2 = new Fraction();
        try {
            f1.Set(1,2);
            f2.Set(1,2);
        } catch (IOException err) {

        }


        Fraction res = Fraction.Add(f1,f2);
        Fraction ans = new Fraction(1);

        System.out.print("Tests:\n\n");

        System.out.print("Copy constructor:\n");
        System.out.printf("f1: %s, copied from f1: %s\n\n", f2.Print(), new Fraction(f2).Print());

        System.out.printf("%s + %s\nResult: %s\nWanted: %s\n\n",
                f1.Print(), f2.Print(), res.Print(), ans.Print());
        assert(Fraction.Equal(res, ans));

        try {
            f1.Set(5,3);
            ans.Set(7,6);
        } catch (IOException err) {

        }
        res = Fraction.Substract(f1,f2);
        System.out.printf("%s - %s\nResult: %s\nWanted: %s\n\n",
                f1.Print(), f2.Print(), res.Print(), ans.Print());
        assert(Fraction.Equal(res, ans));

        res = Fraction.Produce(f1,f2);
        try {
            ans.Set(5,6);
        } catch (IOException err) {

        }
        System.out.printf("%s * %s\nResult: %s\nWanted: %s\n\n",
                f1.Print(), f2.Print(), res.Print(), ans.Print());
        assert(Fraction.Equal(res, ans));

        try {
            f2.Set(4,5);
            ans.Set(25,12);
        } catch (IOException err) {

        }

        try {
            res = Fraction.Divide(f1,f2);
        } catch (IOException err) {
            System.out.print("Division by zero!");
        }

        System.out.printf("%s / %s\nResult: %s\nWanted: %s\n\n",
                f1.Print(), f2.Print(), res.Print(), ans.Print());
        assert(Fraction.Equal(res, ans));

        resBool = Fraction.Greater(f1,f2);
        ansBool = true;

        System.out.printf("%s > %s\nResult: %b\nWanted: %b\n\n",
                f1.Print(), f2.Print(), resBool, ansBool);
        assert(Fraction.Greater(f1, f2));


        resBool = Fraction.Less(f1,f2);
        ansBool = false;
        System.out.printf("%s < %s\nResult: %b\nWanted: %b\n\n",
                f1.Print(), f2.Print(), resBool, ansBool);
        assert(!Fraction.Less(f1, f2));

        resBool = Fraction.Equal(f1,f2);
        ansBool = false;
        System.out.printf("%s == %s\nResult: %b\nWanted: %b\n\n",
                f1.Print(), f2.Print(), resBool, ansBool);
        assert(!Fraction.Less(f1, f2));

        resBool = Fraction.GreaterOrEqual(f1,f2);
        ansBool = true;

        System.out.printf("%s >= %s\nResult: %b\nWanted: %b\n\n",
                f1.Print(), f2.Print(), resBool, ansBool);
        assert(Fraction.Greater(f1, f2));


        resBool = Fraction.LessOrEqual(f1,f2);
        ansBool = false;
        System.out.printf("%s <= %s\nResult: %b\nWanted: %b\n\n",
                f1.Print(), f2.Print(), resBool, ansBool);
        assert(!Fraction.Less(f1, f2));

        Scanner in = new Scanner(System.in);

        for (int i = 0; i < k; i++) {
            System.out.printf("Enter numerator and denominator for %d-th fraction\n", i+1);
            fracs[i] = new Fraction();
            try {
                fracs[i].Set(in.nextLong(), in.nextLong());
            } catch (IOException err) {
                System.out.print("Non-positive denominator was entered, it was set to 1.\n");
            }

        }

        System.out.println("Your fractions:");
        for (int i = 0; i < k; i++) {
            System.out.printf("%s\n", fracs[i].Print());
        }
    }
}
