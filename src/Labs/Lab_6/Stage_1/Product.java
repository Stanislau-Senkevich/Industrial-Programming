package Labs.Lab_6.Stage_1;

import java.io.Serial;
import java.io.Serializable;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private double cost;
    private String name;

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
        return "Product:\tName: " + name +"\tCost: " + Double.toString(cost) +"\n";
    }

}
