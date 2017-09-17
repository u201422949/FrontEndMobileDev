package pe.edu.upc.homeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pe.edu.upc.homeassistant.R;

public class RecoveryActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSend;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        context = RecoveryActivity.this;

        initializeViews();
    }

    private void initializeViews(){
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend){
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }
}
