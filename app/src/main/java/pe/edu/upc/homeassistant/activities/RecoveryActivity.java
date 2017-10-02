package pe.edu.upc.homeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pe.edu.upc.homeassistant.R;

public class RecoveryActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSend;
    private EditText edtMail;
    private TextInputLayout tilMail;
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
        edtMail = (EditText) findViewById(R.id.edtMail);
        tilMail = (TextInputLayout) findViewById(R.id.tilMail);

        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend){
            if (validateFields()){
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }
    }

    private boolean validateFields(){
        String mail = edtMail.getText().toString();

        tilMail.setErrorEnabled(false);

        if (TextUtils.isEmpty(mail)) {
            if (TextUtils.isEmpty(mail)) {
                tilMail.setError(getString(R.string.recovery_empty_mail));
            }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                tilMail.setError(getString(R.string.recovery_invalid_mail));
            }

            return false;
        } else {
            return true;
        }
    }
}
