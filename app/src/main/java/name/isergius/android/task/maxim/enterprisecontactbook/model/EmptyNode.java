package name.isergius.android.task.maxim.enterprisecontactbook.model;

import java.io.Serializable;

/**
 * Created by isergius on 11.01.17.
 */

public class EmptyNode extends Node implements Serializable {

    public static final String TYPE = "EmptyNode";

    public EmptyNode(long id, String name) {
        super(id, name);
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
