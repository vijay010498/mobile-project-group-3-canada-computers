package com.example.group3project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.group3project.R
import com.example.group3project.models.CartItem
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CartAdapter (private val cartList: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView
        val txtProductPrice: TextView
        val txtProductName: TextView

        init {
            imgProduct = itemView.findViewById(R.id.product_image)
            txtProductPrice = itemView.findViewById(R.id.product_price)
            txtProductName = itemView.findViewById(R.id.product_title)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val data = cartList[position]
        holder.txtProductPrice.text = data.productPrice.toString()
        holder.txtProductName.text = data.productName

        Glide.with(holder.imgProduct).load(data.productImage).into(holder.imgProduct)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun getTotalCartItemsCost(): Double {
        var totalCost = 0.0

        for (cartItem in cartList) {
            totalCost += cartItem.productPrice ?: 0.0
        }

        return totalCost
    }



}