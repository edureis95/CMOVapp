package com.cmov.acme.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmov.acme.R;
import com.cmov.acme.adapters.ProductAdapter;
import com.cmov.acme.models.Product;
import com.cmov.acme.models.ProductList;
import com.google.zxing.integration.android.IntentIntegrator;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private Button scan_button;
    private List<Product> products;
    private final static String TAG = "TESTE";
    private ProductAdapter adapter = null;
    private ListView productListView;
    private TextView total_cost;

    public void removeFromProductsList(String name){
        for(int i = 0; i < products.size(); i++){
            if(name.equals(products.get(i).getName())){
                products.remove(i);
            }
        }
    }

    public void opensProduct(){
        Intent intent = new Intent(ShopActivity.this, ProductActivity.class);
        intent.putExtra("bar_code", "61234567890");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Product product = (Product)bundle.getSerializable("Product");
            adapter.add(product);
            adapter.addCost();
            setTotalCost(adapter.getTotal_cost());
        }
    }

    public void setTotalCost(int cost){
        total_cost.setText(Integer.toString(cost));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        scan_button = (Button)findViewById(R.id.scan_button);
        total_cost = (TextView)findViewById(R.id.list_total_cost);
        total_cost.setText("0");
        final Activity activity = this;

        adapter = new ProductAdapter(this, android.R.layout.simple_list_item_1, ProductList.list_products);
        ListView productListView = (ListView) findViewById(R.id.product_list_view);
        productListView.setAdapter(adapter);


                scan_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
               /* if (ContextCompat.checkSelfPermission(ShopActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ShopActivity.this,
                            Manifest.permission.CAMERA)) {
                    } else {
                        ActivityCompat.requestPermissions(ShopActivity.this,
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
                Intent intent = new Intent(ShopActivity.this, ProductActivity.class);
                intent.putExtra(getString(R.string.barCode), result.getContents());
                startActivity(intent);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/
}
