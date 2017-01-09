package name.isergius.android.task.maxim.contactserver.model;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by isergius on 30.12.16.
 */

@Entity
public class Contact implements Serializable {

    private static final long serialVersionUID = 2L;

    private long id;
    private String value;
    private String type;

    public Contact() {
    }

    public Contact(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public String getValue() {
        return this.value;
    }

    public void setValue(String val) {
        this.value = val;
    }

    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        return value.equals(contact.value);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
