package pe.edu.upc.homeassistant.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pe.edu.upc.homeassistant.activities.LoginActivity;
import pe.edu.upc.homeassistant.activities.MainActivity;
import pe.edu.upc.homeassistant.activities.NewRequestActivity;
import pe.edu.upc.homeassistant.adapters.HistoryAdapter;
import pe.edu.upc.homeassistant.adapters.RequestAdapter;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.model.Request;
import pe.edu.upc.homeassistant.network.AssistantApiService;

import static pe.edu.upc.homeassistant.Constants.EXTRA_REQUEST;

public class RequestFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private static int CODE_NEW_REQUEST = 101;
    private RecyclerView recyclerRequest;
    private RequestAdapter historyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabNewRequest;
    private SwipeRefreshLayout swipeRequests;
    private TaskRequests taskRequests;
    private Context context;
    ProgressDialog progressDialog;

    List<Request> lsRequest = new ArrayList<>();

    public RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerRequest = (RecyclerView) view.findViewById(R.id.recyclerRequest);
        fabNewRequest = (FloatingActionButton) view.findViewById(R.id.fabNewRequest);
        swipeRequests = (SwipeRefreshLayout) view.findViewById(R.id.swipeRequests);

        historyAdapter = new RequestAdapter(lsRequest);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerRequest.setItemAnimator(new DefaultItemAnimator());
        recyclerRequest.setAdapter(historyAdapter);
        recyclerRequest.setLayoutManager(layoutManager);

        fabNewRequest.setOnClickListener(this);
        swipeRequests.setOnRefreshListener(this);

        swipeRequests.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fabNewRequest)){
            Intent intent = new Intent(getContext(), NewRequestActivity.class);
            getActivity().startActivityForResult(intent, CODE_NEW_REQUEST);
        }
    }

    @Override
    public void onRefresh() {
       /* adapter.clear();
        // ...the data has come back, add new items to your adapter...
        adapter.addAll(...);
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);*/
    }

    private void callGetRequests(){

        //TODO: Validar la funcionalidad del servicio
        AndroidNetworking.post(AssistantApiService.GET_REQUESTS)
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

    public class TaskRequests extends AsyncTask {

        private String user;
        private String password;

        public TaskRequests(String user, String password) {
            super();
            this.user = user;
            this.password = password;
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

            callGetRequests();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
        }
    }
}
