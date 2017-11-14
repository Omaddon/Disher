package com.ammyt.disher.model

import java.io.Serializable


object DishesAvailable : Serializable {

    private var dishesAvailable: MutableList<Dish> = mutableListOf()

    val count: Int
        get() = dishesAvailable.size

    fun getDish(index: Int): Dish = dishesAvailable[index]
    fun addDish(dish: Dish) = dishesAvailable.add(dish)

    fun getDishesAvailable(): List<Dish> = dishesAvailable
}