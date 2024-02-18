package Labs.Lab_1;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }
        double x = Double.parseDouble(args[0]);
        if (x >= 1 || x <= -1) {
            System.err.println("Invalid argument x");
            System.exit(1);
        }
        int k = Integer.parseInt(args[1]);
        if (k <= 1) {
            System.err.println("Invalid argument k");
            System.exit(1);
        }
        double eps = 1/Math.pow(10,k+1);
        double res = 0;
        double step = x;
        double i = 1;
        while (Math.abs(step) >= eps) {
            res += step;
            step *= x*x;
            step *= -i/(i+2);
            i+=2;
        }

        double arc = Math.atan(x);
        String fmt = "%s: %." + args[1] + "f\n";
        System.out.printf(fmt, "Taylor", res);
        System.out.printf(fmt, "Math", arc);
        System.exit(0);
    }
}