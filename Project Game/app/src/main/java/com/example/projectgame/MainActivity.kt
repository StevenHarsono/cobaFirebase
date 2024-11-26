package com.example.projectgame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val mFragmentManager = supportFragmentManager
        val mfSatu = fsatu()

        mFragmentManager.findFragmentByTag(fsatu::class.java.simpleName)
        mFragmentManager
            .beginTransaction()
            .add(R.id.main, mfSatu, fsatu::class.java.simpleName)
            .commit()


        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)

        btn1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fsatu())
                .addToBackStack(null)
                .commit()
        }

        btn2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fdua())
                .addToBackStack(null)
                .commit()
        }

        btn3.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, ftiga())
                .addToBackStack(null)
                .commit()
        }


    }

    companion object {
        var lower = 1
        var upper = 3
        var score = 0
    }
}