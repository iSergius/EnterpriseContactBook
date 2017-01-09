package name.isergius.android.task.maxim.enterprisecontactbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isergius on 30.12.16.
 */

public class Department implements Serializable {

    private static final long serialVersionUID = 4L;

    private long id;
    private String title;
    private List<Employee> employees = new ArrayList<Employee>();

    public Department() {}

    public Department(long id, String title, List<Employee> employees) {
        this.id = id;
        this.title = title;
        this.employees = employees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int countEmployees() {
        return employees.size();
    }

    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        if (!title.equals(that.title)) return false;
        return employees != null ? employees.equals(that.employees) : that.employees == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + (employees != null ? employees.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", employees=" + employees +
                '}';
    }
}
