package Labs.Lab_5;

import java.util.*;

public class Payment implements Comparable<Payment>, Iterable<String> {
    public static class ArgException extends Exception {
        private static final long serialVersionUID = 1L;

        ArgException(String arg) {
            super("Invalid argument:" + arg);
        }
    }

    public static final String[] names = {
            "*Surname",
            "*Name",
            "*Patronymic",
            "*Year",
            "*Salary",
            "*Workdays",
            "*Workdays completed",
            "Bonus %",
            "Tax %",
            "Accrued amount",
            "Withheld amount",
    };

    public String[] areas = null;

    public static String[] formatStr = {
            "%-30s",
            "%-30s",
            "%-30s",
            "%-8s",
            "%-9s",
            "%-20s",
            "%-20s",
            "%-20s",
            "%-20s",
            "%-20s",
            "%-20s"
    };

    public static String getSortByName(int sortBy) {
        return Payment.names[sortBy];
    }

    // Comparator <Payment>
    public static Comparator<Payment> getComparator(final int sortBy) {
        if (sortBy >= names.length || sortBy < 0) {
            throw new IndexOutOfBoundsException();
        }
        return new Comparator<Payment>() {
            @Override
            public int compare(Payment o1, Payment o2) {
                return o1.areas[sortBy].compareTo(o2.areas[sortBy]);
            }
        };
    }

    protected boolean validSurname(String str) {
        return str != null && !str.isEmpty();
    }
    protected boolean validName(String str) {
        return str != null && !str.isEmpty();
    }
    protected boolean validPatronymic(String str) {
        return str != null && !str.isEmpty();
    }
    protected boolean validStartingWorkYear(String str) {
        return str != null;
    }

    protected boolean validSalary(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    protected boolean validWorkdays(String str) {
        if (str == null) {
            return false;
        }
        int d;
        try {
            d = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return d >= 0 && d <= 31;
    }

    protected boolean validCompletedWorkdays(String str) {
        return validWorkdays(str);
    }

    protected boolean validPercent(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return true;
        }
        double p;
        try {
            p = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return p >= 0. && p <= 100.;
    }

    protected boolean validAmount(String str) {
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return true;
        }
        double am;
        try {
            am = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return am >= 0.;
    }

    public int length() {
        return areas.length;
    }

    public String getArea(int idx) {
        if (idx >= length() || idx < 0) {
            throw new IndexOutOfBoundsException();
        }
        return areas[idx];
    }

    public void setArea(int idx, String value) throws ArgException {
        if (idx >= length() || idx < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (    (idx == 0 && !validSurname(value))
                || (idx == 1 && !validName(value))
                || (idx == 2 && !validPatronymic(value))
                || (idx == 3 && !validStartingWorkYear(value))
                || (idx == 4 && !validSalary(value))
                || (idx == 5 && !validWorkdays(value))
                || (idx == 6 && !validCompletedWorkdays(value))
                || ((idx == 7 || idx == 8) && !validPercent(value))
                || ((idx == 9 || idx == 10) && !validAmount(value))
        ) {
            throw new ArgException(value);
        }
        areas[idx] = value;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int iterator_idx = -1;
            @Override
            public boolean hasNext() {
                return iterator_idx < (length() - 1);
            }

            @Override
            public String next() {
                if (iterator_idx < length() - 1) {
                    return areas[++iterator_idx];
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static String format(Iterable<String> what) {
        String result = "";
        int idx = 0;
        for (String str : what) {
            result += String.format(formatStr[idx++], str);
        }
        return result;
    }

    public static String format() {
        return Payment.format(Arrays.asList(Payment.names));
    }

    public static String format(Payment p) {
        return Payment.format(((Iterable<String>) p));
    }

    // Comparable <Payment>
    @Override
    public int compareTo(Payment p) {
        return Payment.format(this).compareTo(Payment.format(p));
    }

    // toString
    @Override
    public String toString() {
        if (areas == null) {
            return " | | | | | | | | | | | ";
        }
        String res = areas[0];
        for (int i = 1; i < length(); i++) {
            res += "|" + areas[i];
        }
        return res;
    }

    // Constructor

    private void setup(String[] args) throws ArgException {
        if (args == null) {
            throw new ArgException("null pointer passed for args");
        }

        if (args.length < 7 || args.length >names.length) {
            throw new ArgException(Arrays.toString(args));
        }

        areas = new String[names.length];
        int i = 0;
        for (; i < args.length; i++) {
            setArea(i, args[i]);
        }
        while (i < names.length) {
            areas[i++] = "0";
        }
    }

    public Payment(String str) throws ArgException {
        if (str == null) {
            throw new ArgException("null pointer passed for str");
        }
        setup(str.split("\\|"));
    }

    public Payment(String... args) throws ArgException {
        setup(args);
    }

    //methods required

    public double countAccruedSum() throws ArgException {
        // Salary * (CompletedWorkDays/WorkDays) * (Bonus %)/100
        double sum =  Double.parseDouble(areas[4])/Double.parseDouble(areas[5])*
                Double.parseDouble(areas[6]) * Double.parseDouble(areas[7])/100.;
        setArea(9, Double.toString(sum));
        return sum;
    }

    public double countWithheldSum() throws ArgException {
        double sum = (countAccruedSum()*Double.parseDouble(areas[8])/100.);
        setArea(10, Double.toString(sum));
        return sum;
    }

    public double countSumToWorker() throws ArgException {
        return countAccruedSum() - countWithheldSum();
    }

}
