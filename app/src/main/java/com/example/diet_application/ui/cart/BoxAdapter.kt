package com.example.diet_application.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.diet_application.R


class BoxAdapter(private val context: Context, private val users: List<User>) : BaseAdapter() {

    private lateinit var box: CheckBox
    private lateinit var name: TextView
    private lateinit var contactNum: TextView

    override fun getCount(): Int {
        return users.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, CartViewModel: View?, parent: ViewGroup): View? {
        var convertView = CartViewModel
        convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
//        name = convertView.findViewById(R.id.name_cart)
//        contactNum = convertView.findViewById(R.id.desc_cart)
//        box = convertView.findViewById(R.id.cbBox)
        name.text = users[position].name
        contactNum.text = users[position].description
        return convertView
    }
}