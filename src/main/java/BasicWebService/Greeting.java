package BasicWebService;

/**
 * Created by RXC8414 on 4/11/2017.
 */
public class Greeting {
    private long id;
    private String strMessage;

    public Greeting(long id, String strMessage) {
        this.id = id;
        this.strMessage = strMessage;
    }

    public long getId() {
        return id;
    }

    public String getStrMessage() {
        return strMessage;
    }
}
