package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

import android.content.Intent;
import android.net.Uri;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Contact;

/**
 * Created by isergius on 30.12.16.
 */

public class PhoneContactIntentBuilder implements ContactIntentBuilder {
    public static final String TYPE = "tel";

    private Contact contact;

    public PhoneContactIntentBuilder(Contact contact) {
        this.contact = contact;
    }

    @Override
    public Intent build() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(TYPE+":"+contact.getValue()));
        return intent;
    }
}
