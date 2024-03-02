package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        // Change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val edName = findViewById<TextInputEditText>(R.id.editTextName)
        val edPhone = findViewById<TextInputEditText>(R.id.editTextPhone)
        val edEmail = findViewById<TextInputEditText>(R.id.editTextEmail)
        val edPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val singUp = findViewById<Button>(R.id.signUpBtnId)
        val loginText = findViewById<TextView>(R.id.signInTextId)
        progressBar = findViewById(R.id.progressBar)


        singUp.setOnClickListener {

            val name = edName.text.toString()
            val phone = edPhone.text.toString()
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()

            if (name.isNotEmpty() && password.isNotEmpty()) {
                //
                progressBar.visibility = View.VISIBLE
                val user = User(name, phone, email, password)
                database = FirebaseDatabase.getInstance().getReference("Users")

                //accountExistingCheck
                database.child(name).get().addOnSuccessListener {
                    if (it.exists()) {
                        val nameString = it.child("name").value
                        val passwordString = it.child("password").value
                        if (name == nameString && password == passwordString) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(this, "Account already exist", Toast.LENGTH_SHORT).show()
                        } else {

                            //writing data
                            database.child(name).setValue(user).addOnSuccessListener {

                                progressBar.visibility = View.GONE
                                edName.text?.clear()
                                edPhone.text?.clear()
                                edEmail.setText("")
                                edPassword.setText("")
                                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                                finish()

                            }.addOnFailureListener {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }

                        }
                    } else {

                        //writing data
                        database.child(name).setValue(user).addOnSuccessListener {

                            progressBar.visibility = View.GONE
                            edName.text?.clear()
                            edPhone.text?.clear()
                            edEmail.setText("")
                            edPassword.setText("")
                            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                            finish()

                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            } else {
                Toast.makeText(this, "Please input some text", Toast.LENGTH_SHORT).show()
            }
        }


        //
        loginText.setOnClickListener {
            finish()
        }

    } // onCreate end.....

}