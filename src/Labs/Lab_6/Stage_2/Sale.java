package Labs.Lab_6.Stage_2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Sale implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private final Order order;
    private boolean isPaid;

    private final Date date = new Date();
    public Sale(Order o) {
        order = o;
        isPaid = false;
    }
    public void acceptPayment() {
        isPaid = true;
    }

    public boolean getIsPaid() {return isPaid;};
    public double getCost() {return order.getCost();};

    public String toString() {
        return AppLocale.getString(AppLocale.sale) + "\n" + AppLocale.getString(AppLocale.date) +
                ": " + DateFormatDemo.localeDate(AppLocale.getLocale(), date) + "\n" + order.toString() +
                AppLocale.getString(AppLocale.isPaid) + ": " + Boolean.toString(isPaid) + "\n";
    }
}
