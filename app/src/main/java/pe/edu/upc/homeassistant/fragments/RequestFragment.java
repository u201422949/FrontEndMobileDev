package pe.edu.upc.homeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.adapters.RequestAdapter;
import pe.edu.upc.homeassistant.models.Request;
import pe.edu.upc.homeassistant.models.User;

public class RequestFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabNewRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Request> lsRequest = new ArrayList<>();
        lsRequest.add(new Request(new User(), "Descripcion 1", "Titulo 1"));
        lsRequest.add(new Request(new User(), "Descripcion 2", "Titulo 2"));
        // initialize views
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerRequest);
        fabNewRequest = (FloatingActionButton) view.findViewById(R.id.fabNewRequest);

        requestAdapter = new RequestAdapter(lsRequest);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);
        recyclerView.setLayoutManager(layoutManager);

        fabNewRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fabNewRequest)){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, new NewRequestFragment(), "NewFragmentTag");
            ft.commit();
            ft.addToBackStack(null);
        }
    }
}
