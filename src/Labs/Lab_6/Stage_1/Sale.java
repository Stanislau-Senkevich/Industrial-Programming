package Labs.Lab_6.Stage_1;

import java.io.Serial;
import java.io.Serializable;

public class Sale implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private final Order order;
    private boolean isPaid;
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
        return "Sale's order:\n" + order.toString() +
                "Paid: " + Boolean.toString(isPaid) + "\n";
    }
}
