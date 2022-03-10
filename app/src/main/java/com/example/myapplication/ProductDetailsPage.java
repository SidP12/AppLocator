package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailsPage extends AppCompatActivity {

    String prodLocationStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_page);
        Bundle extras = getIntent().getExtras();
        String imageStr = "";
        String priceStr = "";
        String prodNameStr = "";
        String storeLocationStr = "";
        String availabilityStoreStr = "";
        prodLocationStr = "";
        if (extras != null) {
             imageStr = extras.getString("image");
             priceStr = extras.getString("price");
             prodNameStr = extras.getString("prodName");
             storeLocationStr = extras.getString("storeLocation");
             availabilityStoreStr = extras.getString("availability");
             prodLocationStr = extras.getString("prodLocation");

        }
        TextView price = findViewById(R.id.priceProd);
        TextView prodName = findViewById(R.id.prodNameDetails);
        ImageView image = findViewById(R.id.imageProd);
        TextView storeLocation = findViewById(R.id.storeLocation);
        TextView prodLocation = findViewById(R.id.prodLocation);
        TextView stock = findViewById(R.id.prodStock);

        prodLocation.setText(prodLocationStr);
        if (availabilityStoreStr.equals("0")) {
            stock.setText("Out of stock");
            stock.setTextColor(Color.RED);
            storeLocation.setText("     at " + storeLocationStr);
        } else {
            stock.setText(availabilityStoreStr + " in stock");
            stock.setTextColor(Color.parseColor("#095C15"));
            storeLocation.setText("at " + storeLocationStr);
        }
        price.setText("$" + priceStr);
        prodName.setText(prodNameStr);
        Picasso.get().load(imageStr).into(image);
        ImageButton prodFinder = findViewById(R.id.prodFinder);
        prodFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsPage.this, Navigator.class);
                intent.putExtra("prodLocation", prodLocationStr);
                startActivity(intent);
            }
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}