package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    ArrayList<Product> list;
    private RecyclerViewClickListener listener;

    public AdapterClass(ArrayList<Product> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.prodName.setText(list.get(position).getProductName());
        holder.prodPrice.setText("$" + list.get(position).getPrice());
        String availability = list.get(position).getAvailability();
        if (availability.equals("0")) {
            holder.stock.setText("Out of stock");
            holder.stock.setTextColor(Color.RED);
            Picasso.get().load("https://hdclipartall.com/images/red-cross-mark-clipart-red-x-mark-icon-256.png").into(holder.check);
        } else {
            holder.stock.setText(availability + " in stock");
            holder.stock.setTextColor(Color.parseColor("#095C15"));
            Picasso.get().load("https://icones.pro/wp-content/uploads/2021/02/icone-de-coche-verte.png").into(holder.check);
        }
        holder.rating.setText(list.get(position).getReview());
        Picasso.get().load(list.get(position).getPictureOfProduct()).into(holder.prodPic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView prodName, prodPrice, stock, rating;
        ImageView prodPic, check;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.prodName);
            prodPrice = itemView.findViewById(R.id.prodPrice);
            stock = itemView.findViewById(R.id.stock);
            rating = itemView.findViewById(R.id.rating);
            prodPic = itemView.findViewById(R.id.prodPic);
            check = itemView.findViewById(R.id.check);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getBindingAdapterPosition());
        }
    }
}
