package com.cmov.acme.adapters;


import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.models.Product;
import com.cmov.acme.ui.ShopActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product>{

    private Context context;

    private TextView list_product_name;
    private TextView list_product_model;
    private TextView list_product_price;
    private TextView product_quantity;


    private ImageButton button_delete;
    private ImageButton button_add;
    private ImageButton button_substract;

    private List<Product> products;
    private int total_cost=0;
    private int quantity=1;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
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


        list_product_model = (TextView) custom_view.findViewById(R.id.list_product_model);
        product_quantity = (TextView)  custom_view.findViewById(R.id.product_quantity);
        list_product_name = (TextView) custom_view.findViewById(R.id.list_product_name);
        list_product_price = (TextView) custom_view.findViewById(R.id.list_product_price);

        product_quantity.setText(Integer.toString(products.get(position).getQuantity()));


        list_product_name.setText(product_name);
        list_product_price.setText(Integer.toString(product_price));
        list_product_model.setText(product_model);


        button_delete = (ImageButton)custom_view.findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteProduct(position);
            }});

        button_add = (ImageButton)custom_view.findViewById(R.id.button_add);

        button_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Product product = products.get(position);
                product.addQuantity();
                product_quantity.setText(Integer.toString(product.getQuantity()));
                addCost();
                ((ShopActivity)context).setTotalCost(total_cost);
                notifyDataSetChanged();
            }});

        button_substract = (ImageButton)custom_view.findViewById(R.id.button_subtract);
        button_substract.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Product product = products.get(position);
                product.subtractQuantity();
                product_quantity.setText(Integer.toString(product.getQuantity()));
                subtractCost();
                ((ShopActivity)context).setTotalCost(total_cost);
                notifyDataSetChanged();
            }});

        return custom_view;
    }

    public void addCost(){
        for(int i = 0; i < products.size(); i++){;
            total_cost += products.get(i).getPrice();
        }
    }

    public void subtractCost(){
        for(int i = 0; i < products.size(); i++){
            total_cost -= products.get(i).getPrice();
        }
    }

    public void deleteProduct(int pos){
        subtractCost();
        products.remove(pos);

        notifyDataSetChanged();
    }

    public void addQuantity(){

    }

    public void subtractQuantity(){

    }
}
