package com.example.intentkt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mv = findViewById<ConstraintLayout>(R.id.main)

        val mvBtn = mv.findViewById<Button>(R.id.btn)
        val mvTv = mv.findViewById<TextView>(R.id.tv)

        mvBtn.setOnClickListener {

            val intent = Intent(this, SubActivity::class.java) // 다음화면으로 이동하기위한 인텐트객체
            intent.putExtra("msg", mvTv.text.toString())
            startActivity(intent)
            finish() // 자신액티비티 제거
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}