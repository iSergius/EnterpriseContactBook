package name.isergius.android.task.maxim.enterprisecontactbook.services;

import org.json.JSONObject;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Message;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Node;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Organization;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by isergius on 06.01.17.
 */

public interface ContactsApi {

    @GET("Hello")
    Call<Message> check(@Query("login") String username, @Query("password") String pass);

    @GET("GetAll")
    Call<Node> getAll(@Query("login") String username, @Query("password") String pass);

    @GET("GetWPhoto")
    Call<ResponseBody> getPhoto(@Query("login") String username, @Query("password") String pass, @Query("id") long id);

}
