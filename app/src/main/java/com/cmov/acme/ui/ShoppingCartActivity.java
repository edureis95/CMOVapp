package com.cmov.acme.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmov.acme.R;
import com.cmov.acme.adapters.ProductAdapter;
import com.cmov.acme.models.Product;
import com.cmov.acme.models.ProductList;
import com.cmov.acme.singletons.User;
import com.google.zxing.integration.android.IntentIntegrator;

public class ShoppingCartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button scan_button;
    private Button checkout_button;
    private ProductAdapter adapter = null;
    private TextView total_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        scan_button = (Button) findViewById(R.id.scan_button);
        checkout_button = (Button) findViewById(R.id.checkout_button);
        total_cost = (TextView) findViewById(R.id.list_total_cost);
        total_cost.setText("0");

        final Activity activity = this;

        adapter = new ProductAdapter(this, android.R.layout.simple_list_item_1, ProductList.list_products);
        ListView productListView = (ListView) findViewById(R.id.product_list_view);
        productListView.setAdapter(adapter);

        checkout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {   //quando o utilizador clica para comprar, manda produto como resposta
                adapter.make_purchase();

            }
        });

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
               /* if (ContextCompat.checkSelfPermission(ShoppingCartActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ShoppingCartActivity.this,
                            Manifest.permission.CAMERA)) {
                    } else {
                        ActivityCompat.requestPermissions(ShoppingCartActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                1);
                    }
                } else {
                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.initiateScan();
                }*/
                opensProduct();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.initiateScan();
                } else {
                }
                return;
            }
        }
    }

    /*

     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Scanning cancelled", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ShoppingCartActivity.this, ProductActivity.class);
                intent.putExtra(getString(R.string.barCode), result.getContents());
                startActivity(intent);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    public void opensProduct() {
        Intent intent = new Intent(ShoppingCartActivity.this, ProductActivity.class);
        intent.putExtra("bar_code", "61234567890");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    public void setTotalCost(int cost) {
        total_cost.setText(Integer.toString(cost));
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.past_transactions) {
            Intent intent = new Intent(ShoppingCartActivity.this, ReceiptsActivity.class);
            startActivity(intent);
        } else if (id == R.id.account) {

        } else if (id == R.id.logout) {
            User user = User.getInstance();
            user.deleteInstance();

            Intent intent = new Intent(ShoppingCartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Product product = (Product) bundle.getSerializable("Product");
            adapter.addProduct(product);
            adapter.addCost(product);
            setTotalCost(adapter.getTotal_cost());
        }
    }
}
