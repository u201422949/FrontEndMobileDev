package pe.edu.upc.homeassistant.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
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
    private CoordinatorLayout cooExpert;
    private Context context;
    private Request request;
    ProgressDialog progressDialog;
    public RequestQueue requestQueue;
    List<Expert> experts = new ArrayList<>();
    List<Expert> selectedExperts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts);
        context = ExpertsActivity.this;
        progressDialog = new ProgressDialog(context);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        request = (Request) getIntent().getSerializableExtra("request");

        recyclerRequest = (RecyclerView) findViewById(R.id.recyclerRequest);
        fabExperts = (FloatingActionButton) findViewById(R.id.fabExperts);
        cooExpert = (CoordinatorLayout) findViewById(R.id.cooExpert);

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

        callExpertsService();
    }

    @Override
    public void onClick(View view) {
        if(view.equals(fabExperts)){
            callRequestBudgetService();
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

        progressDialog.setMessage("Cargando especialistas");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONObject expertParam = new JSONObject();
        try {
            expertParam.put("idespecialidad",request.getSkill().getCode()+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, AssistantApiService.GET_EXPERTS_URL,
                expertParam,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("especialistas");
                                experts = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++)
                                    try {
                                        experts.add(Expert.from(jsonArray.getJSONObject(i)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    expertsAdapter.setExperts(experts);
                                    expertsAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
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
                        }
                    }
            );

        requestQueue.add(postRequest);
    }

    private void callRequestBudgetService(){

        if (selectedExperts.size() == 0){
            Snackbar snackbar = Snackbar
                    .make(cooExpert, "Debe seleccionar por lo menos un especialista", Snackbar.LENGTH_LONG);

            snackbar.show();
        }

        progressDialog.setMessage("Enviando Solicitud de Cotización");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONObject expertParam = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject objExpert;

        try {

            for (int i = 0; i < selectedExperts.size(); i++) {
                objExpert = new JSONObject();
                objExpert.put("idusuario", selectedExperts.get(i).getId());
                array.put(objExpert);
            }

            expertParam.put("idusuario",String.valueOf(request.getSkill().getCode()));
            expertParam.put("idespecialidad",request.getClient().getId());
            expertParam.put("asunto",request.getSubject());
            expertParam.put("descripcion",request.getDescription());
            expertParam.put("cotizaciones", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                AssistantApiService.SEND_REQUEST_BUDGET_URL, expertParam,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show();
                            finish();

                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
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
                    }
                }
        );

        requestQueue.add(postRequest);
    }

}
