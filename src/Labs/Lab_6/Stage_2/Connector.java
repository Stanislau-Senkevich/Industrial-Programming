package Labs.Lab_6.Stage_2;

import java.io.*;

public class Connector {

    private File file;

    public File getFile() {
        return file;
    }

    public Connector(String filename) {
        this.file = new File(filename);
    }

    public Connector(File file) {
        this.file = file;
    }

    public void writeShop(Shop[] sh) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(sh.length);
            for (Shop s : sh) {
                oos.writeObject(s);
            }
            oos.flush();
        }
    }

    public Shop[] readShop() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            int length = oin.readInt();
            Shop[] res = new Shop[length];
            for (int i = 0; i < length; i++) {
                res[i] = (Shop) oin.readObject();
            }
            return res;
        }
    }

    public void writeCustomer(Customer[] sh) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(sh.length);
            for (Customer s : sh) {
                oos.writeObject(s);
            }
            oos.flush();
        }
    }

    public Customer[] readCustomer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            int length = oin.readInt();
            Customer[] res = new Customer[length];
            for (int i = 0; i < length; i++) {
                res[i] = (Customer) oin.readObject();
            }
            return res;
        }
    }

    public void writeOrder(Order[] sh) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(sh.length);
            for (Order s : sh) {
                oos.writeObject(s);
            }
            oos.flush();
        }
    }

    public Order[] readOrder() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            int length = oin.readInt();
            Order[] res = new Order[length];
            for (int i = 0; i < length; i++) {
                res[i] = (Order) oin.readObject();
            }
            return res;
        }
    }

    public void writeSale(Sale[] sh) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(sh.length);
            for (Sale s : sh) {
                oos.writeObject(s);
            }
            oos.flush();
        }
    }

    public Sale[] readSale() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            int length = oin.readInt();
            Sale[] res = new Sale[length];
            for (int i = 0; i < length; i++) {
                res[i] = (Sale) oin.readObject();
            }
            return res;
        }
    }

    public void writeAdmin(Admin[] sh) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(sh.length);
            for (Admin s : sh) {
                oos.writeObject(s);
            }
            oos.flush();
        }
    }

    public Admin[] readAdmin() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            int length = oin.readInt();
            Admin[] res = new Admin[length];
            for (int i = 0; i < length; i++) {
                res[i] = (Admin) oin.readObject();
            }
            return res;
        }
    }
}