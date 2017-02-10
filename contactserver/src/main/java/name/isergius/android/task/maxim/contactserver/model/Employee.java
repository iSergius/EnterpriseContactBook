package name.isergius.android.task.maxim.contactserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by isergius on 30.12.16.
 */

public class Employee extends Node implements Serializable {

    private static final long serialVersionUID = 5L;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Phone")
    private String phone;

    public Employee(long id, String name, String title, String phone, String email) {
        super(id, name);
        this.title = title;
        this.phone = phone;
        this.email = email;

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

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 31 * (title != null ? title.hashCode() : 0);
        result += 31 * (email != null ? email.hashCode() : 0);
        result += 31 * (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Employee employee = (Employee) o;

        if (title != null ? !title.equals(employee.title) : employee.title != null){
            return false;
        }
        if (email != null ? !email.equals(employee.email) : employee.email != null) {
            return false;
        }
        return phone != null ? !phone.equals(employee.phone) : employee.phone != null;
    }
}
