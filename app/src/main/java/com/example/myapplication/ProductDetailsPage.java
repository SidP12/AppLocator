package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_page);
        Bundle extras = getIntent().getExtras();
        String imageStr = "";
        String priceStr = "";
        String prodNameStr = "";
        if (extras != null) {
             imageStr = extras.getString("image");
             priceStr = extras.getString("price");
             prodNameStr = extras.getString("prodName");
        }
        TextView price = findViewById(R.id.priceProd);
        TextView prodName = findViewById(R.id.prodNameDetails);
        ImageView image = findViewById(R.id.imageProd);
        price.setText("$" + priceStr);
        prodName.setText(prodNameStr);
        Picasso.get().load(imageStr).into(image);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}