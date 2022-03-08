package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ProductPage extends AppCompatActivity {

    private ImageButton button;
  //  FirebaseListAdapter adapter;
    private AdapterClass.RecyclerViewClickListener listener;
    ListView lv;
    RecyclerView recyclerView;
    DatabaseReference ref;
    String value;
  //  boolean inProdName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        button = findViewById(R.id.imageButton11);
        recyclerView = findViewById(R.id.rv);
  //      lv = findViewById(R.id.lvProgram);
        DatabaseActions actions = new DatabaseActions();
        ArrayList<String> items = actions.getItems();
        AutoCompleteTextView editText = findViewById(R.id.editTextTextPersonName2);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        editText.bringToFront();
        editText.setAdapter(itemsAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProductPage();
            }
        });
        Bundle extras = getIntent().getExtras();
        value = "";
        if (extras != null) {
            value = extras.getString("input");
        }
  //      value = value.toLowerCase();
   //     value = value.substring(0, 1).toUpperCase() + value.substring(1);
        value = value.trim();
        ref = FirebaseDatabase.getInstance().getReference().child("Product");
//        Query query;
//        if (!value.isEmpty()) {
//            query = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("productName").equalTo(value);
//        } else {
//            query = FirebaseDatabase.getInstance().getReference().child("Product");
//        }
//        inProdName = false;
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    inProdName = true;
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        value = value.toLowerCase();
//        if (inProdName == false) {
//             query = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("tags").startAt(value)
//                     .endAt(value+"\uf8ff");
//        }
//        ArrayList<String> listItems = new ArrayList<String>();
//        HashMap<Integer, Product> map = new HashMap<>();
//        FirebaseListOptions<Product> options = new FirebaseListOptions.Builder<Product>()
//                .setLayout(R.layout.single_product)
//                .setQuery(query, Product.class)
//                .build();
//        adapter = new FirebaseListAdapter(options) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
//                    //             Toast.makeText(ProductPage.this, "here", Toast.LENGTH_SHORT).show();
//                    Product prod = (Product) model;
//                    value = value.trim();
// //                   if (value.isEmpty() || prod.getProductName().toLowerCase().contains(value.toLowerCase()) || prod.getTags().toLowerCase().contains(value.toLowerCase())) {
//          //              System.out.println(prod.getProductName() + " " + value + " " + prod.getTags());
//                        TextView prodName = v.findViewById(R.id.prodName);
//                        TextView prodPrice = v.findViewById(R.id.prodPrice);
//                        TextView prodStock = v.findViewById(R.id.stock);
//                        TextView prodRating = v.findViewById(R.id.rating);
//                        ImageView image = v.findViewById(R.id.prodPic);
//                        prodName.setText(prod.getProductName());
//                        prodPrice.setText("$" + prod.getPrice());
//                        prodRating.setText(prod.getReview());
//                        Picasso.get().load(prod.getPictureOfProduct()).into(image);
//                        System.out.println(prod.getAvailability());
//                        if (prod.getAvailability().equals("0")) {
//                            prodStock.setText("Out of stock");
//                            prodStock.setTextColor(Color.RED);
//                        } else {
//                            prodStock.setText(prod.getAvailability() + " in stock");
//                            prodStock.setTextColor(Color.parseColor("#095C15"));
//                        }
//
//                        map.put(position, prod);
//                    }
//  //              }
//        };
//        lv.setAdapter(adapter);
//        lv.setClickable(true);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(ProductPage.this, ProductDetailsPage.class);
//                Product prod = map.get(i);
//                intent.putExtra("image", prod.getPictureOfProduct());
//                intent.putExtra("price", prod.getPrice());
//                intent.putExtra("prodName", prod.getProductName());
//                startActivity(intent);
//            }
//        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

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
        for (Product prod : list) {
            if (value.isEmpty() || prod.getProductName().toLowerCase().contains(value.toLowerCase()) || prod.getTags().toLowerCase().contains(value.toLowerCase())) {
                searchedList.add(prod);
            }
        }
        setOnClickListener(searchedList);
        AdapterClass adapterClass = new AdapterClass(searchedList, listener);
        System.out.println(searchedList);
        recyclerView.setAdapter(adapterClass);
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
                startActivity(intent);
            }
        };
    }

    public void openProductPage() {
        Intent intent = new Intent(this, ProductPage.class);
        TextView text = (TextView)findViewById(R.id.editTextTextPersonName2);
        String in = text.getText().toString();
        intent.putExtra("input",in);
        startActivity(intent);
    }
}