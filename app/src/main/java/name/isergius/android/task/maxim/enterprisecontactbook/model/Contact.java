package name.isergius.android.task.maxim.enterprisecontactbook.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by isergius on 30.12.16.
 */

public class Contact implements Serializable {

    public static final String TYPE = "Contact";

    private static final long serialVersionUID = 2L;

    private long id;
    private String value;
    private String type;

    public Contact() {
    }

    public Contact(int id, String value, String type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String val) {
        this.value = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
