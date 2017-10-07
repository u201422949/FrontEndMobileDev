package pe.edu.upc.homeassistant.network;

/**
 * Created by Fernando on 02/10/2017.
 */

public class AssistantApiService {

    public static String LOGIN_CLIENT_URL = "http://45.63.21.47/login";
    public static String REGISTER_CLIENT_URL = "";
    public static String GET_REQUESTS = "";
    public static String GET_SKILLS = "";
    public static String GET_EXPERTS_URL = "";
    public static String SEND_REQUEST_BUDGET_URL = "";

    private AssistantApiService() {}

    public static final String BASE_URL = "http://138.197.78.115/homeassistant/v1/index.php/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
