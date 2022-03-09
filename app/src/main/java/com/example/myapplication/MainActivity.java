package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String locationStr = extras.getString("location");
            TextView textView = findViewById(R.id.location);
            textView.setText(locationStr);
        }
        DatabaseActions actions = new DatabaseActions();
        actions.clearDatabase();
        pushJson();
        ArrayList<String> items = actions.getItems();
        AutoCompleteTextView editText = findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        editText.bringToFront();
        editText.setAdapter(adapter);

        button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.actv);
                if (!textView.getText().toString().isEmpty()) {
                    openProductPage();
                }
            }
        });

        ImageButton locationButton = findViewById(R.id.openLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLocationsPage();
            }
        });
    }

    public void openLocationsPage() {
        Intent intent = new Intent(MainActivity.this, StoreLocator.class);
        TextView location = findViewById(R.id.location);
        String loc = location.getText().toString();
        intent.putExtra("location", loc);
        startActivity(intent);
    }

    public void openProductPage() {
        TextView text = findViewById(R.id.actv);
        TextView location = findViewById(R.id.location);
        String input = text.getText().toString();
        String locStr = location.getText().toString();
        Intent intent = new Intent(MainActivity.this, ProductPage.class);
        intent.putExtra("location", locStr);
        intent.putExtra("input", input);
        startActivity(intent);
    }
    private void pushJson() {
        String json = null;
        try {
            InputStream is = getAssets().open("result.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Map<String, Object> jsonMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Product");
        ref.setValue(jsonMap);
    }
}