package name.isergius.android.task.maxim.enterprisecontactbook.services;

import android.content.SharedPreferences;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Message;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Contact;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Credentials;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Department;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Employee;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;


/**
 * Created by isergius on 05.01.17.
 */

public class ContactsServerTest {

    public static SharedPreferences sharedPreferences;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    public ContactsServer contactsServer;

    @BeforeClass
    public static void setupClass() {
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getString(ContactsServer.PROTOCOL, "https")).thenReturn("http");
        Mockito.when(sharedPreferences.getString(ContactsServer.HOST, "contact.taxsee.com")).thenReturn("localhost");
        Mockito.when(sharedPreferences.getInt(ContactsServer.PORT, 80)).thenReturn(8080);
        Mockito.when(sharedPreferences.getString(ContactsServer.PATH, "Contacts.svc")).thenReturn("Contacts.svc");
        Mockito.when(sharedPreferences.getString(CredentialsStore.USERNAME, null)).thenReturn("user");
        Mockito.when(sharedPreferences.getString(CredentialsStore.PASSWORD, null)).thenReturn("secret");
    }

    @Before
    public void setup() {
        this.contactsServer = new ContactsServer(sharedPreferences);
    }

    @Test
    public void check() throws Exception {
        stubFor(get(urlEqualTo("/Contacts.svc/Hello?login=user&password=secret"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"Message\":\"OK\",\"Success\":\"true\"}")));

        Message expected = new Message("OK", true);
        Message actual = contactsServer.check(new Credentials("user", "secret"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll() throws Exception {
        // @formatter:off
        stubFor(get(urlEqualTo("/Contacts.svc/GetAll?login=user&password=secret"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{" +
                                        "\"id\":1," +
                                        "\"name\":\"Додин Алексей Александрович\"," +
                                        "\"position\":\"Начальник\"," +
                                        "\"department\":" +
                                        "{" +
                                            "\"id\":2," +
                                            "\"title\":\"Производство\"" +
                                        "}," +
                                        "\"contacts\":" +
                                            "[{" +
                                                "\"id\":1," +
                                                "\"value\":\"8-205-850-03-77\"," +
                                                "\"type\":\"phone\"" +
                                            "},{\"" +
                                                "id\":4," +
                                                "\"value\":\"contact1@example.com\"," +
                                                "\"type\":\"email\"" +
                                            "}]" +
                                    "}]")));
        // @formatter:on
        final Employee employee = new Employee(1,
                "Начальник",
                "Додин Алексей Александрович",
                new Department(2,"Производство",new ArrayList<Employee>()),
                Arrays.asList(new Contact[]{new Contact(1, "8-205-850-03-77", "phone"),
                                        new Contact(4,"contact1@example.com","email")}));
        List<Employee> expected = new ArrayList<Employee>() {{
            add(employee);
        }};
        List<Employee> actual = contactsServer.getAll();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getPhoto() throws Exception {
        byte[] expected = new byte[]{1,2,3,4};
        stubFor(get(urlEqualTo("/Contacts.svc/GetWPhoto?login=user&password=secret&id=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "img/jpg")
                .withBody(expected)));
        byte[] actual = contactsServer.getPhoto(1);
        Assert.assertArrayEquals(expected,actual);
    }

}