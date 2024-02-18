package Labs.Lab_8;

import java.io.Serializable;

public class MessageNewsDayResult extends MessageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public News[] news = null;

    public MessageNewsDayResult(String errorMessage ) { //Error
        super( Protocol.CMD_NEWS_DAY, errorMessage );
    }

    public MessageNewsDayResult(News[] news) { // No errors
        super( Protocol.CMD_NEWS_DAY );
        this.news = news;
    }
}
