package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.Serializable;

import name.isergius.android.task.maxim.enterprisecontactbook.R;

/**
 * Created by isergius on 03.01.17.
 */

public class MainMenu extends Fragment implements Serializable {

    private static final long serialVersionUID = 11L;

    private AuthService authService;

    public MainMenu(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.logout).setOnMenuItemClickListener(new Logout());
    }

    private class Logout implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            try {
                authService.logout();
                return true;
            } catch (AuthException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
