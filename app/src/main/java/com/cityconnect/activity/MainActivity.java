package com.cityconnect.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cityconnect.R;
import com.cityconnect.adapter.ServicesAdapter;
import com.cityconnect.api.APIHelper;
import com.cityconnect.model.AddService;
import com.cityconnect.utils.CommonUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    APIHelper apiHelper;
    ServicesAdapter servicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.services);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        apiHelper = APIHelper.init();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, AddServices.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Dialog dialog = CommonUtils.getCircularProgressDialog(MainActivity.this,"Loading",false);
        dialog.show();
        apiHelper.getAllServices(new APIHelper.OnRequestComplete() {
            @Override
            public boolean onSuccess(Object object) {

                ArrayList<AddService> addServices = (ArrayList<AddService>) object;
                dialog.dismiss();
                if (addServices.size() == 0) {
                    Toast.makeText(MainActivity.this, "Services not available", Toast.LENGTH_SHORT).show();
                } else {
                    servicesAdapter = new ServicesAdapter(MainActivity.this, addServices);
                    recyclerView.setAdapter(servicesAdapter);

                }


                return false;
            }

            @Override
            public void onFailure(String errorMessage) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
