package pe.edu.upc.homeassistant.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.edu.upc.homeassistant.Constants;
import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.network.APIService;
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
    private LinearLayout lnLogin;
    ProgressDialog progressDialog;
    private Gson gson;

    public RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        progressDialog = new ProgressDialog(context);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        gson = new Gson();

        initializeViews();
    }

    private void initializeViews(){
        lnLogin = (LinearLayout) findViewById(R.id.lnLogin);
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

            if(validateFields(mail, password)) {
                callLoginService(mail, password);
            }

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

    private void callLoginService(final String mail, String password) {

        progressDialog.setMessage(getString(R.string.login_dialog_progress));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONObject client = new JSONObject();
        try {
            client.put("usuario", mail);
            client.put("password", password);
            client.put("tipo", "c");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, AssistantApiService.LOGIN_CLIENT_URL,
                client,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Client mClient = new Client();
                            mClient.setName(response.getString("nombre"))
                                    .setMail(mail)
                                    .setTipo("c")
                                    .setId(response.getString("idusuario"));

                            Toast.makeText(context, "Bienvenido "+mClient.getName(), Toast.LENGTH_LONG).show();
                            saveDataUser(mClient);
                            progressDialog.dismiss();

                            startActivity(new Intent(context, MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(lnLogin, "Hubo un error al autenticarse", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "error");
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(lnLogin, "Hubo un error al autenticarse", Snackbar.LENGTH_LONG);

                        snackbar.show();
                    }
                }
        );

        requestQueue.add(postRequest);
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
