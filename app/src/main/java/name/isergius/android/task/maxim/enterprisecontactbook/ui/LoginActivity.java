package name.isergius.android.task.maxim.enterprisecontactbook.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import name.isergius.android.task.maxim.enterprisecontactbook.R;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Credentials;
import name.isergius.android.task.maxim.enterprisecontactbook.services.ContactsServer;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.AuthException;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.AuthService;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.LockView;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.Progress;

/**
 * Created by isergius on 30.12.16.
 */

public class LoginActivity extends AppCompatActivity {

    private final int activityId = R.id.activity_login;

    private AuthService authService;
    private UsernameInput usernameInput;
    private PasswordInput passwordInput;
    private LoginButton loginButton;
    private Form form;
    private Progress progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.initPreferences();
        this.createServices();
        this.createUiComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.progress.lockAndShow();
        try {
            this.authService.isAuthenticated();
        } catch (AuthException e) {

        }
        this.progress.hideAndUnlock();
    }

    private void createServices() {
        this.authService = new AuthService();
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(this.activityId, this.authService)
                .commit();
    }

    private void createUiComponents() {
        this.setContentView(R.layout.activity_login);
        this.usernameInput = new UsernameInput();
        this.passwordInput = new PasswordInput();
        this.loginButton = new LoginButton();
        this.form = new Form();
        this.progress = new Progress();
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(form.getId(), progress)
                .hide(progress)
                .commit();
        this.progress.add(this.usernameInput);
        this.progress.add(this.passwordInput);
        this.progress.add(this.loginButton);
    }

    private void initPreferences() {
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(ContactsServer.PROTOCOL,"http");
        edit.putString(ContactsServer.HOST,"192.168.0.2");
        edit.putInt(ContactsServer.PORT,8080);
        edit.putString(ContactsServer.PATH,"");
        edit.apply();
    }

    private void attemptLogin() {
        progress.lockAndShow();
        if (usernameInput.isValid() && passwordInput.isValid()) {
            Credentials credentials = new Credentials(usernameInput.getValue(), passwordInput.getValue());
            try {
                this.authService.login(credentials);
            } catch (AuthException e) {
                usernameInput.showError(getString(R.string.wrong_username));
            }
        }
        progress.hideAndUnlock();
    }

    private class Form implements LockView {
        private LinearLayout view = (LinearLayout) findViewById(R.id.form);

        int getId() {
            return view.getId();
        }

        @Override
        public void lock() {
            view.setVisibility(View.GONE);
        }

        @Override
        public void unlock() {
            view.setVisibility(View.VISIBLE);
        }
    }

    private class UsernameInput implements LockView {
        private EditText view = (EditText) findViewById(R.id.username);

        boolean isValid() {
            /*if (!view.getText().toString().matches("[A-Z,a-z]+")) {
                view.setError(getString(R.string.username_validation_error));
                return false;
            }*/
            return true;
        }

        String getValue() {
            return this.view.getText().toString();
        }

        @Override
        public void lock() {
            view.setVisibility(View.GONE);
        }

        @Override
        public void unlock() {
            view.setVisibility(View.VISIBLE);
        }

        void showError(String error) {
            view.setError(error);
        }
    }

    private class PasswordInput implements TextView.OnEditorActionListener, LockView {
        private EditText view = (EditText) findViewById(R.id.password);

        PasswordInput() {
            view.setOnEditorActionListener(this);
        }

        boolean isValid() {
            if (view.getText().toString().length() < 4) {
                view.setError(getString(R.string.password_validation_error));
                return false;
            }
            return true;
        }

        String getValue() {
            return view.getText().toString();
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        }

        @Override
        public void lock() {
            view.setVisibility(View.GONE);
        }

        @Override
        public void unlock() {
            view.setVisibility(View.VISIBLE);
        }
    }

    private class LoginButton implements View.OnClickListener, LockView {
        private Button view = (Button) findViewById(R.id.login);

        LoginButton() {
            this.view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            attemptLogin();
        }

        @Override
        public void lock() {
            view.setVisibility(View.GONE);
        }

        @Override
        public void unlock() {
            view.setVisibility(View.VISIBLE);
        }
    }
}
