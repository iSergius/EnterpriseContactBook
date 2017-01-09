package name.isergius.android.task.maxim.enterprisecontactbook.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by isergius on 30.12.16.
 */

public class Employee implements Serializable {

    private static final long serialVersionUID = 5L;

    private long id;
    private String name;
    private String position;
    private Department department;
    private List<Contact> contacts = new ArrayList<Contact>();
    private byte[] photo;

    public Employee() {
    }

    public Employee(int id, String position, String name,Department department, List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
        this.contacts = contacts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (!name.equals(employee.name)) return false;
        if (position != null ? !position.equals(employee.position) : employee.position != null)
            return false;
        if (department != null ? !department.equals(employee.department) : employee.department != null)
            return false;
        if (contacts != null ? !contacts.equals(employee.contacts) : employee.contacts != null)
            return false;
        return Arrays.equals(photo, employee.photo);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", department=" + department +
                ", contacts=" + contacts +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
