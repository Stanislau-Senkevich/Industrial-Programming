package Labs.Lab_6.Stage_2;

import java.util.*;

public class AppLocale {
    private static final String strMsg = "Msg";
    private static Locale loc = Locale.getDefault();
    private static ResourceBundle res =
            ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc);

    static Locale get() {
        return AppLocale.loc;
    }

    static void set( Locale loc ) {
        AppLocale.loc = loc;
        res = ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
    }

    static Locale getLocale() {
        return loc;
    }

    static ResourceBundle getBundle() {
        return AppLocale.res;
    }

    static String getString( String key ) {
        return AppLocale.res.getString(key);
    }

        // Resource keys:

    public static final String shop="shop";
    public static final String order="order";
    public static final String customer="customer";
    public static final String sale="sale";
    public static final String products="products";
    public static final String product="product";
    public static final String surname="surname";
    public static final String date="date";
    public static final String cost="cost";
    public static final String login="login";
    public static final String balance="balance";
    public static final String name="name";
    public static final String isPaid="isPaid";
    public static final String admin="admin";
    public static final String orderList="order_list";
    public static final String adminList="admin_list";
    public static final String customerList="customer_list";
    public static final String saleList="sale_list";
    public static final String blackList="black_list";
    public static final String productList="product_list";

}

