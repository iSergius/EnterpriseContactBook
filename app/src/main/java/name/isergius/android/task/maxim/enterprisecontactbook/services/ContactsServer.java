package name.isergius.android.task.maxim.enterprisecontactbook.services;

import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.io.InterruptedIOException;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Message;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Credentials;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Node;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Organization;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by isergius on 06.01.17.
 */

public class ContactsServer {

    private static final long serialVersionUID = 12L;

    private static final String PREFIX = "";
    public static final String PATH = PREFIX + "path";
    public static final String HOST = PREFIX + "host";
    public static final String PORT = PREFIX + "port";
    public static final String PROTOCOL = PREFIX + "protocol";

    private CredentialsStore credentialsDao;

    private ContactsApi contactsApi;


    public ContactsServer(SharedPreferences preferences) {
        credentialsDao = new CredentialsStore(preferences);
        HttpUrl url = new HttpUrl.Builder()
                .scheme(preferences.getString(PROTOCOL, "https"))
                .host(preferences.getString(HOST, "contact.taxsee.com"))
                .port(preferences.getInt(PORT, 443))
                .addPathSegments(preferences.getString(PATH, "Contacts.svc"))
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("Node deserializer").addDeserializer(Node.class, new NodeDeserializer());
        objectMapper.registerModule(module);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl(url + "/")
                .build();
        contactsApi = retrofit.create(ContactsApi.class);
    }

    public Message check(Credentials credentials) throws IOException {
        return contactsApi
                .check(credentials.getUsername(), credentials.getPassword())
                .execute()
                .body();
    }

    public Node getAll() throws IOException {
        try {
            Credentials credentials = credentialsDao.read();
            return contactsApi
                    .getAll(credentials.getUsername(), credentials.getPassword())
                    .execute()
                    .body();
        } catch (StoreException e) {
            throw new IOException(e);
        }
    }

    public byte[] getPhoto(long id) throws IOException {
        try {
            Credentials credentials = credentialsDao.read();
            return contactsApi
                    .getPhoto(credentials.getUsername(), credentials.getPassword(), id)
                    .execute()
                    .body()
                    .bytes();
        } catch (StoreException | InterruptedIOException e) {
            throw new IOException(e);
        }
    }

}
