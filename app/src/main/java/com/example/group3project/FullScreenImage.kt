package com.example.group3project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide

class FullScreenImage : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var closeButton: Button
    private lateinit var productTitleTV: TextView
    private lateinit var productDescriptionTV: TextView
    private lateinit var productRatingTV: RatingBar
    private lateinit var productPriceTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        imageView = findViewById(R.id.imgDisplay)
        closeButton = findViewById(R.id.btnClose)
        productTitleTV = findViewById(R.id.product_title)
        productDescriptionTV = findViewById(R.id.product_description)
        productRatingTV = findViewById(R.id.product_rating)
        productPriceTV = findViewById(R.id.product_price)

        val intent = intent
        val imageURL = intent.getStringExtra("imageURL")
        val productTitle = intent.getStringExtra("productTitle")
        val productDescription = intent.getStringExtra("productDescription")
        val productRating = intent.getDoubleExtra("productRating", 0.0)
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        Glide.with(this)
            .load(imageURL)
            .placeholder(R.drawable.placeholder_image)
            .into(imageView)
        productTitleTV.text = productTitle
        productDescriptionTV.text = productDescription
        productRatingTV.rating = productRating.toFloat()
        productPriceTV.text = productPrice.toString()


        closeButton.setOnClickListener {
            finish()
        }
    }
}