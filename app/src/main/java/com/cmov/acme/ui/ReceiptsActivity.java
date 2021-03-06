package com.cmov.acme.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.adapters.ReceiptAdapter;
import com.cmov.acme.api.model.response.ReceiptResponse;
import com.cmov.acme.api.service.Receipts_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReceiptsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Retrofit retrofit;
    private ArrayList<ReceiptResponse> listaResposta;
    private ListView listView;
    private ProgressBar progressBar;
    private View receipts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipts_activity_drawer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        receipts = findViewById(R.id.transactionsview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        listaResposta = new ArrayList<ReceiptResponse>();
        listView = (ListView) findViewById(R.id.listview);
        retrofit = RetrofitSingleton.getInstance();
        Receipts_service receipts_service = retrofit.create(Receipts_service.class);
        Call<ArrayList<ReceiptResponse>> call = receipts_service.getReceipts("Bearer "+ User.getInstance().getToken());

        showProgress(true);
        call.enqueue(new Callback<ArrayList<ReceiptResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ReceiptResponse>> call, Response<ArrayList<ReceiptResponse>> response) {
                if(response.isSuccessful() ) {
                    listaResposta = response.body();
                    ReceiptAdapter adapter = new ReceiptAdapter(ReceiptsActivity.this, listaResposta);
                    listView.setAdapter(adapter);
                    showProgress(false);
                } else {
                    Toast.makeText(ReceiptsActivity.this,response.message(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ReceiptResponse>>  call, Throwable t) {
                Toast.makeText(ReceiptsActivity.this,"Unable to connect to server", Toast.LENGTH_LONG).show();
                showProgress(false);
                finish();

            }
        });

    }

    private void showProgress(final boolean show) {
        receipts.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shopping_cart) {
            Intent intent = new Intent(ReceiptsActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.past_transactions) {

        } else if (id == R.id.account) {
            Intent intent = new Intent(ReceiptsActivity.this, AccountActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.logout) {

            Intent intent = new Intent(ReceiptsActivity.this, LoginActivity.class);
            if(User.getInstance().getAdapter() != null)
                User.getInstance().getAdapter().reset_products();
            User user = User.getInstance();
            user.deleteInstance();
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
