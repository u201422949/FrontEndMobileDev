package pe.edu.upc.homeassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIngresar;
    private Button btnRegistrar;
    private Button btnRecuperarClave;
    private EditText edtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initParams();
        setParams();
    }

    protected void initParams() {
        btnIngresar = (Button)findViewById(R.id.btn_ingresar);
        btnRegistrar = (Button)findViewById(R.id.btn_registrar);
        btnRecuperarClave = (Button)findViewById(R.id.btn_olvideclave);
        edtUsuario = (EditText) findViewById(R.id.edt_usuario);
    }

    protected void setParams() {
        btnIngresar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnRecuperarClave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_ingresar:
                goToActivity(MainNavActivity.class);
                break;
            case R.id.btn_registrar:
                goToActivity(RegisterActivity.class);
                break;
            case R.id.btn_olvideclave:
                goToActivity(RecoveryActivity.class);
                break;
        }
    }

    protected void goToActivity(Class activity) {
        Intent intent = new Intent(this,activity);
        intent.putExtra("saludo", edtUsuario.getText().toString());
        startActivity(intent);
    }
}
