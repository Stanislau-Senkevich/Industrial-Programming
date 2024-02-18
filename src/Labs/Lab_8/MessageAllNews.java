package Labs.Lab_8;

import java.io.Serializable;

public class MessageAllNews extends Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public MessageAllNews() {
        super( Protocol.CMD_ALL_NEWS );
    }
}
