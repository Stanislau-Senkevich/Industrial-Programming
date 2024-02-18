package Labs.Lab_6.Stage_2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private double cost;
    private String name;
    private final Date date = new Date();

    public Product(String _name, double _cost) throws NullPointerException {
        if (_name == null) {
            throw new NullPointerException();
        }
        name = _name;
        cost = _cost;
    }

    public double getCost() {return cost;}
    public String getName() {return name;}
    public void setCost(double c) {cost = c;}
    public void setName(String s) {name = s;}

    public String toString() {
        String res = "";
        try {
            res = AppLocale.getString(AppLocale.name)
                    + ": " + name + "\t" +
                    AppLocale.getString(AppLocale.cost) + ": " + Double.toString(cost) +
                    "\t" + AppLocale.getString(AppLocale.date) + ": " +
                    DateFormatDemo.localeDate(AppLocale.getLocale(), date) + "\n";
        } catch (Exception err) {
            System.out.println(err.toString());
        }
        return res;
    }

}
