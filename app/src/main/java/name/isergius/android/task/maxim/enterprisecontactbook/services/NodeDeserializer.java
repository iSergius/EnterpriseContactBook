package name.isergius.android.task.maxim.enterprisecontactbook.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Department;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Employee;
import name.isergius.android.task.maxim.enterprisecontactbook.model.EmptyNode;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Node;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Organization;

/**
 * Created by isergius on 11.01.17.
 */

public class NodeDeserializer extends StdDeserializer<Node> {

    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String DEPARTMENTS = "Departments";
    public static final String EMPLOYEES = "Employees";
    public static final String TITLE = "Title";
    public static final String PHONE = "Phone";
    public static final String EMAIL = "Email";

    public NodeDeserializer() {
        super(Node.class);
    }

    @Override
    public Node deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);
        Node result = builtTree(node);
        return result;
    }

    private Node builtTree(JsonNode node) {
        while (node.has(ID) && node.has(NAME)) {
            long id = node.get(ID).asLong();
            String name = node.get(NAME).asText();
            if (node.has(DEPARTMENTS)) {
                List<Node> departments = new ArrayList<>();
                Iterator<JsonNode> elements = node.get(DEPARTMENTS).elements();
                while (elements.hasNext()) {
                    departments.add(builtTree(elements.next()));
                }
                return new Organization(id, name, departments);
            } else if (node.has(EMPLOYEES)) {
                List<Employee> employees = new ArrayList<>();
                Iterator<JsonNode> elements = node.get(EMPLOYEES).elements();
                while (elements.hasNext()) {
                    employees.add((Employee) builtTree(elements.next()));
                }
                return new Department(id, name, employees);
            } else if (node.has(TITLE)) {
                String title = null;
                String email = null;
                String phone = null;
                if (node.has(TITLE)) {
                    title = node.get(TITLE).asText();
                }
                if (node.has(PHONE)) {
                    phone = node.get(PHONE).asText();
                }
                if (node.has(EMAIL)) {
                    email = node.get(EMAIL).asText();
                }
                return new Employee(id, name, title, phone, email);
            } else {
                return new EmptyNode(id, name);
            }
        }
        return null;
    }
}
