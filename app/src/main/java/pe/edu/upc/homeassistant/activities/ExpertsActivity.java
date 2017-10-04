package pe.edu.upc.homeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.adapters.ExpertsAdapter;
import pe.edu.upc.homeassistant.adapters.HistoryAdapter;
import pe.edu.upc.homeassistant.adapters.RecyclerViewClickListener;
import pe.edu.upc.homeassistant.model.Expert;
import pe.edu.upc.homeassistant.model.Request;
import pe.edu.upc.homeassistant.model.Skill;

import static java.security.AccessController.getContext;

/**
 * Created by Fernando on 27/09/2017.
 */

public class ExpertsActivity extends AppCompatActivity implements RecyclerViewClickListener{

    private RecyclerView recyclerRequest;
    private ExpertsAdapter expertsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    List<Expert> experts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts);
        context = ExpertsActivity.this;
        recyclerRequest = (RecyclerView) findViewById(R.id.recyclerRequest);

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

        expertsAdapter = new ExpertsAdapter(experts, this);
        layoutManager = new LinearLayoutManager(ExpertsActivity.this);
        recyclerRequest.setItemAnimator(new DefaultItemAnimator());
        recyclerRequest.setAdapter(expertsAdapter);
        recyclerRequest.setLayoutManager(layoutManager);
    }

    @Override
    public void recyclerViewListClicked(View view, int position) {
        Expert expert = experts.get(position);

        Intent intent = new Intent(context, DetailExpertActivity.class);
        intent.putExtras(expert.toBundle());
        startActivity(intent);
    }
}
