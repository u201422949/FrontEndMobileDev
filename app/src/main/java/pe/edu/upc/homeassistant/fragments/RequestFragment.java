package pe.edu.upc.homeassistant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.activities.NewRequestActivity;
import pe.edu.upc.homeassistant.adapters.HistoryAdapter;
import pe.edu.upc.homeassistant.adapters.RequestAdapter;
import pe.edu.upc.homeassistant.model.Request;

import static pe.edu.upc.homeassistant.Constants.EXTRA_REQUEST;

public class RequestFragment extends Fragment implements View.OnClickListener{

    private static int CODE_NEW_REQUEST = 101;
    private RecyclerView recyclerRequest;
    private RequestAdapter historyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabNewRequest;

    List<Request> lsRequest = new ArrayList<>();

    public RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerRequest = (RecyclerView) view.findViewById(R.id.recyclerRequest);
        fabNewRequest = (FloatingActionButton) view.findViewById(R.id.fabNewRequest);

        historyAdapter = new RequestAdapter(lsRequest);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerRequest.setItemAnimator(new DefaultItemAnimator());
        recyclerRequest.setAdapter(historyAdapter);
        recyclerRequest.setLayoutManager(layoutManager);

        fabNewRequest.setOnClickListener(this);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_NEW_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                Request request =  (Request) data.getSerializableExtra(EXTRA_REQUEST);
                lsRequest.add(request);
                historyAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "ESTOY PINTANTO DESDE FRAGMENT", Toast.LENGTH_LONG).show();
            }
        }

    }
}
