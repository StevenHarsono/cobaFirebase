package com.example.cobafirebase

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var DataProvinsi = ArrayList<daftarProvinsi>()
    lateinit var lvAdapter : SimpleAdapter

    lateinit var _etProvinsi : EditText
    lateinit var _etIbukota : EditText

    var data: MutableList<Map<String, String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _etProvinsi = findViewById<EditText>(R.id.editTextText)
        _etIbukota = findViewById<EditText>(R.id.editTextText2)

        val _btSimpan = findViewById<Button>(R.id.button)
        val _lvData = findViewById<ListView>(R.id.listView)

        lvAdapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf<String>("Pro", "Ibu"),
            intArrayOf(
                android.R.id.text1,
                android.R.id.text2
            )
        )
        _lvData.adapter = lvAdapter

        _btSimpan.setOnClickListener {
            TambahData(db, _etProvinsi.text.toString(), _etIbukota.text.toString())
        }
        readData(db)
    }

    fun TambahData(db: FirebaseFirestore, Provinsi: String, IbuKota: String) {
        val dataBaru = daftarProvinsi(Provinsi, IbuKota)
        db.collection("tbProvinsi")
            .add(dataBaru)
            .addOnSuccessListener {
                _etProvinsi.setText("")
                _etIbukota.setText("")
                Log.d("Firebase", "Data Berhasil Disimpan")
            }
            .addOnFailureListener {
                Log.d("Firease", it.message.toString())
            }

        readData(db)
    }

    fun readData(db: FirebaseFirestore) {
        db.collection("tbProvinsi").get()
            .addOnSuccessListener {
                result ->
                DataProvinsi.clear()
                for (document in result) {
                    val readData = daftarProvinsi(
                        document.data.get("provinsi").toString(),
                        document.data.get("ibukota").toString()
                    )
                    DataProvinsi.add(readData)

                    data.clear()
                    DataProvinsi.forEach {
                        val dt: MutableMap<String, String> = HashMap(2)
                        dt["Pro"] = it.provinsi
                        dt["Ibu"] = it.ibukota
                        data.add(dt)
                    }
                }
                lvAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }
}