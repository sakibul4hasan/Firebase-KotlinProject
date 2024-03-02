package com.example.kotlinproject

import android.content.Intent
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

class LoginActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var progressBar: ProgressBar

    companion object {
        const val nameKEY = "com.example.kotlinproject.LoginActivity.nameKEY"
        const val emailKEY = "com.example.kotlinproject.LoginActivity.emailKEY"
        const val phoneKEY = "com.example.kotlinproject.LoginActivity.phoneKEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val singUpTextId = findViewById<TextView>(R.id.singUpTextId);
        val loginBtnId = findViewById<Button>(R.id.loginBtnId)
        val editTextName = findViewById<TextInputEditText>(R.id.editTextName)
        val editTextPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val forgetTextId = findViewById<TextView>(R.id.forgetTextId)
        progressBar = findViewById(R.id.progressBar)


        singUpTextId.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        loginBtnId.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (name.isNotEmpty() && password.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                readData(name, password)

            } else {
                Toast.makeText(this, "Please enter Name and Password", Toast.LENGTH_SHORT).show()
            }
        }


    } // onCreate fun end...

    private fun readData(name: String, password: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(name).get().addOnSuccessListener {
            progressBar.visibility = View.GONE
            if (it.exists()) {
                //
                val nameString = it.child("name").value
                val passwordString = it.child("password").value
                val emailString = it.child("email").value
                val phoneString = it.child("phone").value

                if (name == nameString && password == passwordString) {
                    //
                    Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra(nameKEY, nameString.toString())
                    intent.putExtra(emailKEY, emailString.toString())
                    intent.putExtra(phoneKEY, phoneString.toString())
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "User dose not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }

}