package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.R;

/**
 * Created by isergius on 30.12.16.
 */


public class Progress extends Fragment {
    private List<LockView> views = new ArrayList<LockView>();

    public void show() {
        this.getFragmentManager()
                .beginTransaction()
                .show(this)
                .commit();
    }

    public void hide() {
        this.getFragmentManager()
                .beginTransaction()
                .hide(this)
                .commit();
    }

    public void lock() {
        for (LockView view : views) {
            view.lock();
        }
    }

    public void unlock() {
        for (LockView view : views) {
            view.unlock();
        }
    }

    public void lockAndShow() {
        lock();
        show();
    }

    public void hideAndUnlock() {
        hide();
        unlock();
    }

    public void add(LockView lockView) {
        views.add(lockView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress_bar, container, false);
    }
}
