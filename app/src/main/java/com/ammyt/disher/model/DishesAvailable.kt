package com.ammyt.disher.model

import com.ammyt.disher.R
import java.io.Serializable


object DishesAvailable : Serializable {

    private var dishesAvailable: MutableList<Dish> = mutableListOf(
            Dish("Beer", R.drawable.food_beer, null, 1.50f, "Beeeerrrrr")
    )

    val count: Int
        get() = dishesAvailable.size

    fun getDish(index: Int): Dish = dishesAvailable[index]
    fun putDish(dish: Dish) = dishesAvailable.add(dish)

    fun toArray() = dishesAvailable.toTypedArray()
}