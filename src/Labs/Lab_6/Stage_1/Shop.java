package Labs.Lab_6.Stage_1;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<Admin> admins;
    private final ArrayList<Product> products;
    private final ArrayList<Customer> blackList;
    private final ArrayList<Order> orders;
    private final ArrayList<Sale> sales;

    public Shop() {
        admins = new ArrayList<Admin>();
        products = new ArrayList<Product>();
        blackList = new ArrayList<Customer>();
        orders = new ArrayList<Order>();
        sales = new ArrayList<Sale>();
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public boolean isAdmin(Admin admin) {
        return admins.contains(admin);
    }

    public void deleteAdmin(Admin admin) {
        int ind = admins.indexOf(admin);
        if (ind != -1) {
            admins.remove(ind);
        }
    }

    public void addProduct(Product p) {
        if (!products.contains(p)) {
            products.add(p);
        }
    }

    public void deleteProduct(Product p) {
        int ind = products.indexOf(p);
        if (ind != -1) {
            products.remove(ind);
        }
    }

    public void updateProduct(Product oldP, Product newP) {
        int ind = products.indexOf(oldP);
        if (ind != -1) {
            products.set(ind, newP);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addToBlackList(Customer c) {
        if (!blackList.contains(c)) {
            blackList.add(c);
        }
    }

    public boolean inBlackList(Customer c) {
        return blackList.contains(c);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void registerOrder(int ind) throws IndexOutOfBoundsException {
        if (ind < 0 || ind > orders.size()) {
            throw new IndexOutOfBoundsException();
        }
        Order order = orders.get(ind);
        orders.remove(ind);
        sales.add(new Sale(order));
    }

    public void addOrder(Order o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        orders.add(o);
    }

    public String toString() {
        String res = "--------------------------------\nShop\n";
        res += "ADMIN LIST:\n";
        for (Admin a : admins) {
            res += a.toString();
        }
        res += "PRODUCT LIST:\n";
        for (Product p : products) {
            res += p.toString();
        }
        res += "ORDER LIST:\n";
        for (Order o : orders) {
            res += o.toString();
        }
        res += "SALES LIST:\n";
        for (Sale s : sales) {
            res += s.toString();
        }
        res += "BLACK LIST:\n";
        for (Customer c : blackList) {
            res += c.toString();
        }
        res += "--------------------------------\n";
        return res;
    }


}
