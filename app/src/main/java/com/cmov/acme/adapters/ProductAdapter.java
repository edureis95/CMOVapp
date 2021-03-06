package com.cmov.acme.adapters;


import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.api.model.request.CheckoutRequest;
import com.cmov.acme.api.model.request.RegisterRequest;
import com.cmov.acme.api.model.response.CheckoutResponse;
import com.cmov.acme.api.model.response.RegisterResponse;
import com.cmov.acme.api.service.Checkout_service;
import com.cmov.acme.api.service.Register_service;
import com.cmov.acme.models.Product;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;
import com.cmov.acme.ui.RegisterActivity;
import com.cmov.acme.ui.ShoppingCartActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.id.input;

public class ProductAdapter extends ArrayAdapter<Product>{

    private Context context;
    private Retrofit retrofit;

    private TextView list_product_name;
    private TextView list_product_model;
    private TextView list_product_price;
    private TextView product_quantity;


    private Button button_delete;
    private Button button_add;
    private Button button_substract;

    private List<Product> products;
    private int total_cost = 0;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.retrofit = RetrofitSingleton.getInstance();
        this.context = context;
        this.products = products;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext()); //inflate: fica pronto para renderizar
        View custom_view = inflater.inflate(R.layout.custom_row, parent, false);

        String product_name = getItem(position).getName();
        String product_model = getItem(position).getModel();
        int product_price = getItem(position).getPrice();


        list_product_model = custom_view.findViewById(R.id.list_product_model);
        product_quantity =  custom_view.findViewById(R.id.product_quantity);
        list_product_name =  custom_view.findViewById(R.id.list_product_name);
        list_product_price =  custom_view.findViewById(R.id.list_product_price);

        product_quantity.setText(Integer.toString(products.get(position).getQuantity()));


        list_product_name.setText(product_name);
        list_product_price.setText(Integer.toString(product_price));
        list_product_model.setText(product_model);


        button_delete = custom_view.findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteProduct(position);
            }});

        button_add = custom_view.findViewById(R.id.button_add);

        button_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Product product = products.get(position);
                product.addQuantity();
                product_quantity.setText(Integer.toString(product.getQuantity()));
                addCost(product);
                ((ShoppingCartActivity)context).setTotalCost(total_cost);
                notifyDataSetChanged();
            }});

        button_substract = custom_view.findViewById(R.id.button_subtract);
        button_substract.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Product product = products.get(position);

                product_quantity.setText(Integer.toString(product.getQuantity()));
                subtractCost(product);
                product.subtractQuantity();
                ((ShoppingCartActivity)context).setTotalCost(total_cost);
                notifyDataSetChanged();
            }});

        return custom_view;
    }

    public void addCost(Product product){
        total_cost += product.getPrice();
    }

    public void subtractCost(Product product){
        int price = product.getPrice();

        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getName().equals(product.getName())){
               if(products.get(i).getQuantity() > 1) {
                   total_cost -= price;
               }
            }
        }
        if(total_cost < 0)
            total_cost = 0;

    }

    public void deleteProduct(int pos){
        total_cost -= (products.get(pos).getPrice() * products.get(pos).getQuantity());

        if(total_cost < 0)
            total_cost = 0;

        products.remove(pos);
        ((ShoppingCartActivity)context).setTotalCost(total_cost);
        notifyDataSetChanged();
    }

    public void addProduct(Product product){
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getName().equals(product.getName())){
                products.get(i).addQuantity();
                ((ShoppingCartActivity)context).setTotalCost(total_cost);
                notifyDataSetChanged();
                return;
            }
        }
        product.addQuantity();
        products.add(product);

    }

    public void reset_products(){
        products.clear();
        total_cost = 0;
        ((ShoppingCartActivity)context).setTotalCost(total_cost);
        notifyDataSetChanged();

    }

    public void make_purchase() throws SignatureException {

        if(products.isEmpty()){
            Toast.makeText(context, "Shopping cart empty", Toast.LENGTH_LONG).show();
            return;
        }

        List<CheckoutRequest> purchases = new ArrayList<>();
        Checkout_service checkout_service = retrofit.create(Checkout_service.class);

        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            CheckoutRequest request = new CheckoutRequest(product.getId(), product.getQuantity());
            purchases.add(request);
        }

        KeyPair key = User.getInstance().getKp();

        String json = new Gson().toJson(purchases);

        Signature sg = null;          // for signing with the stated algorithm
        try {
            sg = Signature.getInstance("SHA1WithRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sg.initSign(key.getPrivate());                                             // supply the private key
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            sg.update(json.getBytes());                                             // define the data to sign
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        byte[] output = sg.sign();

        Call<CheckoutResponse> call = checkout_service.sendPurchase(new String(Base64.encode(output, Base64.DEFAULT)).trim(),"Bearer " + User.getInstance().getToken(), purchases);



        call.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Checkout successful", Toast.LENGTH_LONG).show();
                    reset_products();
                }
                else{
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
