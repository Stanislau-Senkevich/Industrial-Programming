package Labs.Lab_8;

import java.io.Serializable;

public class MessageAllNewsResult extends MessageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public News[] news = null;

    public MessageAllNewsResult(String errorMessage ) { //Error
        super( Protocol.CMD_ALL_NEWS, errorMessage );
    }

    public MessageAllNewsResult(News[] news) { // No errors
        super( Protocol.CMD_ALL_NEWS );
        //this.news = news;
        this.news = news;
    }
}
