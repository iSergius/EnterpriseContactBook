package name.isergius.android.task.maxim.enterprisecontactbook.services;

import android.content.SharedPreferences;

import java.io.Serializable;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Credentials;

/**
 * Created by isergius on 30.12.16.
 */

public class CredentialsStore implements Serializable {

    private static final long serialVersionUID = 8L;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REGISTERED = "registered";

    private SharedPreferences preferences;

    public CredentialsStore(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public Credentials read() throws StoreException {
        String username = preferences.getString(USERNAME, null);
        String password = preferences.getString(PASSWORD, null);
        if (username == null || password == null) {
            throw new StoreException("User is not contain");
        }
        return new Credentials(username, password);
    }

    public void save(Credentials credentials) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME, credentials.getUsername());
        editor.putString(PASSWORD, credentials.getPassword());
        editor.apply();
    }

    public void registered() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(REGISTERED, true);
        editor.apply();

    }

    public void unregistered() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(REGISTERED, false);
        editor.apply();
    }

    public boolean isRegistered() {
        return preferences.getBoolean(REGISTERED,false);
    }
}
