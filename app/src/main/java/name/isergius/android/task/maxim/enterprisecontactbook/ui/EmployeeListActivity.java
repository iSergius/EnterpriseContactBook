package name.isergius.android.task.maxim.enterprisecontactbook.ui;

import android.animation.LayoutTransition;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import name.isergius.android.task.maxim.enterprisecontactbook.R;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Node;
import name.isergius.android.task.maxim.enterprisecontactbook.services.ContactsServer;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.AuthService;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.MainMenu;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.OrganizationView;

public class EmployeeListActivity extends AppCompatActivity {

    private int activityId = R.id.activity_employee_list;

    private OrganizationView organizationTree;
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
            organizationTree = new OrganizationView(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        authService = new AuthService();
        mainMenu = new MainMenu(authService);
        getSupportFragmentManager()
                .beginTransaction()
                .add(activityId, mainMenu)
                .add(activityId, authService)
                .add(activityId, organizationTree)
                .commit();
//        LinearLayout layout = (LinearLayout) findViewById(R.id.organization_container);
//        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
    }


    private class OrganizationBuildTask extends AsyncTask<Void, Void, Node> {
        private ContactsServer contactsServer;

        OrganizationBuildTask() {
            contactsServer = new ContactsServer(getSharedPreferences(AuthService.PREFERENCES, MODE_PRIVATE));
        }

        @Override
        protected Node doInBackground(Void... params) {
            Node node = null;
            try {
                node = contactsServer.getAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return node;
        }
    }
}
