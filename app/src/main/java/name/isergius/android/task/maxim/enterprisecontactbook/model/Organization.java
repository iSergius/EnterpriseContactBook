package name.isergius.android.task.maxim.enterprisecontactbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isergius on 02.01.17.
 */

public class Organization implements Serializable {

    private static final long serialVersionUID = 6L;

    private List<Department> departments = new ArrayList<Department>();

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public int countDepartments() {
        return departments.size();
    }

    public Department getDepartment(int id) {
        return departments.get(id);
    }

    public void add(Department department) {
        this.departments.add(department);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "departments=" + departments +
                '}';
    }
}
