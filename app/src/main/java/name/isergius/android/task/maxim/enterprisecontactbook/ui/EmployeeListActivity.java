package name.isergius.android.task.maxim.enterprisecontactbook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import name.isergius.android.task.maxim.enterprisecontactbook.R;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Department;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Employee;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Organization;
import name.isergius.android.task.maxim.enterprisecontactbook.services.ContactsServer;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.AuthService;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.MainMenu;

public class EmployeeListActivity extends AppCompatActivity {

    private int activityId = R.id.activity_employee_list;

    private OrganizationTree organizationTree;
    private MainMenu mainMenu;
    private AuthService authService;

    private OrganizationBuildTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        try {
            task = new OrganizationBuildTask();
            task.execute();
            organizationTree = new OrganizationTree(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        authService = new AuthService();
        mainMenu = new MainMenu(authService);
        getSupportFragmentManager()
                .beginTransaction()
                .add(activityId, mainMenu)
                .add(activityId, authService)
                .commit();
    }


    private class OrganizationBuildTask extends AsyncTask<Void,Void,Organization> {
        private ContactsServer contactsServer;

        OrganizationBuildTask() {
            contactsServer = new ContactsServer(getSharedPreferences(AuthService.PREFERENCES,MODE_PRIVATE));
        }

        @Override
        protected Organization doInBackground(Void... params) {
            Organization org = new Organization();
            try {
                Map<Department, List<Employee>> map = new HashMap<>();
                List<Employee> employees = contactsServer.getAll();
                for (Employee employee : employees) {
                    Department department = employee.getDepartment();
                    if (map.get(department) == null) {
                        map.put(department, new ArrayList<Employee>());
                    }
                    map.get(department).add(employee);
                }
                for (Department department : map.keySet()) {
                    department.setEmployees(map.get(department));
                    org.add(department);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return org;
        }
    }

    private class OrganizationTree {
        private ExpandableListView view = (ExpandableListView) findViewById(R.id.organization_tree);

        OrganizationTree(Organization organization) {
            view.setAdapter(new OrganizationTreeViewAdapter(organization));
            view.setGroupIndicator(null);
        }

        private class EmployeeItemListener implements View.OnClickListener {
            private Employee employee;

            EmployeeItemListener(Employee employee) {
                this.employee = employee;
            }

            @Override
            public void onClick(View v) {
                Intent intentList = new Intent(getApplicationContext(), EmployeeActivity.class);
                intentList.putExtra("employee", employee);
                startActivity(intentList);
            }
        }

        private class OrganizationTreeViewAdapter extends BaseExpandableListAdapter {
            private Organization organization;

            OrganizationTreeViewAdapter(Organization organization) {
                this.organization = organization;
            }

            @Override
            public int getGroupCount() {
                return organization.countDepartments();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return organization.getDepartment(groupPosition).countEmployees();
            }

            @Override
            public Department getGroup(int groupPosition) {
                return organization.getDepartment(groupPosition);
            }

            @Override
            public Employee getChild(int groupPosition, int childPosition) {
                return organization.getDepartment(groupPosition).getEmployee(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return organization.getDepartment(groupPosition).getId();
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return organization.getDepartment(groupPosition).getEmployee(childPosition).getId();
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                View view = inflate(R.layout.fragment_department_item, convertView, parent);
                TextView departmentTitle = (TextView) view.findViewById(R.id.fragment_department_item_title);
                ImageView imageView = (ImageView) view.findViewById(R.id.fragment_department_item_image);
                if (isExpanded) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                }
                departmentTitle.setText(organization.getDepartment(groupPosition).getTitle());
                return view;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                Employee employee = organization.getDepartment(groupPosition).getEmployee(childPosition);
                View view = inflate(R.layout.fragment_employee_item, convertView, parent);
                TextView textViewName = (TextView) view.findViewById(R.id.fragment_employee_item_name_title);
                TextView textViewPosition = (TextView) view.findViewById(R.id.fragment_employee_item_position_title);
                textViewName.setText(employee.getName());
                textViewPosition.setText(employee.getPosition());
                view.setOnClickListener(new EmployeeItemListener(employee));
                return view;
            }

            private View inflate(int layout, View view, ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    return inflater.inflate(layout, parent, false);
                } else {
                    return view;
                }
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        }
    }

}
