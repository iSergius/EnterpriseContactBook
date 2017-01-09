package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

import android.content.Intent;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Contact;

/**
 * Created by isergius on 30.12.16.
 */

public class EmailContactIntentBuilder implements ContactIntentBuilder {
    public static final String TYPE = "email";
    private Contact contact;

    public EmailContactIntentBuilder(Contact contact) {
        this.contact = contact;
    }

    @Override
    public Intent build() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {this.contact.getValue()});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
        return Intent.createChooser(intent,"send email ...");
    }
}
