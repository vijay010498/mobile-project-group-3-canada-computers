package com.example.group3project

import android.content.Context
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.google.firebase.storage.StorageReference
import com.firebase.ui.storage.images.FirebaseImageLoader
import java.io.InputStream

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(
        context: android.content.Context, glide: Glide, registry: Registry
    ) {
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
        )
    }
}