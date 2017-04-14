package BasicWebServices;

import java.util.Map;

/**
 * Created by RXC8414 on 4/13/2017.
 */
public class Contractors {
    private String className;
    private String instructor;
    private Map<String,String> contractors;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Map<String, String> getContractors() {
        return contractors;
    }

    public void setContractors(Map<String, String> contractors) {
        this.contractors = contractors;
    }
}
