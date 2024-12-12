package com.example.cobauas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cobauas.database.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Register : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegister = findViewById<Button>(R.id.registerButton)

        btnRegister.setOnClickListener {
            val nomorHP = findViewById<EditText>(R.id.editText3).text.toString()
            val password = findViewById<EditText>(R.id.editText4).text.toString()
            tambahData(db, nomorHP, password)
        }
    }

    fun tambahData(db: FirebaseFirestore, nomorHP: String, password: String) {
        val dataBaru = User(nomorHP, password)

        db.collection("users")
            .add(dataBaru)
            .addOnSuccessListener {
                startActivity(Intent(this, Login::class.java))
            }
    }
}