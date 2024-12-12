package com.example.cobauas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cobauas.database.PerhitunganDB
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    private lateinit var DB: PerhitunganDB
    val dbFirebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (dataUser == "") {
            startActivity(Intent(this, Login::class.java))
        }

        DB = PerhitunganDB.getDatabase(this)
    }

    override fun onStart() {
        super.onStart()

        // Mengambil nomor HP dari database firebase
        dbFirebase.collection("users")
            .document(dataUser)
            .get()
            .addOnSuccessListener {
                result ->
                val nomorHP = result.get("nomorHP").toString()

                // Mengambil list berat bedasarkan akun yg di login
                val listView = findViewById<ListView>(R.id.listView)
                val perhitunganList = DB.perhitunganDAO().getPerhitungan(nomorHP)
                val displayList = perhitunganList.map { perhitungan ->
                    "Nama: ${perhitungan.nama}, Berat: ${perhitungan.berat} kg"
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
                listView.adapter = adapter

                // Menambah data pada room database
                val buttonHitung = findViewById<Button>(R.id.hitungButton)
                buttonHitung.setOnClickListener {
                    val inputNama = findViewById<EditText>(R.id.inputNama).text.toString()
                    val inputBerat = findViewById<EditText>(R.id.inputBerat).text.toString()

                    CoroutineScope(Dispatchers.IO).async {
                        DB.perhitunganDAO().insert(
                            com.example.cobauas.database.Perhitungan(
                                nomorHP = nomorHP,
                                nama = inputNama,
                                berat = inputBerat.toInt()
                            )
                        )
                    }
                    val listView = findViewById<ListView>(R.id.listView)
                    val perhitunganList = DB.perhitunganDAO().getPerhitungan(nomorHP)
                    val displayList = perhitunganList.map { perhitungan ->
                        "Nama: ${perhitungan.nama}, Berat: ${perhitungan.berat} kg"
                    }
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
                    listView.adapter = adapter
                }
            }
    }

    companion object {
        var dataUser = ""
    }
}