package com.example.edittextbtnkt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        val mainLayout =
            findViewById<ConstraintLayout>(R.id.main)

        val et = mainLayout.findViewById<EditText>(R.id.et_id)
        val btn = mainLayout.findViewById<Button>(R.id.btn_getText)
        val tv = mainLayout.findViewById<TextView>(R.id.tv_result)

        btn.setOnClickListener {
            val getVal = et.text.toString()
            tv.setText(getVal)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}