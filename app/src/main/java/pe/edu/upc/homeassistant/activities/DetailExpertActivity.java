package pe.edu.upc.homeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.adapters.SkillsAdapter;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.model.Expert;
import pe.edu.upc.homeassistant.model.Skill;
import pe.edu.upc.homeassistant.network.AssistantApiService;

/**
 * Created by Fernando on 27/09/2017.
 */

public class DetailExpertActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgProfile;
    private TextView txtName;
    private TextView txtDescription;
    private TextView txtPhone;
    private TextView txtMail;
    private RatingBar ratingExpert;
    private RecyclerView recyclerSkills;
    private FloatingActionButton fabSendRequest;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private Expert expert;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_detail);
        context = DetailExpertActivity.this;
        expert = Expert.from(getIntent().getExtras());

        initializeViews();
        setValueToFields();
    }

    private void initializeViews(){
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtMail = (TextView) findViewById(R.id.txtMail);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        ratingExpert = (RatingBar) findViewById(R.id.ratingExpert);
        recyclerSkills = (RecyclerView) findViewById(R.id.recyclerSkills);
        fabSendRequest = (FloatingActionButton) findViewById(R.id.fabSendRequest);

        fabSendRequest.setOnClickListener(this);
    }

    private void setValueToFields(){
        txtName.setText(expert.getName());
        txtMail.setText(expert.getMail());
        txtDescription.setText(expert.getDescription());
        txtPhone.setText(expert.getPhone());
        ratingExpert.setRating(expert.getRate());

        layoutManager = new LinearLayoutManager(context);
        adapter = new SkillsAdapter(expert.getSkills());
        recyclerSkills.setLayoutManager(layoutManager);
        recyclerSkills.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fabSendRequest)){
            sendRequestBudget();
        }
    }

    private void sendRequestBudget(){
        AndroidNetworking.post(AssistantApiService.SEND_REQUEST_BUDGET_URL)
                .addBodyParameter("idSkill", "")
                .addBodyParameter("subject", "")
                .addBodyParameter("description", "1")
                .addBodyParameter("idExpert", "1")
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
                                //saveDataUser(client);

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
}
