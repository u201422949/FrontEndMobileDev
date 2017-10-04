package pe.edu.upc.homeassistant.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.homeassistant.Constants;
import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Client;
import pe.edu.upc.homeassistant.network.AssistantApiService;
import pe.edu.upc.homeassistant.util.ConvertObject;
import pe.edu.upc.homeassistant.views.SupportMapInsideScrollFragment;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private static int CODE_OPEN_CAMERA = 100;

    private GoogleMap map;
    private Button btnSave;
    private Button btnPhoto;
    private ImageView imgProfile;
    private EditText edtName;
    private EditText edtMail;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtPassword;
    private EditText edtRepeatPassword;
    private EditText edtDocument;
    private TextInputLayout tilDocument;
    private TextInputLayout tilName;
    private TextInputLayout tilMail;
    private TextInputLayout tilPhone;
    private TextInputLayout tilAddress;
    private TextInputLayout tilPassword;
    private TextInputLayout tilRepeatPassword;
    private ScrollView scrollRegister;
    private LatLng userLocation;
    private Bitmap profilePhoto;
    private Context context;
    private SupportMapInsideScrollFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;

        initializeViews();
    }

    private void initializeViews() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        edtName = (EditText) findViewById(R.id.edtName);
        edtDocument = (EditText) findViewById(R.id.edtDocument);
        edtMail = (EditText) findViewById(R.id.edtMail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRepeatPassword = (EditText) findViewById(R.id.edtRepeatPassword);
        tilName = (TextInputLayout) findViewById(R.id.tilName);
        tilDocument = (TextInputLayout) findViewById(R.id.tilDocument);
        tilMail = (TextInputLayout) findViewById(R.id.tilMail);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);
        tilAddress = (TextInputLayout) findViewById(R.id.tilAddress);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        tilRepeatPassword = (TextInputLayout) findViewById(R.id.tilRepeatPassword);
        scrollRegister = (ScrollView) findViewById(R.id.scrollRegister);
        mapFragment = (SupportMapInsideScrollFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        btnSave.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSave) {
            Client client = validateFields();
            if (client != null) {
                //TODO: Llamar al servicio de registro de usuario y remover guardado en saveDataUser()
                //callLoginService(client);
                saveDataUser(client);
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        }else if(view == btnPhoto){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CODE_OPEN_CAMERA);
        }
    }

    private void callLoginService(Client client){
        AndroidNetworking.post(AssistantApiService.REGISTER_CLIENT_URL)
                .addBodyParameter(client)
                .setPriority(Priority.MEDIUM)
                .setTag(getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if("error".equalsIgnoreCase(response.getString("status"))){
                                Log.e(getString(R.string.app_name), response.getString("message"));
                                return;
                            }

                            Client client = Client.from(response.getJSONObject("client"));
                            saveDataUser(client);

                            startActivity(new Intent(context, MainActivity.class));
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(getString(R.string.app_name), anError.getLocalizedMessage());
                    }
                });
    }

    private Client validateFields() {
        Client client = null;

        String name = edtName.getText().toString();
        String mail = edtMail.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        String password = edtPassword.getText().toString();
        String rePassword = edtRepeatPassword.getText().toString();
        String documentNumber = edtDocument.getText().toString();

        tilName.setErrorEnabled(false);
        tilMail.setErrorEnabled(false);
        tilPhone.setErrorEnabled(false);
        tilAddress.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        tilRepeatPassword.setErrorEnabled(false);
        tilDocument.setErrorEnabled(false);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(address) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(rePassword)) {

            if (TextUtils.isEmpty(name)) {
                tilName.setError(getString(R.string.register_empty_username));
            }
            if (TextUtils.isEmpty(mail)) {
                tilMail.setError(getString(R.string.register_empty_mail));
            }
            if (TextUtils.isEmpty(phone)) {
                tilPhone.setError(getString(R.string.register_empty_phone));
            }
            if (TextUtils.isEmpty(address)) {
                tilAddress.setError(getString(R.string.register_empty_address));
            }
            if (TextUtils.isEmpty(password)) {
                tilPassword.setError(getString(R.string.register_empty_password));
            }
            if (TextUtils.isEmpty(rePassword)) {
                tilRepeatPassword.setError(getString(R.string.register_empty_re_password));
            }
            if (TextUtils.isEmpty(documentNumber)){
                tilDocument.setError(getString(R.string.register_empty_document));
            }
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            tilMail.setError(getString(R.string.register_invalid_mail));
        } else if (!password.equals(rePassword)) {
            tilRepeatPassword.setError(getString(R.string.register_invalid_re_password));
        } else {
            client = new Client().setName(name)
                    .setMail(mail)
                    .setAddress(address)
                    .setPhone(phone)
                    .setLatitude(userLocation.latitude)
                    .setLongitude(userLocation.longitude)
                    .setPassword(password)
                    .setDocumentNumber(Integer.parseInt(documentNumber))
                    .setPhoto(ConvertObject.bitmapToByteArray(profilePhoto));
        }

        return client;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        int zoomCamera;
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }else{
            if(!map.isMyLocationEnabled())
                map.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (currentLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = locationManager.getBestProvider(criteria, true);
                currentLocation = locationManager.getLastKnownLocation(provider);
            }

            if(currentLocation != null) {
                userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                zoomCamera = 18;
            } else {
                userLocation = new LatLng(0, 0);
                zoomCamera = 14;
            }

            map.addMarker(new MarkerOptions().position(userLocation));
            map.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomCamera), 1500, null);
        }

        mapFragment.setListener(new SupportMapInsideScrollFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollRegister.requestDisallowInterceptTouchEvent(true);
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                userLocation = point;
                map.clear();
                map.addMarker(new MarkerOptions().position(point));
                map.moveCamera(CameraUpdateFactory.newLatLng(point));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 14), 1500, null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_OPEN_CAMERA && resultCode == Activity.RESULT_OK) {
            profilePhoto = (Bitmap) data.getExtras().get("data");
            imgProfile.setVisibility(View.VISIBLE);
            imgProfile.setImageBitmap(profilePhoto);
        }
    }

    private void saveDataUser(Client client){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(client);
        editor.putString(Constants.SP_DATA_CLIENT, json);
        editor.commit();
    }
}
