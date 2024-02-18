package Labs.Lab_6.Stage_2;

import java.util.*;
import java.text.*;

public class DateFormatDemo {

    static public String localeDate(Locale currentLocale, Date date) {
        Date today;
        String dateOut;
        DateFormat dateFormatter;

        dateFormatter =
                DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
        dateOut = dateFormatter.format(date);

        return dateOut;
    }
}
