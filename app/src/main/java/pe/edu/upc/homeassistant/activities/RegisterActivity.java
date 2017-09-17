package pe.edu.upc.homeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pe.edu.upc.homeassistant.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private GoogleMap map;
    private Button btnSave;
    private Context context;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;

        initializeViews();
    }

    private void initializeViews(){
        btnSave = (Button) findViewById(R.id.btnSave);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSave){
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-12.1131669, -77.0331475);
        map.addMarker(new MarkerOptions().position(sydney).title("Clinica Delgado"));

        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
