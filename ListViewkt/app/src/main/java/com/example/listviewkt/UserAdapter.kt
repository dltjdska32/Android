package com.example.listviewkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * 리스트뷰 커스텀 어뎁터
 */
class UserAdapter (val context: Context, val userList: ArrayList<User> ) : BaseAdapter(){
    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(position: Int): Any? {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    // 뷰를가져왔을때 어떻게 뿌려줄것인지 메서드
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        // 뷰를붙힐때 layoutinflater사용
        val view : View = LayoutInflater.from(context).inflate(R.layout.list_user, null)

        val profile = view.findViewById<ImageView>(/* id = */ R.id.iv_profile)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val id = view.findViewById<TextView>(R.id.tv_id)
        val pw = view.findViewById<TextView>(R.id.tv_pw)
        val age = view.findViewById<TextView>(R.id.tv_age)

        val user = userList[position] // position ->  리스트뷰의 인덱스번호

        profile.setImageResource(user.profile)
        name.setText(user.name)
        id.setText(user.id)
        pw.setText(user.pw)
        age.setText(user.age.toString())

        return view
    }
}