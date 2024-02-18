package Labs.Lab_7;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public class Tour implements Serializable {
    private static final long serialVersionUID = 1L;
    String tourName;
    static final String P_tourName = "TourName";
    String name;
    static final String P_name = "Name";
    double dayCost;
    static final String P_dayCost = "DayCost";
    int dayAmount;
    static final String P_dayAmount = "DayAmount";
    double transportPrice;
    static final String P_transportPrice = "TransportPrice";
    double tripPrice;
    static final String P_price = "TripPrice";


    static Boolean validMoney(double cost) {
        return cost > 0.;
    }

    static boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
        out.print(prompt);
        out.print(": ");
        return fin.hasNextLine();
    }

    static boolean nextRead(Scanner fin, PrintStream out) {
        return nextRead(P_name, fin, out);
    }

    public static Tour read(Scanner fin, PrintStream out) throws IOException,
            NumberFormatException {
        String str;
        Tour tour = new Tour();
        if (!nextRead(P_tourName, fin, out)) {
            return null;
        }
        tour.tourName = fin.nextLine();
        if (!nextRead(P_name, fin, out)) {
            return null;
        }
        tour.name = fin.nextLine();
        if (!nextRead(P_dayCost, fin, out)) {
            return null;
        }
        str = fin.nextLine();
        tour.dayCost = Double.parseDouble(str);
        if (!Tour.validMoney(tour.dayCost)) {
            throw new IOException("Invalid Tour.cost value");
        }
        if (!nextRead(P_dayAmount, fin, out)) {
            return null;
        }

        str = fin.nextLine();
        tour.dayAmount = Integer.parseInt(str);
        if (!nextRead(P_transportPrice, fin, out)) {
            return null;
        }

        str = fin.nextLine();
        tour.transportPrice = Double.parseDouble(str);
        if (!Tour.validMoney(tour.transportPrice)) {
            throw new IOException("Invalid Tour.TransportPrice value");
        }

        if (!nextRead(P_price, fin, out)) {
            return null;
        }

        str = fin.nextLine();
        tour.tripPrice = Double.parseDouble(str);
        if (!Tour.validMoney(tour.tripPrice)) {
            throw new IOException("Invalid Tour.TripPrice value");
        }
        return tour;
    }

    public Tour() {
    }

    public static final String areaDel = "\n";

    public String toString() {
        return new String(
                "Name: " + name + areaDel +
                        "Tour name: " + tourName + areaDel +
                        "Day's cost: " + dayCost + areaDel +
                        "Days amount: " + dayAmount + areaDel +
                        "Transport's price: " + transportPrice + areaDel +
                        "Trip price: " + tripPrice
        );
    }
}
