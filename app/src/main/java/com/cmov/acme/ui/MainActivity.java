package com.cmov.acme.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmov.acme.R;
import com.cmov.acme.singletons.User;
import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends AppCompatActivity {

    private Button login_button;
    private Button shop_button;
    private Button register_button;
    private Button logout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = (Button)findViewById(R.id.login_button);
        final Activity activity = this;
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
              //  finish();
            }
        });

        shop_button = (Button)findViewById(R.id.shop_button);
        shop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
               // finish();
            }
        });


        register_button = (Button)findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                // finish();
            }
        });

        logout_button = (Button)findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               User user = User.getInstance();
                user.deleteInstance();
                login_button.setVisibility(View.VISIBLE);
                register_button.setVisibility(View.VISIBLE);
                logout_button.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        User user =  User.getInstance();
        if(user.getToken() != null){
            login_button.setVisibility(View.INVISIBLE);
            register_button.setVisibility(View.INVISIBLE);
            logout_button.setVisibility(View.VISIBLE);

        }
    }
}
