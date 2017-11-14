package com.ammyt.disher.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ammyt.disher.R
import com.ammyt.disher.model.Dish


class DishRecyclerViewAdapter(val dishList: List<Dish>?) :
        RecyclerView.Adapter<DishRecyclerViewAdapter.DishListViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onBindViewHolder(holder: DishListViewHolder?, position: Int) {
        dishList?.let {
            holder?.bindDish(dishList.get(position), position)
        }
    }

    override fun getItemCount(): Int {
        return dishList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_dish_list, parent, false)
        view.setOnClickListener(onClickListener)

        return DishListViewHolder(view)
    }


    inner class DishListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dishImage = itemView.findViewById<ImageView>(R.id.dish_image)
        val dishName = itemView.findViewById<TextView>(R.id.dish_name)

        fun bindDish(dish: Dish, position: Int) {
            val context = itemView.context

            dishImage.setImageResource(dish.image)
            dishName.text = dish.name
        }
    }
}