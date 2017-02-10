package name.isergius.android.task.maxim.enterprisecontactbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by isergius on 30.12.16.
 */
public class Department extends Node implements Serializable {

    public static final String TYPE = "Department";

    private static final long serialVersionUID = 4L;

    @JsonProperty("Employees")
    private List<Employee> employees = Collections.EMPTY_LIST;

    public Department() {
    }

    public Department(long id, String name) {
        super(id, name);
    }

    public Department(long id, String name, List<Employee> employees) {
        this(id, name);
        this.employees = employees;
    }

    public int countEmployees() {
        return employees.size();
    }

    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "\nDepartment{" + super.toString() +
                "employees=" + employees +
                "} \n";
    }

    @Override
    public int countItems() {
        return employees.size();
    }

    @Override
    public Node getItem(int id) {
        return employees.get(id);
    }

    @Override
    public String type() {
        return TYPE;
    }
}
