package com.ammyt.disher.model

import java.io.Serializable


object DishesAvailable : Serializable {

    private var dishesAvailable: MutableList<Dish> = mutableListOf()

    val count: Int
        get() = dishesAvailable.size

    fun getDish(index: Int): Dish = dishesAvailable[index]
    fun putDish(dish: Dish) = dishesAvailable.add(dish)

    fun toArray() = dishesAvailable.toTypedArray()
}