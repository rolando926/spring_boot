package BasicWebServices;

import java.util.Map;

/**
 * Created by RXC8414 on 4/13/2017.
 */
public class Associates {
    private String className;
    private String instructor;
    private Map<String,String> associates;

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

    public Map<String, String> getAssociates() {
        return associates;
    }

    public void setAssociates(Map<String, String> associates) {
        this.associates = associates;
    }
}
