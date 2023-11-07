package com.example.group3project

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.group3project.adapter.ProductAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.group3project.models.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductsActivity : AppCompatActivity() {
    private var goToCartBtn: Button? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Loading Products")
        progressDialog!!.show()

        goToCartBtn = findViewById(R.id.button_go_to_cart)

        val productsDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Product")

        val productsItemsList = ArrayList<Product>()

        val productsItemsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productsItemsList.clear()
                for (itemSnapshot in snapshot.children) {
                    val product = itemSnapshot.getValue(Product::class.java)
                    if (product !== null) {
                        productsItemsList.add(product)
                    }
                }

                val mRecyclerView = findViewById<RecyclerView>(R.id.recycler_products)

                val orientation = resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mRecyclerView.setLayoutManager(GridLayoutManager(this@ProductsActivity, 2))
                } else {
                    mRecyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity)
                }

                val adapter = ProductAdapter(this@ProductsActivity,productsItemsList)
                mRecyclerView.adapter = adapter
                progressDialog!!.dismiss()


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        productsDatabaseReference.addListenerForSingleValueEvent(productsItemsListener)
        goToCartBtn?.setOnClickListener(View.OnClickListener {
            val cartIntent = Intent(this@ProductsActivity,
                CartActivity::class.java)
            startActivity(cartIntent)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
