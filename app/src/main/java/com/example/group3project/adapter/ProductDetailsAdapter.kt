package com.example.group3project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.group3project.R
import com.example.group3project.models.Product
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductDetailsAdapter(options: FirebaseRecyclerOptions<Product>) : FirebaseRecyclerAdapter<Product, ProductDetailsAdapter.MyViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater  = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Product) {

        val storRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
        Glide.with(holder.product_image.context).load(storRef).into(holder.product_image)

        holder.product_price.text = model.price.toString()
        holder.product_title.text = model.title
        holder.product_rating.text = model.rating.toString()
        holder.product_description.text = model.description


    }

    inner class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.activity_full_screen_image, parent, false))
    {

        val product_title: TextView = itemView.findViewById(R.id.product_title)
        val product_price: TextView = itemView.findViewById(R.id.product_price)
        val product_image: ImageView = itemView.findViewById(R.id.product_image)
        val product_description: TextView = itemView.findViewById(R.id.product_description)
        val product_rating: TextView = itemView.findViewById(R.id.product_rating)




    }
}