package com.example.navigationviewkt

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var ivBtn: ImageView
    private lateinit var dl: DrawerLayout
    private lateinit var nv: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ivBtn = findViewById(R.id.btn_nv)
        dl = findViewById(R.id.layout_drawer)
        nv = findViewById(R.id.naviView)


        ivBtn.setOnClickListener {
            dl.openDrawer(GravityCompat.START) // Start = left , End = right
            nv.setNavigationItemSelectedListener (this) // 네비게이션 메뉴아이템에 클릭속성뷰여 onNavigationItemSelected 메서드 실행위한 설정

        }

        // 뒤로가기 버튼 클릭시 최신버전
//        onBackPressedDispatcher.addCallback(
//            this,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    // 드로우어가 열려있으면 닫고 아니면 뒤로가기할경우 앱나감
//                    if (dl.isDrawerOpen(GravityCompat.START)) {
//                        dl.closeDrawers()
//                    } else {
//                        // 이 콜백을 끄고 기본 뒤로가기 동작 실행
//                        isEnabled = false
//                        onBackPressedDispatcher.onBackPressed()
//                    }
//                }
//            }
//        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_drawer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // 네비게시연 메뉴 아이템 클릭시 수행

        when(item.itemId) {
            R.id.acs -> Toast.makeText(applicationContext, "접근성", Toast.LENGTH_SHORT).show()
            R.id.mail -> Toast.makeText(applicationContext, "메일", Toast.LENGTH_SHORT).show()
            R.id.send -> Toast.makeText(applicationContext, "메시지", Toast.LENGTH_SHORT).show()
        }


        dl.closeDrawers() //네비뷰닫기
        return true
    }

    // 뒤로가기 버튼 클릭시 디플리케이티드됨 .
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}