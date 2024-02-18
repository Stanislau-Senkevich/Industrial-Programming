package Labs.Lab_6.Stage_1;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private String surname;
    private double balance;

    public Customer() {
        surname = "";
        balance = 0.;
    }

    public Customer(String _surname, double _balance) {
        surname = _surname;
        balance = _balance;
    }

    public void pay(Sale sale) throws AppException {
        if (sale.getIsPaid()) {
            throw new AppException("Sale is already paid");
        }
        if (balance < sale.getCost()) {
            throw new AppException("Insufficient funds");
        }
        balance -= sale.getCost();
        sale.acceptPayment();
    }
    public String getSurname() {return surname;}
    public ArrayList<Product> getProducts(Shop s) {
        return s.getProducts();
    }
    public void makeOrder(Product[] products, Shop s) throws NullPointerException, AppException {
        if (s == null || products == null) {
            throw new NullPointerException();
        }
        if (s.inBlackList(this)) {
            throw new AppException("You are in blackList!");
        }
        s.addOrder(new Order(this, products));
    }

    public String toString() {
        return "Customer:\t" +
                "Surname: " + surname + "\tBalance: " + Double.toString(balance) + "\n";
    }
}
