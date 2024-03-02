package com.example.kotlinproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val nameText = findViewById<TextView>(R.id.nameText)
        val emailText = findViewById<TextView>(R.id.emailText)
        val phoneText = findViewById<TextView>(R.id.phoneText)
        val edName = findViewById<TextInputEditText>(R.id.edName)
        val edPhone = findViewById<TextInputEditText>(R.id.edPhone)
        val saveBtn = findViewById<MaterialButton>(R.id.saveBtn)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        ///
        val nameInt = intent.getStringExtra(LoginActivity.nameKEY)
        val emailInt = intent.getStringExtra(LoginActivity.emailKEY)
        val phoneInt = intent.getStringExtra(LoginActivity.phoneKEY)

        nameText.text = "Welcome $nameInt"
        emailText.text = "Email : $emailInt"
        phoneText.text = "Phone : $phoneInt"


        ///
        saveBtn.setOnClickListener {
            val name = edName.text.toString().trim()
            val phone = edPhone.text.toString().trim()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                val contact = Contact(name, phone)

                databaseReference = FirebaseDatabase.getInstance().getReference("Contact")
                databaseReference.child(phone).setValue(contact).addOnSuccessListener {
                    //
                    progressBar.visibility = View.GONE
                    SuccessDialog()

                }.addOnFailureListener {
                    Log.e("error", it.toString())
                }

            } else Toast.makeText(this, "Please insert your data", Toast.LENGTH_SHORT).show()

        }


    }//

    private fun SuccessDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okBtn = dialog.findViewById<TextView>(R.id.okBtn)
        okBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


}