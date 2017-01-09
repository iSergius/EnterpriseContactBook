package name.isergius.android.task.maxim.enterprisecontactbook.services;

import java.util.List;

import name.isergius.android.task.maxim.enterprisecontactbook.model.Message;
import name.isergius.android.task.maxim.enterprisecontactbook.model.Employee;
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
    Call<List<Employee>> getAll(@Query("login") String username, @Query("password") String pass);

    @GET("GetWPhoto")
    Call<ResponseBody> getPhoto(@Query("login") String username, @Query("password") String pass, @Query("id") long id);

}
