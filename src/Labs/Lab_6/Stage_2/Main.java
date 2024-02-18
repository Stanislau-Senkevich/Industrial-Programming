package Labs.Lab_6.Stage_2;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

//14. Система Интернет-магазин. Администратор добавляет информацию о Товаре. Клиент
//делает и оплачивает Заказ на Товары. Администратор регистрирует Продажу и может занести
//неплательщиков в “черный список”.

public class Main {
    static Locale createLocale(String[] args) {
        if (args.length == 2) {
            return new Locale(args[0], args[1]);
        } else if (args.length == 4) {
            return new Locale(args[2], args[3]);
        }
        return null;
    }

    static void setupConsole(String[] args) {
        if (args.length >= 2) {
            if (args[0].equals("-encoding")) {
                try {
                    System.setOut(new PrintStream(System.out, true, args[1]));
                } catch (UnsupportedEncodingException ex) {
                    System.err.println("Unsupported encoding: " + args[1]);
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            setupConsole(args);
            Locale locale = createLocale(args);
            if (locale == null) {
                System.err.println("Invalid argument(s)\n"
                        + "Syntax: [-encoding ENCODING_ID] language country\n"
                        + "Example: -encoding Cp855 be BY");
                System.exit(1);
            }
            AppLocale.set(locale);
            Connector conn = new Connector("output.dat");
            Shop sh = new Shop();
            Admin admin1 = new Admin("login");
            Customer c1 = new Customer("Raitsyna", 5000.);
            Customer c2 = new Customer("Kanoplich", 1.);
            Customer c3 = new Customer("BimBimBim", 100.);
            Product p1 = new Product("BIM-BIM-BAM-BAM", 50);
            Product p2 = new Product("POCO F4 PRO", 2000.);
            Product p3 = new Product("IPHONE 13", 100.);
            sh.addAdmin(admin1);
            admin1.addProduct(sh, p1);
            admin1.addProduct(sh, p2);
            sh.addProduct(p3);
            Product[] c1Prod = new Product[2];
            Product[] c3Prod = new Product[1];
            c3Prod[0] = p1;
            c1Prod[0] = p1;
            c1Prod[1] = p2;
            c1.makeOrder(c1Prod, sh);
            c2.makeOrder(c1Prod, sh);
            c3.makeOrder(c3Prod, sh);
            admin1.registerOrder(sh, 0);
            c1.pay(sh.getSales().get(0));
            admin1.addToBlackList(sh, c3);


            Shop[] shops = new Shop[2];
            shops[0] = sh;
            shops[1] = sh;

            conn.writeShop(shops);
            Shop[] res = conn.readShop();
            for (Shop s : res) {
                System.out.println(s.toString());
            }

            System.out.println("Orders output example\n");
            conn.writeOrder(sh.getOrders().toArray(new Order[0]));
            Order[] o1;
            o1 = conn.readOrder();

            for (Order o : o1) {
                System.out.println(o.toString());
            }

            System.out.println("----------------------------------------------------");


            System.out.println("Sales output example\n");
            conn.writeSale(sh.getSales().toArray(new Sale[0]));
            Sale[] s1;
            s1 = conn.readSale();

            for (Sale sale : s1) {
                System.out.println(sale.toString());
            }

            System.out.println("----------------------------------------------------");

            System.out.println("Customer output example\n");
            Customer[] writeCusts = new Customer[3];
            writeCusts[0] = c1;
            writeCusts[1] = c2;
            writeCusts[2] = c3;
            conn.writeCustomer(writeCusts);
            writeCusts = conn.readCustomer();

            for (Customer c : writeCusts) {
                System.out.println(c.toString());
            }

            System.out.println("----------------------------------------------------");

            System.out.println("Admin output example\n");
            Admin[] writeAdm = new Admin[1];
            writeAdm[0] = admin1;
            conn.writeAdmin(writeAdm);

            for (Admin adm : writeAdm) {
                System.out.println(adm.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception err) {
            System.out.println(err.toString());
        }

    }
}