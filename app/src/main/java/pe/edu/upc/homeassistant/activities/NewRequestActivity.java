package pe.edu.upc.homeassistant.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.model.Request;
import pe.edu.upc.homeassistant.model.Skill;
import pe.edu.upc.homeassistant.network.AssistantApiService;

import static pe.edu.upc.homeassistant.Constants.EXTRA_REQUEST;

public class NewRequestActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Spinner spnRequestType;
    private EditText edtSubject;
    private TextInputLayout tilSubject;
    private EditText edtDescription;
    private TextInputLayout tilDescription;
    private Button btnSend;
    private SpinnerAdapter spinnerAdapter;
    private Context context;
    private List<Skill> skills = new ArrayList<>();
    ProgressDialog progressDialog;
    TaskSkills taskSkills;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        context = NewRequestActivity.this;
        progressDialog = new ProgressDialog(context);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spnRequestType = (Spinner) findViewById(R.id.spnRequestType);
        edtSubject = (EditText) findViewById(R.id.edtSubject);
        tilSubject = (TextInputLayout) findViewById(R.id.tilSubject);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        tilDescription = (TextInputLayout) findViewById(R.id.tilDescription);
        btnSend = (Button) findViewById(R.id.btnSend);

        setSupportActionBar(toolbar);
        btnSend.setOnClickListener(this);

        taskSkills = new TaskSkills();
        taskSkills.execute();

        adaptSpinner();

    }

    private void adaptSpinner(){
        String[] requestTypes = getResources().getStringArray(R.array.array_request_type);
        spinnerAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, requestTypes);
        spnRequestType.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend){
            String description = edtDescription.getText().toString();
            String subject = edtSubject.getText().toString();
            int category = spnRequestType.getSelectedItemPosition();

            if(validateFields(subject, description, category)) {
                Skill skill = new Skill(spnRequestType.getSelectedItemPosition(), (String) spnRequestType.getSelectedItem());
                Client client = Client.from(context);
                Request request = new Request(client, skill, edtDescription.getText().toString(), edtSubject.getText().toString());

                Intent intent = new Intent(NewRequestActivity.this, ExpertsActivity.class);
                intent.putExtra("request", request);
                startActivityForResult(intent, 2);
            }
        }
    }

    private boolean validateFields(String subject, String description, int category){
        tilSubject.setErrorEnabled(false);
        tilDescription.setErrorEnabled(false);

        if (TextUtils.isEmpty(subject) || TextUtils.isEmpty(description)) {
            if (TextUtils.isEmpty(subject)) {
                tilSubject.setError(getString(R.string.new_request_empty_subject));
            }
            if (TextUtils.isEmpty(description)) {
                tilDescription.setError(getString(R.string.new_request_empty_desc));
            }

            return false;
        } else {
            return true;
        }
    }

    private void callSkillsService(){

        AndroidNetworking.get(AssistantApiService.GET_SKILLS)
                .addHeaders("Content-Type", "application/json")
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

                                JSONArray jsonArray = response.getJSONArray("");
                                for (int i = 0; i < jsonArray.length(); i++)
                                    try {
                                        skills.add(Skill.from(jsonArray.getJSONObject(i)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(getString(R.string.app_name), anError.getErrorBody());

                    }
                });
    }

    public class TaskSkills extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.login_dialog_progress));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            callSkillsService();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
        }
    }
}
