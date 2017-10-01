package pe.edu.upc.homeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.adapters.ExpertsAdapter;
import pe.edu.upc.homeassistant.adapters.HistoryAdapter;
import pe.edu.upc.homeassistant.model.Expert;
import pe.edu.upc.homeassistant.model.Request;

/**
 * Created by Fernando on 27/09/2017.
 */

public class ExpertsActivity extends AppCompatActivity {

    private RecyclerView recyclerRequest;
    private ExpertsAdapter expertsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts);

        recyclerRequest = (RecyclerView) findViewById(R.id.recyclerRequest);

        //TODO: Set original list of requests
        List<Expert> experts = new ArrayList<>();

        experts.add(new Expert("Augusto Alva", "aalvacampos@dsd.com"));
        experts.add(new Expert("Julio Patrick", "jpat@dsd.com"));

        expertsAdapter = new ExpertsAdapter(experts);
        layoutManager = new LinearLayoutManager(ExpertsActivity.this);
        recyclerRequest.setItemAnimator(new DefaultItemAnimator());
        recyclerRequest.setAdapter(expertsAdapter);
        recyclerRequest.setLayoutManager(layoutManager);
    }
}
