package name.isergius.android.task.maxim.enterprisecontactbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.EmailContactIntentBuilder;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.PhoneContactIntentBuilder;

/**
 * Created by isergius on 30.12.16.
 */

public class Employee extends Node implements Serializable {

    public static final String TYPE = "Employee";

    private static final long serialVersionUID = 5L;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Phone")
    private String phone;

    private List<Contact> contacts = new ArrayList<>();

    public Employee(long id, String name, String title, String phone, String email) {
        super(id,name);
        this.title = title;
        this.phone = phone;
        this.email = email;
        contacts.add(new Contact(2, phone, PhoneContactIntentBuilder.TYPE));
        contacts.add(new Contact(1, email, EmailContactIntentBuilder.TYPE));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Employee employee = (Employee) o;

        if (title != null ? !title.equals(employee.title) : employee.title != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        if (phone != null ? !phone.equals(employee.phone) : employee.phone != null) return false;
        return contacts != null ? contacts.equals(employee.contacts) : employee.contacts == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nEmployee{" + super.toString() +
                "title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", contacts=" + contacts +
                "} \n";
    }

    @Override
    public int countItems() {
        return 0;
    }

    @Override
    public Node getItem(int groupPosition) {
        return null;
    }

    @Override
    public String type() {
        return TYPE;
    }
}
