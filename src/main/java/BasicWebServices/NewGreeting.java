package BasicWebServices;

/**
 * Created by RXC8414 on 4/11/2017.
 */
public class NewGreeting {
    private long id;
    private String strMessage;

    public NewGreeting(){}


    public void setId(long id) {
        this.id = id;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    public long getId() {
        return id;
    }

    public String getStrMessage() {
        return strMessage;
    }
}
