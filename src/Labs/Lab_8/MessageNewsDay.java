package Labs.Lab_8;

import java.io.Serializable;

public class MessageNewsDay extends Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public String date;

    public MessageNewsDay() {
        super( Protocol.CMD_NEWS_DAY );
    }
}
