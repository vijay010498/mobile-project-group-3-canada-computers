package com.example.group3project.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.group3project.R
import com.example.group3project.models.Product
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProductAdapter(private val context: Context, private val productsList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val product_title: TextView
        val product_price: TextView
        val product_image: ImageView
        val product_description: TextView
        val product_category: TextView
        val product_rating: RatingBar
        val btnAddToCart: Button

        init {
            product_title = itemView.findViewById(R.id.product_title)
            product_price = itemView.findViewById(R.id.product_price)
            product_image = itemView.findViewById(R.id.product_image)
            product_description = itemView.findViewById(R.id.product_description)
            product_category = itemView.findViewById(R.id.product_category)
            product_rating = itemView.findViewById(R.id.product_rating)
            btnAddToCart = itemView.findViewById(R.id.add_to_cart_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = productsList[position]
        Glide.with(holder.product_image).load(data.image).into(holder.product_image)

        holder.product_price.text = data.price.toString()
        holder.product_title.text = data.title
        holder.product_rating.rating = data.rating.toFloat()
        holder.product_description.text = data.description
        holder.product_category.text = data.category

        holder.btnAddToCart.setOnClickListener {
            val auth = Firebase.auth
            val userId = auth.uid
            val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
            Log.i("user-id", auth.uid.toString());

            val cartItem = mapOf(
                "productName" to data.title,
                "productPrice" to data.price.toDouble(),
                "productImage" to data.image,
                "productDescription" to data.description,
                "productRating" to data.rating.toDouble(),
                "productCategory" to data.category.toString()
            )
            if (userId != null) {
                databaseReference.child("users").child(userId).child("cart items").push()
                    .setValue(cartItem)
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                    .show()
                Log.i("cart-firebase", "item-savedinto FB")
            }

        }


    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}