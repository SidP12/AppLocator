package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StoreLocator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_locator);
        Button confirmed = findViewById(R.id.confirm);
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String locationStr = extras.getString("location");
                    confirmed(locationStr);
                } else {
                    confirmed("");
                }

            }
        });
 //       ImageView imageView = findViewById(R.id.imageView4);
//        Picasso.get().load("C:\\Users\\pilla\\AndroidStudioProjects\\MyApplication4\\app\\src\\main\\res\\drawable\\storemap.png").into(imageView);
    }
    private void confirmed(String location) {
        RadioButton kennesaw = findViewById(R.id.Kennesaw);
        RadioButton douglasville = findViewById(R.id.Douglasville);
        RadioButton conyers = findViewById(R.id.Conyers);
        RadioButton hiram = findViewById(R.id.Hiram);
        RadioButton snellville = findViewById(R.id.Snellville);
        RadioButton mcDonough = findViewById(R.id.McDonough);
        if (kennesaw.isChecked()) {
            location = "Kennesaw";
        } else if (douglasville.isChecked()) {
            location = "Douglasville";
        } else if (conyers.isChecked()) {
            location = "Conyers";
        } else if (hiram.isChecked()) {
            location = "Hiram";
        } else if (snellville.isChecked()) {
            location = "Snellville";
        } else if (mcDonough.isChecked()) {
            location = "McDonough";
        }
        Intent intent = new Intent(StoreLocator.this, MainActivity.class);
        intent.putExtra("location", location);
        startActivity(intent);
    }

}