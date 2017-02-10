package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;


import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.R;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Department;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Employee;
import name.isergius.android.task.maxim.enterprisecontactbook.model.EmptyNode;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Node;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Organization;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.EmployeeActivity;

/**
 * Created by isergius on 11.01.17.
 */

public class OrganizationView extends Fragment implements Serializable {
    private Node root;

    public OrganizationView(Node root) {
        this.root = root;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organization_list, container, false);
        buildView(root, R.id.organization_container, new ArrayList<Item>(1), false, 0);

        return view;
    }

    private void buildView(Node node, int containerId, List<Item> fragments, boolean hided, int nesting) {
        Item fragment = null;
        switch (node.type()) {
            case EmptyNode.TYPE:
            case Department.TYPE:
            case Organization.TYPE:
                ExpandableItem expandableItem = new ExpandableItem(node, nesting);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(containerId, expandableItem)
                        .commit();
                ++nesting;
                for (int i = 0; i < node.countItems(); i++) {
                    Node item = node.getItem(i);
                    buildView(item, containerId, expandableItem.getItems(), true, nesting);
                }
                fragments.add(expandableItem);
                fragment = expandableItem;
                break;
            case Employee.TYPE:
                Employee employee = (Employee) node;
                fragment = new EmployeeItem(employee, nesting);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(containerId, fragment)
                        .commit();
                fragments.add(fragment);
                break;
        }
        if (hided) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .hide(fragment)
                    .commit();
        }
    }

    private class ExpandableItem extends Item implements View.OnClickListener, Serializable {
        private LinearLayout view;
        private Node node;
        private TextView title;
        private ImageView image;
        private List<Item> items = new ArrayList<>();
        private boolean expanded = false;
        private int nesting;

        public ExpandableItem() {}

        public ExpandableItem(Node node, int nesting) {
            this.node = node;
            this.nesting = nesting;
        }

        public List<Item> getItems() {
            return items;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = (LinearLayout) inflater.inflate(R.layout.fragment_expandable_item, container, false);

            int scale = (int) (getResources().getDisplayMetrics().density * 16);
            view.setPadding(view.getPaddingLeft() + (scale * nesting), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
            view.setOnClickListener(this);
            view.setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_dim_dark));
            view.setShowDividers(LinearLayout.SHOW_DIVIDER_END);

            title = (TextView) view.findViewById(R.id.fragment_expandable_item_title);
            image = (ImageView) view.findViewById(R.id.fragment_expandable_item_image);
            title.setText(node.getName());
            return view;
        }

        @Override
        public void onClick(View v) {
            if (expanded) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                expanded = false;
                hide();
            } else {
                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                expanded = true;
                show();
            }
        }

        @Override
        public void show() {
            if (expanded) {
                for (Item item : items) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .show(item)
                            .commit();
                    item.show();
                }
            }
        }

        @Override
        public void hide() {
            for (Item item : items) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .hide(item)
                        .commit();
                item.hide();
            }
        }
    }

    private class EmployeeItem extends Item implements View.OnClickListener {
        private LinearLayout view;
        private Employee employee;
        private TextView position;
        private TextView name;
        private int nesting;

        public EmployeeItem(Employee employee, int nesting) {
            this.employee = employee;
            this.nesting = nesting;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = (LinearLayout) inflater.inflate(R.layout.fragment_employee_item, container, false);
            view.setOnClickListener(this);
            view.setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_dim_dark));
            view.setShowDividers(LinearLayout.SHOW_DIVIDER_END);
            position = (TextView) view.findViewById(R.id.fragment_employee_item_position_title);
            name = (TextView) view.findViewById(R.id.fragment_employee_item_name_title);
            position.setText(employee.getTitle());
            name.setText(employee.getName());
            return view;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), EmployeeActivity.class);
            intent.putExtra(Employee.TYPE,employee);
            startActivity(intent);
        }

        @Override
        void show() {

        }

        @Override
        void hide() {

        }
    }

    private abstract class Item extends Fragment {

        abstract void show();

        abstract void hide();
    }
}
