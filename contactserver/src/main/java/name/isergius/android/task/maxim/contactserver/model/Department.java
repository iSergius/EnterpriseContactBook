package name.isergius.android.task.maxim.contactserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by isergius on 30.12.16.
 */
@Entity
public class Department implements Serializable {

    private static final long serialVersionUID = 4L;

    private long id;
    private String title;
//    private List<Employee> employees = new ArrayList<Employee>();

    public Department() {}

    public Department(long id, String title/*, List<Employee> employees*/) {
        this.id = id;
        this.title = title;
/*        this.employees = employees;*/
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

/*    @JsonIgnore
    @OneToMany
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
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
