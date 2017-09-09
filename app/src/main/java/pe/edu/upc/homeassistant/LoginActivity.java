package pe.edu.upc.homeassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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
                //goToActivity(RecoveryActivity.class);
                ShowAlertDialogWithListview();
                break;
        }
    }

    protected void goToActivity(Class activity) {
        Intent intent = new Intent(this,activity);
        intent.putExtra("saludo", edtUsuario.getText().toString());
        startActivity(intent);
    }

    public void ShowAlertDialogWithListview() {
        List<Competencia> mCom = new ArrayList<>();

        mCom.add(new Competencia("Albañil"));
        mCom.add(new Competencia("Gasfitero"));
        mCom.add(new Competencia("Carpintero"));
        mCom.add(new Competencia("Sastre"));
        mCom.add(new Competencia("Cerrajero"));
        mCom.add(new Competencia("Pintor"));
        mCom.add(new Competencia("Mecanico"));
        mCom.add(new Competencia("Electricista"));

        final  AlertDialog alertDialogObject;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_list_competencias, null);

        CompetenciaListAdapter adapter = new CompetenciaListAdapter(getBaseContext(), mCom);

        ListView listView = (ListView) view.findViewById(R.id.lvCompetencias);
        listView.setAdapter(adapter);

        Button bb = (Button)  view.findViewById(R.id.button3);

        dialogBuilder.setView(view);

        //Create alert dialog object via builder
        alertDialogObject = dialogBuilder.create();

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogObject.dismiss();
            }
        });
        //Show the dialog
        alertDialogObject.show();

    }
}
