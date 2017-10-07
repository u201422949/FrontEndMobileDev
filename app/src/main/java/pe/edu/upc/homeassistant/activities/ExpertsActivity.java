package pe.edu.upc.homeassistant.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.homeassistant.Constants;
import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.adapters.ExpertsAdapter;
import pe.edu.upc.homeassistant.adapters.HistoryAdapter;
import pe.edu.upc.homeassistant.adapters.RecyclerMultiClickListener;
import pe.edu.upc.homeassistant.adapters.RecyclerViewClickListener;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.model.Expert;
import pe.edu.upc.homeassistant.model.Request;
import pe.edu.upc.homeassistant.model.Skill;
import pe.edu.upc.homeassistant.network.AssistantApiService;

import static java.security.AccessController.getContext;

/**
 * Created by Fernando on 27/09/2017.
 */

public class ExpertsActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerRequest;
    private ExpertsAdapter expertsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabExperts;
    private Context context;
    ProgressDialog progressDialog;
    TaskExperts taskExperts;
    List<Expert> experts = new ArrayList<>();
    List<Expert> selectedExperts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts);
        context = ExpertsActivity.this;

        recyclerRequest = (RecyclerView) findViewById(R.id.recyclerRequest);
        fabExperts = (FloatingActionButton) findViewById(R.id.fabExperts);

        //TODO: Set original list of requests
        experts = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1, "Carpinteria"));
        skills.add(new Skill(2, "Gasfiteria"));
        skills.add(new Skill(3, "Costura"));
        experts.add(new Expert("Augusto Alva", "aalva@gmail.cm", "Me dedido a mis estudios", "984562121", 3, skills));
        experts.add(new Expert("Fabiana Cuenca", "fabc@gmail.cm", "Otra descripcion", "321153132", 3, skills));
        experts.add(new Expert("Pedro Toro", "pedrotoro@gmail.cm", "dasdasdasdasdas", "3894421", 2, skills));
        experts.add(new Expert("Fernando Castro", "fercas@gmail.cm", "Mi pais es primero", "9999999", 1, skills));

        expertsAdapter = new ExpertsAdapter(experts, selectedExperts);
        layoutManager = new LinearLayoutManager(ExpertsActivity.this);
        recyclerRequest.setItemAnimator(new DefaultItemAnimator());
        recyclerRequest.setAdapter(expertsAdapter);
        recyclerRequest.setLayoutManager(layoutManager);

        fabExperts.setOnClickListener(this);

        recyclerRequest.addOnItemTouchListener(new RecyclerMultiClickListener(this, recyclerRequest,
                new RecyclerMultiClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                multi_select(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                selectedExperts = new ArrayList<Expert>();
                multi_select(position);
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if(view.equals(fabExperts)){
            taskExperts = new TaskExperts();
            taskExperts.execute();
        }
    }


    public void multi_select(int position) {
        if (selectedExperts.contains(experts.get(position)))
            selectedExperts.remove(experts.get(position));
        else
            selectedExperts.add(experts.get(position));

        refreshAdapter();
    }

    public void refreshAdapter() {
        expertsAdapter.setSelectedExperts(selectedExperts);
        expertsAdapter.setExperts(experts);
        expertsAdapter.notifyDataSetChanged();
    }

    private void callExpertsService(){

        //TODO: Validar la funcionalidad del servicio
        AndroidNetworking.post(AssistantApiService.GET_EXPERTS_URL)
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

                                startActivity(new Intent(context, MainActivity.class));
                                finish();


                            }else{

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


    public class TaskExperts extends AsyncTask {


        public TaskExperts() {
            super();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.login_dialog_progress));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            callExpertsService();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
        }
    }
}
