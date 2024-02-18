package Labs.Lab_6.Stage_2;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private final String surname;
    private double balance;
    private final Date date = new Date();

    public Customer() {
        surname = "";
        balance = 0.;
    }

    public Customer(String _surname, double _balance) {
        surname = _surname;
        balance = _balance;
    }

    public void pay(Sale sale) throws AppException  {
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
        return AppLocale.getString(AppLocale.customer) + ":\t" +
                AppLocale.getString(AppLocale.surname) + " " + surname +
                "\t" + AppLocale.getString(AppLocale.balance) + ": " + Double.toString(balance) +
                "\t" + AppLocale.getString(AppLocale.date) + ": "
                + DateFormatDemo.localeDate(AppLocale.getLocale(), date) + "\n";
    }
}
