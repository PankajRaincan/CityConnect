package com.cityconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cityconnect.GPSTracker;
import com.cityconnect.R;
import com.cityconnect.api.APIHelper;
import com.cityconnect.model.AddService;
import com.cityconnect.utils.CommonUtils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;

public class AddServices extends AppCompatActivity {

    EditText mName, mMobile, mEmail, Dob, Address1, Address2, mDesc, latitude, longitude;
    AppCompatButton mSaveService;
    APIHelper apiHelper;
    ImageView imageView;
    private File logoFile;
    private LocationManager locationManager;
    private String provider;
    GPSTracker gps;
    Button changeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        init();


        logoFile = new File(Environment.getExternalStorageDirectory(), "service.jpg");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);

            }
        });

        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(AddServices.this);
                    startActivityForResult(intent, 11);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        mSaveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddService addService = new AddService();

                if (mName.getText().toString().trim().isEmpty()) {

                    Toast.makeText(AddServices.this, "Name can't be empty", Toast.LENGTH_SHORT).show();

                } else if (mMobile.getText().toString().trim().isEmpty()) {

                    Toast.makeText(AddServices.this, "Mobile can't be empty", Toast.LENGTH_SHORT).show();

                } else if (mEmail.getText().toString().trim().isEmpty() || !CommonUtils.validateEmail(mEmail.getText().toString().trim())) {

                    Toast.makeText(AddServices.this, "Invalid Email", Toast.LENGTH_SHORT).show();

                } else if (Dob.getText().toString().trim().isEmpty()) {

                    Toast.makeText(AddServices.this, "Date of Birth can't be empty", Toast.LENGTH_SHORT).show();

                } else if (mDesc.getText().toString().trim().isEmpty()) {

                    Toast.makeText(AddServices.this, "Description can't be empty", Toast.LENGTH_SHORT).show();

                } else {

                    final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "service.jpg");
                    Log.v("File---", "" + file.exists() + "---" + file.getAbsolutePath());
                    addService.setName(mName.getText().toString().trim());
                    addService.setEmail(mEmail.getText().toString().trim());
                    addService.setMobile(mMobile.getText().toString().trim());
                    addService.setAddress1(Address1.getText().toString().trim());
                    addService.setAddress2(Address2.getText().toString().trim());
                    addService.setDesc(mDesc.getText().toString().trim());
                    addService.setDob(Dob.getText().toString().trim());

                    if (!TextUtils.isEmpty(latitude.getText().toString().trim()) && !TextUtils.isEmpty(longitude.getText().toString().trim())) {

                        addService.setLatitude(latitude.getText().toString().trim());
                        addService.setLongitude(longitude.getText().toString().trim());

                    } else {

                        addService.setLatitude("");
                        addService.setLongitude("");
                    }
                    apiHelper.addService(file, addService, new APIHelper.OnRequestComplete() {
                        @Override
                        public boolean onSuccess(Object object) {

                            finish();
                            Toast.makeText(AddServices.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public void onFailure(String errorMessage) {

                            Toast.makeText(AddServices.this, errorMessage, Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            CommonUtils.saveBitmapToFile(photo, logoFile);

        }

        if (requestCode == 11 && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }
            Log.v("PickerLocation--", "" + place.getLatLng());

            latitude.setText(""+place.getLatLng().latitude);
            longitude.setText(""+place.getLatLng().longitude);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        gps = new GPSTracker(AddServices.this);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (enabled && gps.canGetLocation()) {

            latitude.setText("" + gps.getLatitude());
            longitude.setText("" + gps.getLongitude());
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        gps.stopUsingGPS();
    }

    public void init() {

        mName = (EditText) findViewById(R.id.name);
        mMobile = (EditText) findViewById(R.id.mobile);
        mEmail = (EditText) findViewById(R.id.email);
        Dob = (EditText) findViewById(R.id.dob);
        Address1 = (EditText) findViewById(R.id.address1);
        Address2 = (EditText) findViewById(R.id.address2);
        mDesc = (EditText) findViewById(R.id.desc);
        mSaveService = (AppCompatButton) findViewById(R.id.add_service);
        imageView = (ImageView) findViewById(R.id.profile_img);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        changeLocation = (Button) findViewById(R.id.change_location);


        apiHelper = APIHelper.init();
    }

}
