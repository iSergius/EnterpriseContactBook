package name.isergius.android.task.maxim.contactserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Created by isergius on 09.01.17.
 */

@MappedSuperclass
public abstract class Node implements Serializable {

    private static final long serialVersionUID = 18L;

    @JsonProperty("ID")
    private long id;
    @JsonProperty("Name")
    private String name;

    public Node() {
    }

    public Node(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;
        return name != null ? name.equals(node.name) : node.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + "\'";
    }

}
