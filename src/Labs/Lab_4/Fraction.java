package Labs.Lab_4;

import java.io.IOException;

public class Fraction {
    private long m, n;
    public Fraction() {
        m = 0;
        n = 1;
    }
    public Fraction(long x, long y) throws IOException {
        m = x;
        n = y;
        if (y <= 0) {
            throw new IOException("Negative denominator");
        }
    }

    public Fraction(long numerator) {
        m = numerator;
        n = 1;
    }

    public Fraction(final Fraction f) {
        m = f.m;
        n = f.n;
    }

    public String Print() {
        return String.format("%d/%d", m,n);
    }

    public long GetNumerator() {
        return m;
    }

    public long GetDenominator() {
        return n;
    }
    public void SetNumerator(long num) {
        m = num;
    }

    public void SetDenominator(long den) throws IOException {
        if (den <= 0) {
            throw new IOException("Negative denominator");
        }
        n = den;
    }

    public void Set(long num, long den) throws IOException {
        SetNumerator(num);
        SetDenominator(den);
    }

    public static Fraction Add(final Fraction f1, final Fraction f2) {
        Fraction ans = new Fraction();
        ans.n = f1.n * f2.n;
        ans.m =  f1.m * f2.n + f2.m * f1.n;
        ans.Reduce();
        return ans;
    }

    public static Fraction Substract(final Fraction f1, final Fraction f2) {
        Fraction ans = new Fraction();
        ans.n = f2.n * f1.n;
        ans.m =  f1.m * f2.n - f2.m * f1.n;
        ans.Reduce();
        return ans;
    }

    public static Fraction Produce(final Fraction f1, final Fraction f2) {
        Fraction ans = new Fraction();
        ans.n = f1.n * f2.n;
        ans.m =  f1.m * f2.m;
        ans.Reduce();
        return ans;
    }

    public static Fraction Divide(final Fraction f1, final Fraction f2) throws IOException {
        Fraction ans = new Fraction();
        if (f2.m == 0) {
            throw new IOException("Division by zero");
        }
        ans.n = f1.n * f2.m;
        ans.m = f1.m * f2.n;
        ans.Reduce();
        return ans;
    }

    public void Reduce() {
        for (int i = 2; i < Math.sqrt(Math.max(m,n))+4; i++) {
            while (m % i == 0 && n % i == 0) {
                m /= i;
                n /= i;
            }
        }
    }

    public static boolean Greater(final Fraction f1, final Fraction f2) {
        return Substract(f1, f2).m > 0;
    }

    public static boolean Equal(final Fraction f1, final Fraction f2) {
        return Substract(f1,f2).m == 0;
    }

    public static boolean GreaterOrEqual(final Fraction f1, final Fraction f2) {
        return Greater(f1,f2) || Equal(f1,f2);
    }

    public static boolean Less(final Fraction f1, final Fraction f2) {
        return !Greater(f1,f2);
    }

    public static boolean LessOrEqual(final Fraction f1, final Fraction f2) {
        return Less(f1,f2) || Equal(f1,f2);
    }
}

