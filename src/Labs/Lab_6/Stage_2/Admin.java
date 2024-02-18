package Labs.Lab_6.Stage_2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private final String login;
    private final Date date = new Date();

    public Admin(String _login)  {
        login = _login;
    }

    public void addProduct(Shop s, Product p) throws AppException {
        if (s == null || p == null) {
            throw new NullPointerException();
        }
        if (!s.isAdmin(this)) {
            throw new AppException("Forbidden");
        }
        s.addProduct(p);
    }

    public void deleteProduct(Shop s, Product p) throws AppException {
        if (s == null || p == null) {
            throw new NullPointerException();
        }
        if (!s.isAdmin(this)) {
            throw new AppException("Forbidden");
        }
        s.deleteProduct(p);
    }

    public void updateProduct(Shop s, Product oldP, Product newP) throws AppException {
        if (s == null || oldP == null || newP == null) {
            throw new NullPointerException();
        }
        if (s.isAdmin(this)) {
            throw new AppException("Forbidden");
        }
        s.updateProduct(oldP, newP);
    }

    public void registerOrder(Shop s, int ind) throws AppException {
        if (s == null) {
            throw new NullPointerException();
        }
        if (!s.isAdmin(this)) {
            throw new AppException("Forbidden");
        }
        s.registerOrder(ind);
    }

    public void addToBlackList(Shop s, Customer c) throws AppException {
        if (s == null || c == null) {
            throw new NullPointerException();
        }
        if (!s.isAdmin(this)) {
            throw new AppException("Forbidden");
        }
        s.addToBlackList(c);
    }

    public String toString() {
        return AppLocale.getString(AppLocale.admin) + ":\t" +
                AppLocale.getString(AppLocale.login) + ": " + login + "\t" +
                AppLocale.getString(AppLocale.date) + ": " +
                DateFormatDemo.localeDate(AppLocale.getLocale(), date) + "\n";
    }
}
