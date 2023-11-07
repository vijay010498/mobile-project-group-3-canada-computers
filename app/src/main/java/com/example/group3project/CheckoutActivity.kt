package com.example.group3project

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : AppCompatActivity() {
    private lateinit var totalAmountTV: TextView
    private lateinit var totalProductsCount: TextView
    private lateinit var checkoutBtn: Button
    private lateinit var progressDialog: ProgressDialog
    private val handler = Handler(Looper.getMainLooper())

    // checkout form fields
    private lateinit var editShippingName: MaterialAutoCompleteTextView
    private lateinit var editShippingEmail: MaterialAutoCompleteTextView
    private lateinit var editShippingAddress: MaterialAutoCompleteTextView
    private lateinit var editShippingPincode: MaterialAutoCompleteTextView
    private lateinit var creditCardCvv: TextInputEditText
    private lateinit var creditCardNumber: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        totalAmountTV = findViewById(R.id.total_amount)
        checkoutBtn = findViewById(R.id.btn_checkout)
        totalProductsCount = findViewById(R.id.total_products_count)

        val intent = intent
        val totalPrice = intent.getDoubleExtra("totalAmount", 0.0)
        val cartItems  = intent.getStringExtra("cartItems")
        val totalProducts = intent.getIntExtra("totalProducts", 0)

        Log.i("cart-items-checkout", cartItems.toString())

        totalAmountTV.text = "Total: $$totalPrice"
        totalProductsCount.text = "Products: $totalProducts"

        checkoutBtn.setOnClickListener {
            editShippingName = findViewById(R.id.edit_shipping_name)
            editShippingEmail = findViewById(R.id.edit_shipping_email)
            editShippingAddress = findViewById(R.id.edit_shipping_address)
            editShippingPincode = findViewById(R.id.edit_shipping_pincode)
            creditCardNumber = findViewById(R.id.credit_card_number)
            creditCardCvv = findViewById(R.id.credit_card_cvv)

            val name = editShippingName.text.toString().trim()
            val email = editShippingEmail.text.toString().trim()
            val address = editShippingAddress.text.toString().trim()
            val pincode = editShippingPincode.text.toString().trim()
            val cardNumber = creditCardNumber.text.toString().trim()
            val cvv = creditCardCvv.text.toString().trim()

            if (name.isEmpty()) {
                editShippingName.error = "Please enter your name"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                editShippingEmail.error = "Please enter your email"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editShippingEmail.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            if (address.isEmpty()) {
                editShippingAddress.error = "Please enter your address"
                return@setOnClickListener
            }

            if (pincode.isEmpty()) {
                editShippingPincode.error = "Please enter your postal code"
                return@setOnClickListener
            }

            if (cardNumber.isEmpty()) {
                creditCardNumber.error = "Please enter your credit card number"
                return@setOnClickListener
            }

            if (cardNumber.length != 16) {
                creditCardNumber.error = "Please enter a valid 16-digit credit card number"
                return@setOnClickListener
            }

            if (cvv.isEmpty()) {
                creditCardCvv.error = "Please enter your CVV"
                return@setOnClickListener
            }

            if (cvv.length != 3) {
                creditCardCvv.error = "Please enter a valid 3-digit CVV"
                return@setOnClickListener
            }

            // create dummy checkout flow
            progressDialog = ProgressDialog(this@CheckoutActivity)
            progressDialog.setCancelable(false)
            progressDialog.setMessage("Checking Out, Please wait")
            progressDialog.show()

            handler.postDelayed({
                progressDialog.setMessage("Checking Inventory, Please wait")
                handler.postDelayed({
                    progressDialog.setMessage("Processing Payment $totalPrice CAD")
                    handler.postDelayed({
                        progressDialog.setMessage("Contacting Bank")
                        handler.postDelayed({
                            progressDialog.setMessage("Payment Processed, Thank you")
                            handler.postDelayed({
                                val auth = FirebaseAuth.getInstance()
                                val currentUser = auth.currentUser
                                val userId = currentUser?.uid
                                val databaseReference = FirebaseDatabase.getInstance().reference
                                val cartItemsRef = databaseReference.child("users").child(userId!!).child("cart items")

                                cartItemsRef.removeValue()

                                handler.postDelayed({
                                    progressDialog.setMessage("Placing Order")
                                    val orderItem = mapOf(
                                        "totalAmount" to totalPrice,
                                        "totalProducts" to totalProducts,
                                        "cartItems" to cartItems,
                                    )
                                    databaseReference.child("users").child(userId).child("orders").push().setValue(orderItem)
                                    handler.postDelayed({
                                        progressDialog.setMessage("Order Placed")
                                        progressDialog.dismiss()
                                        finish()
                                        val productsIntent = Intent(this@CheckoutActivity, ProductsActivity::class.java)
                                        startActivity(productsIntent)
                                    },1800)
                                },1400)
                            }, 1000)
                        }, 700)
                    }, 500)
                }, 400)
            }, 300)
        }

    }
}