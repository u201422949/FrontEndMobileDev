package pe.edu.upc.homeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.homeassistant.Constants;
import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.network.AssistantApiService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignIn;
    private Button btnSignUp;
    private TextView txtRecovery;
    private EditText edtUser;
    private EditText edtPassword;
    private TextInputLayout tilUser;
    private TextInputLayout tilPassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        initializeViews();
    }

    private void initializeViews(){
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        txtRecovery = (TextView) findViewById(R.id.txtRecovery);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        tilUser = (TextInputLayout) findViewById(R.id.tilUser);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        txtRecovery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSignUp){
            startActivity(new Intent(context, RegisterActivity.class));
        }else if(view == btnSignIn){
            String mail = edtUser.getText().toString();
            String password = edtPassword.getText().toString();

            if(validateFields(mail, password))
                callLoginService(mail, password);

        }else if(view == txtRecovery){
            startActivity(new Intent(context, RecoveryActivity.class));
        }
    }

    private boolean validateFields(String mail, String password){
        tilUser.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);

        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(mail)) {
                tilUser.setError(getString(R.string.login_empty_username));
            }
            if (TextUtils.isEmpty(password)) {
                tilPassword.setError(getString(R.string.login_empty_password));
            }

            return false;
        } else {
            return true;
        }
    }

    private void callLoginService(String mail, String password){

        //TODO: Validar la funcionalidad del servicio
        AndroidNetworking.post(AssistantApiService.LOGIN_CLIENT_URL)
                .addBodyParameter("email", mail)
                .addBodyParameter("password", password)
                .addBodyParameter("type", "1")
                .setPriority(Priority.MEDIUM)
                .setTag(getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if("error".equalsIgnoreCase(response.getString("status"))){
                                Log.e(getString(R.string.app_name), response.getString("message"));
                                return;
                            }
                            Boolean status = response.getBoolean("status");

                            if(status == true){

                                Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show();

                                Client client = Client.from(response.getJSONArray("object").getJSONObject(0));
                                saveDataUser(client);

                                startActivity(new Intent(context, MainActivity.class));
                                finish();


                            }else{
                                Toast toast = Toast.makeText(context, "Incorrecto", Toast.LENGTH_LONG);
                                toast.show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(getString(R.string.app_name), anError.getLocalizedMessage());
                    }
                });
    }

    private void saveDataUser(Client client){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(client);
        editor.putString(Constants.SP_DATA_CLIENT, json);
        editor.commit();
    }
}
