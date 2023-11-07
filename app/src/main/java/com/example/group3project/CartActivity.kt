package com.example.group3project

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.group3project.R
import com.example.group3project.adapter.CartAdapter
import com.example.group3project.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val totalPrice = intent.getDoubleExtra("totalAmount", 0.0)
        val totalProducts = intent.getIntExtra("totalProducts", 0)

        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            var totalAmount = 0.0
            var totalProducts = 0
            val userId = currentUser.uid
            val cartItemsReference = database.reference.child("users").child(userId).child("cart items")

            val cartItemsList = ArrayList<CartItem>()

            val cartItemsListener = object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartItemsList.clear()
                    for (itemSnapshot in snapshot.children) {
                        val cartItem = itemSnapshot.getValue(CartItem::class.java)
                        if (cartItem != null) {
                            cartItemsList.add(cartItem)
                        }
                    }

                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_cart_items)

                    val orientation = resources.configuration.orientation
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        recyclerView.setLayoutManager(GridLayoutManager(this@CartActivity, 2))
                    } else {
                        recyclerView.layoutManager = LinearLayoutManager(this@CartActivity)
                    }


                    val adapter = CartAdapter(cartItemsList)
                    recyclerView.adapter = adapter

                    totalAmount = adapter.getTotalCartItemsCost()
                    totalProducts = adapter.itemCount

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            cartItemsReference.addListenerForSingleValueEvent(cartItemsListener)
            val checkoutButton  = findViewById<Button>(R.id.button_go_to_checkout)
            checkoutButton.setOnClickListener {
                val checkoutIntent = Intent(this@CartActivity, CheckoutActivity::class.java)
                checkoutIntent.putExtra("totalAmount", totalAmount)

                val gson = Gson()
                val jsonArrayAsString = gson.toJson(cartItemsList)
                checkoutIntent.putExtra("cartItems", jsonArrayAsString)
                checkoutIntent.putExtra("totalProducts", totalProducts)
                startActivity(checkoutIntent)
            }
        }


    }
}