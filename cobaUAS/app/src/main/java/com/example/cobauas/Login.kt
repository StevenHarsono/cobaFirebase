package com.example.cobauas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Login : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<Button>(R.id.loginButton)
        val registerText = findViewById<TextView>(R.id.registerText)

        btnLogin.setOnClickListener {
            val nomorHP = findViewById<EditText>(R.id.editText).text.toString()
            val password = findViewById<EditText>(R.id.editText2).text.toString()
            checkLogin(db, nomorHP, password)
        }

        registerText.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    fun checkLogin(db: FirebaseFirestore, nomorHP: String, password: String) {
        db.collection("users").whereEqualTo("nomorHP", nomorHP)
            .get()
            .addOnSuccessListener {
                result ->
                if (result.isEmpty) {
                    Toast.makeText(this, "Nomor HP tidak terdaftar", Toast.LENGTH_SHORT).show()
                } else {
                    if (result.documents[0].get("password").toString() == password) {
                        MainActivity.dataUser = result.documents[0].id
                        finish()
                    } else {
                        Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}