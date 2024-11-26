package uts.c14220010.tebakangka

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frameContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mFragmentManager = supportFragmentManager
        val mfSatu = fSatu()

        mFragmentManager.findFragmentByTag(fSatu::class.java.simpleName)
        mFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, mfSatu, fSatu::class.java.simpleName)
            .commit()

        // Button navigation
        val _btnPageSatu = findViewById<Button>(R.id.btnPageSatu)
        val _btnPageDua = findViewById<Button>(R.id.btnPageDua)
        val _btnPageTiga = findViewById<Button>(R.id.btnPageTiga)

        _btnPageSatu.setOnClickListener {
            score = 50
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fSatu())
                .addToBackStack(null)
                .commit()
        }

        _btnPageDua.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fDua())
                .addToBackStack(null)
                .commit()
        }

        _btnPageTiga.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fTiga())
                .addToBackStack(null)
                .commit()
        }
    }

    companion object{
        var score: Int = 50
        var upperBound: Int = 5
        var lowerBound: Int = 1
    }
}