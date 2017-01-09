package name.isergius.android.task.maxim.enterprisecontactbook.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.R;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Contact;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Employee;
import name.isergius.android.task.maxim.enterprisecontactbook.services.ContactsServer;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.AuthService;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.ContactIntentBuilder;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.DefaultIntentBuilder;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.EmailContactIntentBuilder;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.LockView;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.MainMenu;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.PhoneContactIntentBuilder;
import name.isergius.android.task.maxim.enterprisecontactbook.ui.components.Progress;

public class EmployeeActivity extends AppCompatActivity {

    private final int activityId = R.id.activity_employee;

    private AuthService authService;

    private Employee employee;

    private Photo photo;
    private Position position;
    private Name name;
    private ContactList contactList;
    private MainMenu mainMenu;
    private Progress progress;
    private PhotoContainer photoContainer;
    private ImageLoadingTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        employee = (Employee) getIntent().getExtras().get("employee");
        photo = new Photo();
        position = new Position(employee.getPosition());
        name = new Name(employee.getName());
        contactList = new ContactList(employee.getContacts());
        authService = new AuthService();
        mainMenu = new MainMenu(authService);
        photoContainer = new PhotoContainer();
        progress = new Progress();
        progress.add(photo);
        getSupportFragmentManager()
                .beginTransaction()
                .add(activityId, mainMenu)
                .add(activityId, authService)
                .add(photoContainer.getId(), progress)
                .hide(progress)
                .commit();
        task = new ImageLoadingTask(progress, photo);
        task.execute(employee.getId());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task.getStatus().equals(AsyncTask.Status.RUNNING)) {
            task.cancel(true);
        }
    }

    private class ImageLoadingTask extends AsyncTask<Long, Void, byte[]> {
        private final Photo photo;
        private ContactsServer contactsServer;
        private Progress progress;

        ImageLoadingTask(Progress progress, Photo photo) {
            contactsServer = new ContactsServer(getSharedPreferences(AuthService.PREFERENCES, MODE_PRIVATE));
            this.progress = progress;
            this.photo = photo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.lockAndShow();
        }

        @Override
        protected byte[] doInBackground(Long... params) {
            try {
                return contactsServer.getPhoto(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            photo.show(bytes);
            progress.hideAndUnlock();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private class PhotoContainer {
        private RelativeLayout view = (RelativeLayout) findViewById(R.id.employee_photo_container);

        public int getId() {
            return view.getId();
        }
    }

    private class Photo implements LockView {
        private AppCompatImageView view = (AppCompatImageView) findViewById(R.id.employee_photo);

        void show(byte[] photo) {
            if (photo != null) {
                view.setBackgroundDrawable(null);
                view.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            } else {

            }
        }

        public int getId() {
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

    private class Position {
        private TextView view = (TextView) findViewById(R.id.position_title);

        Position(String position) {
            view.setText(position);
        }
    }

    private class Name {
        private TextView view = (TextView) findViewById(R.id.name_title);

        Name(String name) {
            view.setText(name);
        }
    }

    private class ContactList {
        private ListView view = (ListView) findViewById(R.id.contact_list);

        ContactList(List<Contact> contacts) {
            view.setAdapter(new ContactListViewAdapter(contacts));
        }

        private class ContactListener implements View.OnClickListener {
            private Contact contact;
            private ContactIntentBuilder intentBuilder;

            ContactListener(Contact contact) {
                this.contact = contact;
                switch (contact.getType()) {
                    case PhoneContactIntentBuilder.TYPE:
                        intentBuilder = new PhoneContactIntentBuilder(contact);
                        break;
                    case EmailContactIntentBuilder.TYPE:
                        intentBuilder = new EmailContactIntentBuilder(contact);
                        break;
                    default:
                        intentBuilder = new DefaultIntentBuilder(contact);
                }
            }

            @Override
            public void onClick(View v) {
                startActivity(intentBuilder.build());
            }
        }

        private class ContactListViewAdapter extends BaseAdapter {
            private List<Contact> contacts;

            ContactListViewAdapter(List<Contact> contacts) {
                this.contacts = contacts;
            }

            @Override
            public int getCount() {
                return contacts.size();
            }

            @Override
            public Contact getItem(int position) {
                return contacts.get(position);
            }

            @Override
            public long getItemId(int position) {
                return contacts.get(position).getId();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Contact contact = contacts.get(position);
                View view = inflate(R.layout.fragment_contact_item, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.contact_value);
                textView.setText(contact.getValue());
                view.setOnClickListener(new ContactListener(contact));
                return view;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            private View inflate(int layout, View view, ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    return inflater.inflate(layout, parent, false);
                } else {
                    return view;
                }
            }
        }
    }
}
