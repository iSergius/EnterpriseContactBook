package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import name.isergius.android.task.maxim.enterprisecontactbook.R;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Message;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Credentials;
import name.isergius.android.task.maxim.enterprisecontactbook.services.ContactsServer;
import name.isergius.android.task.maxim.enterprisecontactbook.services.CredentialsStore;
import name.isergius.android.task.maxim.enterprisecontactbook.services.StoreException;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.LoginActivity;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.EmployeeListActivity;

/**
 * Created by isergius on 02.01.17.
 */

public class AuthService extends Fragment implements Serializable {

    private static final long serialVersionUID = 10L;
    public static final String PREFERENCES = "preferences";

    private CredentialsStore credentialsDao;
    private ContactsServer server;

    private AuthTask task;

    public AuthService() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        credentialsDao = new CredentialsStore(preferences);
        server = new ContactsServer(preferences);
    }



    public void login(Credentials credentials) throws AuthException {
        try {
            AuthTask task = new AuthTask();
            task.execute(credentials);
            Message message = task.get();
            if (message == null) {
                showNetworkError();
            } else if (message.isSuccess()) {
                credentialsDao.save(credentials);
                credentialsDao.registered();
                enter();
            } else {
                throw new AuthException(message.getMessage());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void logout() throws AuthException {
        credentialsDao.unregistered();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void isAuthenticated() throws AuthException {
        if (credentialsDao.isRegistered()) {
            try {
                Credentials credentials = credentialsDao.read();
                task = new AuthTask();
                task.execute(credentials);
                Message message = task.get();
                if (message == null) {
                    showNetworkError();
                } else if (message.isSuccess()) {
                    enter();
                }
            } catch (StoreException e) {
                throw new AuthException("store error");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void enter() {
        Intent intentList = new Intent(getContext(), EmployeeListActivity.class);
        startActivity(intentList);
    }

    private class AuthTask extends AsyncTask<Credentials,Void,Message> {

        @Override
        protected Message doInBackground(Credentials... credentials) {
            try {
                return server.check(credentials[0]);
            } catch (IOException e) {

            }
            return null;
        }
    }

    private void showNetworkError() {
        Toast.makeText(getActivity().getApplication(), R.string.contact_server_unavalable,Toast.LENGTH_SHORT);
    }

}
