package com.example.group3project.models

data class CartItem(
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val productDescription: String,
    val productKey: String,
    val productCategory: String,
    val productRating: Double
){
    constructor() : this("", 0.0, "", "", "", "", 0.0)
}