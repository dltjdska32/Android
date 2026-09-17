package com.example.listviewkt

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val lv = findViewById<ListView>(R.id.lv)
/*      안드로이드 기본 어레이 어뎁터

        val lst = arrayOf("abc", "def", "lmn", "opq", "rxz")



        lv.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lst) // 파람 1 = context - 한액티비티의 모든 정보를 담고있다.
                                                                                                              // 파람 2 = 텍스트뷰
                                                                                                                // 파람 3 = 리스트값
*/

        val lst = ArrayList<User>()

        val user1 = User(R.drawable.ic_launcher_background,"김말자", 12, "123112ㅁㄴ", "123123ㄴㄴㅇ")
        val user2 = User(R.drawable.ic_launcher_background,"김말자", 13, "123112123", "123123ㄴ1ㅋㅊㅍㄴㅇ")
        val user3 = User(R.drawable.ic_launcher_background,"김말자", 14, "123112ㅋㅌㅊㅋㅌㅁㄴ", "123123ㄴㅁㄴㅇㄹㄴㅁㅇㅎㅋㅊㅍㄴㅇ")
        val user4 = User(R.drawable.ic_launcher_background,"김말자", 15, "123112123ㄴㅁㅁㄴ", "123123ㄴㅁㅇㅎㅁㅍㄴㅇ")
        val user5 = User(R.drawable.ic_launcher_background,"김말자", 16, "12311212312ㅁㄴ", "123123ㄴㄴㅁ!#!#ㅇ")

        lst.add(user1)
        lst.add(user2)
        lst.add(user3)
        lst.add(user4)
        lst.add(user5)


        // 커스텀어뎁터 활용
        val adapter = UserAdapter(this, lst)
        lv.adapter = adapter

        lv.onItemClickListener = AdapterView.OnItemClickListener {
            parent, view, position, id ->

            val getItem : User = parent.getItemAtPosition(position) as User

            Toast.makeText(this, getItem.name, Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}