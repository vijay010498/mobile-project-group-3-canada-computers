package com.example.group3project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class FullScreenImage : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var closeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        imageView = findViewById(R.id.imgDisplay)
        closeButton = findViewById(R.id.btnClose)

        val intent = intent
        val imageURL = intent.getStringExtra("imageURL")
        Glide.with(this)
            .load(imageURL)
            .placeholder(R.drawable.placeholder_image)
            .into(imageView)

        closeButton.setOnClickListener {
            finish()
        }
    }
}