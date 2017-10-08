package pe.edu.upc.homeassistant.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import pe.edu.upc.homeassistant.activities.LoginActivity;
import pe.edu.upc.homeassistant.activities.MainActivity;
import pe.edu.upc.homeassistant.activities.NewRequestActivity;
import pe.edu.upc.homeassistant.adapters.HistoryAdapter;
import pe.edu.upc.homeassistant.adapters.RecyclerViewClickListener;
import pe.edu.upc.homeassistant.adapters.RequestAdapter;
import pe.edu.upc.homeassistant.model.Budge;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.model.Expert;
import pe.edu.upc.homeassistant.model.Request;
import pe.edu.upc.homeassistant.network.AssistantApiService;

import static pe.edu.upc.homeassistant.Constants.EXTRA_REQUEST;

public class RequestFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        RecyclerViewClickListener {

    private static int CODE_NEW_REQUEST = 101;
    private RecyclerView recyclerRequest;
    private RequestAdapter historyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabNewRequest;
    private SwipeRefreshLayout swipeRequests;
    private Context context;
    public RequestQueue requestQueue;
    ProgressDialog progressDialog;

    List<Request> lsRequest = new ArrayList<>();

    public RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        progressDialog = new ProgressDialog(context);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerRequest = (RecyclerView) view.findViewById(R.id.recyclerRequest);
        fabNewRequest = (FloatingActionButton) view.findViewById(R.id.fabNewRequest);
        swipeRequests = (SwipeRefreshLayout) view.findViewById(R.id.swipeRequests);

        historyAdapter = new RequestAdapter(lsRequest, this);
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
        callGetRequest();
    }

    private void callGetRequest() {

        progressDialog.setMessage(getString(R.string.login_dialog_progress));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONObject client = new JSONObject();
        try {
            client.put("idusuario", Client.from(context).getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                AssistantApiService.GET_REQUESTS, client,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("solicitudes");
                            lsRequest = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++)
                                try {
                                    lsRequest.add(Request.from(jsonArray.getJSONObject(i)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            historyAdapter.setRequestList(lsRequest);
                            historyAdapter.notifyDataSetChanged();
                            swipeRequests.setRefreshing(false);
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

    @Override
    public void recyclerViewListClicked(View view, int position) {
        List<Budge> budges = lsRequest.get(position).getBudges();
        Toast.makeText(context, "Mejor Precio: "+budges.get(position).getPrice(), Toast.LENGTH_LONG).show();
    }
}
