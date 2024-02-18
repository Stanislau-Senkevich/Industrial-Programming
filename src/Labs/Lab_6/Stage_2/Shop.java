package Labs.Lab_6.Stage_2;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Shop implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<Admin> admins;
    private final ArrayList<Product> products;
    private final ArrayList<Customer> blackList;
    private final ArrayList<Order> orders;
    private final ArrayList<Sale> sales;

    private final Date date = new Date();

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
        String res = AppLocale.getString(AppLocale.shop) + "\n";
        res += AppLocale.getString(AppLocale.date) + ": " +
                DateFormatDemo.localeDate(AppLocale.getLocale(), date) + "\n";
        res += AppLocale.getString(AppLocale.adminList) + ":\n\n";
        for (Admin a : admins) {
            res += a.toString();
        }
        res += "\n" + AppLocale.getString(AppLocale.productList) + ":\n\n";
        for (Product p : products) {
            res += p.toString();
        }
        res += "\n" + AppLocale.getString(AppLocale.orderList) + ":\n\n";
        for (Order o : orders) {
            res += o.toString();
        }
        res += "\n" + AppLocale.getString(AppLocale.saleList) + ":\n\n";
        for (Sale s : sales) {
            res += s.toString();
        }
        res += "\n" + AppLocale.getString(AppLocale.blackList) + ":\n\n";
        for (Customer c : blackList) {
            res += c.toString();
        }
        return res;
    }
}
