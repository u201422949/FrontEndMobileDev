package pe.edu.upc.homeassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.model.Request;
import pe.edu.upc.homeassistant.model.Skill;
import pe.edu.upc.homeassistant.network.AssistantApiService;

import static pe.edu.upc.homeassistant.Constants.EXTRA_REQUEST;

public class NewRequestActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Spinner spnRequestType;
    private EditText edtSubject;
    private TextInputLayout tilSubject;
    private EditText edtDescription;
    private TextInputLayout tilDescription;
    private Button btnSend;
    private SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spnRequestType = (Spinner) findViewById(R.id.spnRequestType);
        edtSubject = (EditText) findViewById(R.id.edtSubject);
        tilSubject = (TextInputLayout) findViewById(R.id.tilSubject);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        tilDescription = (TextInputLayout) findViewById(R.id.tilDescription);
        btnSend = (Button) findViewById(R.id.btnSend);

        setSupportActionBar(toolbar);
        btnSend.setOnClickListener(this);

        adaptSpinner();

    }

    private void adaptSpinner(){

        String[] requestTypes = getResources().getStringArray(R.array.array_request_type);
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, requestTypes);
        spnRequestType.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend){
            Skill skill = new Skill(spnRequestType.getSelectedItemPosition(), (String) spnRequestType.getSelectedItem());
            Request request = new Request(skill, edtDescription.getText().toString(), edtSubject.getText().toString());
            /*Intent returnIntent = new Intent();
            returnIntent.putExtra(EXTRA_REQUEST, request);
            setResult(RESULT_OK, returnIntent);
            finish();*/
            Intent intent = new Intent(NewRequestActivity.this, ExpertsActivity.class);
            intent.putExtra("",request);
            startActivityForResult(intent, 2);
        }
    }
}
