package com.example.sweetcartapp.ShoppersRoom.RecyclerViewSetup;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweetcartapp.R;

public class ProductCardViewHolder extends RecyclerView.ViewHolder {

    ImageView imgCard;
    public TextView productTitle;
    public TextView productPrice;

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        imgCard = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        productPrice = itemView.findViewById(R.id.product_price);
        // TODO: Find and store views from itemView
    }
}