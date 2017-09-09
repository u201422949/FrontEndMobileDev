package pe.edu.upc.homeassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignIn;
    private Button btnRegistrar;
    private TextView txtRecovery;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        initializeViews();
    }

    private void initializeViews(){
        btnRegistrar = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        txtRecovery = (TextView) findViewById(R.id.txtRecovery);

        btnRegistrar.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        txtRecovery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegistrar){
            startActivity(new Intent(context, RegisterActivity.class));
        }else if(view == btnSignIn){
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }else if(view == txtRecovery){
            startActivity(new Intent(context, RecoveryActivity.class));
        }
    }
}
