package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseActions {
    private DatabaseReference ref;

    public DatabaseActions() {
        this.ref = FirebaseDatabase.getInstance().getReference();
    }
    public ArrayList<String> getItems() {
        ref = ref.child("Product");
        ArrayList<String> items = new ArrayList<>();
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d1 : snapshot.getChildren()) {
                            String prodName = d1.child("productName").getValue(String.class);
                            items.add(prodName);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("error retrieval");
                        return;
                    }
                }
        );
        return items;
    }


    public void clearDatabase() {
        ref.setValue(null);
    }
//    private void updateDatabase(String item, String price, String location, String sku, String tags, String availability, String pictureOfProduct) {
//        Map<String, Object> fishingTools = new HashMap<>();
//        fishingTools.put("price", price);
//        fishingTools.put("location", location);
//        fishingTools.put("SKU", sku);
//        fishingTools.put("tags", tags);
//        fishingTools.put("availability", availability);
//        fishingTools.put("pictureOfProduct", pictureOfProduct);
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference(item);
//        db.updateChildren(fishingTools).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
