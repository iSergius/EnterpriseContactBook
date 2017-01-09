package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

import android.content.Intent;
import android.net.Uri;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Contact;

/**
 * Created by isergius on 07.01.17.
 */
public class DefaultIntentBuilder implements ContactIntentBuilder {
    private Contact contact;

    public DefaultIntentBuilder(Contact contact) {
        this.contact = contact;
    }

    @Override
    public Intent build() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(contact.getType()+":"+contact.getValue()));
        return intent;
    }
}
