package com.example.intentkt

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sub)

        val sv = findViewById<ConstraintLayout>(R.id.sub)
        val tvSub = sv.findViewById<TextView>(R.id.tv_sub)

        if(intent.hasExtra("msg")) {
            tvSub.setText(intent.getStringExtra("msg"))

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sub)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}