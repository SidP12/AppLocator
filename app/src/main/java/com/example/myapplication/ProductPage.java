package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductPage extends AppCompatActivity {

    private ImageButton button;
    private AdapterClass.RecyclerViewClickListener listener;
    ListView lv;
    RecyclerView recyclerView;
    DatabaseReference ref;
    String locationStr;
    String value;
    ArrayList<Product> inStockList;
    ArrayList<Product> allList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Bundle extras = getIntent().getExtras();
        value = "";
        if (extras != null) {
            locationStr = extras.getString("location");
            TextView textView = findViewById(R.id.location);
            textView.setText(locationStr);
            value = extras.getString("input");
            value = value.trim();
        }
        button = findViewById(R.id.imageButton11);
        recyclerView = findViewById(R.id.rv);
        DatabaseActions actions = new DatabaseActions();
        ArrayList<String> items = actions.getItems();
        AutoCompleteTextView editText = findViewById(R.id.editTextTextPersonName2);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        editText.bringToFront();
        editText.setAdapter(itemsAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.editTextTextPersonName2);
                if (!textView.getText().toString().trim().isEmpty()) {
                    openProductPage();
                }
            }
        });
        ref = FirebaseDatabase.getInstance().getReference().child("Product");

        ImageButton locationButton = findViewById(R.id.openLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLocationsPage();
            }
        });
        Switch sw = (Switch) findViewById(R.id.hideStock);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            TextView numItems = findViewById(R.id.numItems);
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    numItems.setText(inStockList.size() + " ITEMS");
                    setOnClickListener(inStockList);
                    AdapterClass adapterClass = new AdapterClass(inStockList, listener);
                    recyclerView.setAdapter(adapterClass);
                } else {
                    numItems.setText(allList.size() + " ITEMS");
                    setOnClickListener(allList);
                    AdapterClass adapterClass = new AdapterClass(allList, listener);
                    recyclerView.setAdapter(adapterClass);
                }
            }
        });
    }

    public void openLocationsPage() {
        Intent intent = new Intent(ProductPage.this, StoreLocator.class);
        TextView location = findViewById(R.id.location);
        String loc = location.getText().toString();
        intent.putExtra("location", loc);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Product> list = new ArrayList<>();
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(Product.class));
                        }
                    }
                    search(value, list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("DB Error");
                }
            });
        }
    }

    private void search(String value, ArrayList<Product> list) {
        ArrayList<Product> searchedList = new ArrayList<>();
        inStockList = new ArrayList<>();

        for (Product prod : list) {
            if (!value.isEmpty() && (prod.getProductName().toLowerCase().contains(value.toLowerCase()) || prod.getTags().toLowerCase().contains(value.toLowerCase()) || value.toLowerCase().contains(prod.getTags().toLowerCase()))) {
                searchedList.add(prod);
                if (!prod.getAvailability().equals("0")) {
                    inStockList.add(prod);
                }
            }
        }
        TextView textView = findViewById(R.id.noItems);
        TextView numItems = findViewById(R.id.numItems);
        View b = findViewById(R.id.hideStock);
        if (searchedList.isEmpty()) {
            b.setVisibility(View.GONE);
            numItems.setText("");
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textView.setText("No products found for the search!");
        } else {
            b.setVisibility(View.VISIBLE);
            allList = searchedList;
            numItems.setText(searchedList.size() + " ITEMS");
            textView.setText("");
            setOnClickListener(searchedList);
            AdapterClass adapterClass = new AdapterClass(searchedList, listener);
            recyclerView.setAdapter(adapterClass);
            Switch sw = (Switch) findViewById(R.id.hideStock);
            if (sw.isChecked()) {
                numItems.setText(inStockList.size() + " ITEMS");
                setOnClickListener(inStockList);
                AdapterClass adapterClass2 = new AdapterClass(inStockList, listener);
                recyclerView.setAdapter(adapterClass);
            }
        }
    }

    private void setOnClickListener(ArrayList<Product> searchedList) {
        listener = new AdapterClass.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ProductPage.this, ProductDetailsPage.class);
                Product prod = searchedList.get(position);
                intent.putExtra("image", prod.getPictureOfProduct());
                intent.putExtra("price", prod.getPrice());
                intent.putExtra("prodName", prod.getProductName());
                intent.putExtra("prodLocation", prod.getLocation());
                intent.putExtra("availability", prod.getAvailability());
                intent.putExtra("storeLocation", locationStr);
                startActivity(intent);
            }
        };
    }

    public void openProductPage() {
        Intent intent = new Intent(this, ProductPage.class);
        TextView text = (TextView)findViewById(R.id.editTextTextPersonName2);
        String in = text.getText().toString();
        intent.putExtra("location", locationStr);
        intent.putExtra("input",in);
        startActivity(intent);
    }
}