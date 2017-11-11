package com.ammyt.disher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ammyt.disher.R
import com.ammyt.disher.model.Tables


class DishListViewAdapter(var context: Context) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return "ID ${position}"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Tables.count
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = LayoutInflater.from(context).inflate(R.layout.row_dish_list, parent, false)

        row.findViewById<TextView>(R.id.dishName).text = "ID ${position}"

        return row
    }
}
