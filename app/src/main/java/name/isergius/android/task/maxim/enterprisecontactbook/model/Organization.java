package name.isergius.android.task.maxim.enterprisecontactbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by isergius on 02.01.17.
 */

public class Organization extends Node implements Serializable {

    public static final String TYPE = "Organization";

    private static final long serialVersionUID = 19L;
    @JsonProperty("Departments")
    private List<Node> departments;

    public Organization() {
    }

    public Organization(long id, String name, List<Node> departments) {
        super(id, name);
        this.departments = departments;
    }


    public List<Node> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Node> departments) {
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Organization that = (Organization) o;

        return departments != null ? departments.equals(that.departments) : that.departments == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nOrganization{" + super.toString() +
                "departments=" + departments +
                "} \n";
    }

    @Override
    public int countItems() {
        return departments.size();
    }

    @Override
    public Node getItem(int id) {
        return departments.get(id);
    }

    @Override
    public String type() {
        return TYPE;
    }
}
