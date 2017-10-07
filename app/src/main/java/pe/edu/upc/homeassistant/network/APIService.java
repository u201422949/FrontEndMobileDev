package pe.edu.upc.homeassistant.network;

import pe.edu.upc.homeassistant.model.Client;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Fernando on 07/10/2017.
 */

public interface APIService {

    @POST("login")
    @FormUrlEncoded
    Call<Client> savePost(@Field("usuario") String user,
                          @Field("password") String password,
                          @Field("tipo") String type);
}
