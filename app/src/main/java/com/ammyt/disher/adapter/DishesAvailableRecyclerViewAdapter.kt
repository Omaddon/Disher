package com.ammyt.disher.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ammyt.disher.R
import com.ammyt.disher.model.DishesAvailable


class DishesAvailableRecyclerViewAdapter :
        RecyclerView.Adapter<DishesAvailableRecyclerViewAdapter.DishesAvailableViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onBindViewHolder(holder: DishesAvailableViewHolder?, position: Int) {
        holder?.bindDishAvailable(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishesAvailableViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_dishes_available, parent, false)
        view.setOnClickListener(onClickListener)

        return DishesAvailableViewHolder(view)
    }

    override fun getItemCount(): Int {
        return DishesAvailable.count
    }


    inner class DishesAvailableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.dish_available_name)

        fun bindDishAvailable(position: Int) {
            name.text = DishesAvailable.getDish(position).name
        }
    }
}