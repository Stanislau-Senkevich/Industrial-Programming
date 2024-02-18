package Labs.Lab_10.tours;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public class Tour implements Serializable {
    // class release version:
    private static final long serialVersionUID = 1L;
    String tourName;
    public static final String P_tourName = "TourName";
    String name;
    public static final String P_name = "Name";
    double dayCost;
    public static final String P_dayCost = "DayCost";
    int dayAmount;
    public static final String P_dayAmount = "DayAmount";
    double transportPrice;
    public static final String P_transportPrice = "TransportPrice";
    double tripPrice;
    public static final String P_price = "TripPrice";


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

    public static final String AREA_DEL = "\n";

    public String toString() {
        return new String(
                tourName + AREA_DEL +
                        name + AREA_DEL +
                        dayCost + AREA_DEL +
                        dayAmount + AREA_DEL +
                        transportPrice + AREA_DEL +
                        tripPrice
        );
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        if (tourName == null) {
            throw new IllegalArgumentException("Illegal tour name");
        }
        this.tourName = tourName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Illegal name");
        }
        this.name = name;
    }

    public double getDayCost() {
        return dayCost;
    }

    public void setDayCost(String strDayCost) {
        if (strDayCost == null) {
            throw new IllegalArgumentException("Illegal day cost");
        }
        boolean isError = false;
        double c = 0;
        try {
            c = Double.parseDouble(strDayCost);
        } catch (Error | Exception e) {
            isError = true;
        }
        if (isError || !validMoney(c)) {
            throw new IllegalArgumentException("Illegal money");
        }
        this.dayCost = c;
    }

    public int getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(String strDayAmount) {
        if (strDayAmount == null) {
            throw new IllegalArgumentException("Illegal day amount");
        }
        boolean isError = false;
        int c = 0;
        try {
            c = Integer.parseInt(strDayAmount);
        } catch (Error | Exception e) {
            isError = true;
        }
        if (isError || c <= 0) {
            throw new IllegalArgumentException("Illegal day amount");
        }
        this.dayAmount = c;
    }

    public double getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(String strTransPrice) {
        if (strTransPrice == null) {
            throw new IllegalArgumentException("Illegal transport price");
        }
        boolean isError = false;
        double c = 0;
        try {
            c = Double.parseDouble(strTransPrice);
        } catch (Error | Exception e) {
            isError = true;
        }
        if (isError || !validMoney(c)) {
            throw new IllegalArgumentException("Illegal money");
        }
        this.transportPrice = c;
    }

    public double getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(String strTripPrice) {
        if (strTripPrice == null) {
            throw new IllegalArgumentException("Illegal trip price");
        }
        boolean isError = false;
        double c = 0;
        try {
            c = Double.parseDouble(strTripPrice);
        } catch (Error | Exception e) {
            isError = true;
        }
        if (isError || !validMoney(c)) {
            throw new IllegalArgumentException("Illegal money");
        }
        this.tripPrice = c;
    }
}
