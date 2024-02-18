package Labs.Lab_6.Stage_1;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private final Customer customer;
    private final ArrayList<Product> products;

    private double cost;
    public Order(Customer c) throws NullPointerException {
        if (c == null) {
            throw new NullPointerException();
        }
        customer = c;
        products = new ArrayList<Product>();
        cost = 0.;
    }

    public Order(Customer c, Product[] pr) throws NullPointerException {
        if (c == null || pr == null) {
            throw new NullPointerException();
        }
        customer = c;
        products = new ArrayList<Product>();
        cost = 0.;
        for (Product p : pr) {
            products.add(p);
            cost += p.getCost();
        }
    }

    void addProduct(Product p) throws IllegalArgumentException {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        products.add(p);
        cost += p.getCost();
    }

    void deleteProduct(Product p) {
        int ind = products.indexOf(p);
        if (ind != -1) {
            products.remove(ind);
        }
        cost -= p.getCost();
    }

    public Customer getCustomer() {return customer;};
    public double getCost() {
        return cost;
    }

    public String toString() {
        String ans = "Order\n" + customer.toString() + "Products:\n";
        for (Product p : products) {
            ans += p.toString();
        }
        return ans;
    }
}
