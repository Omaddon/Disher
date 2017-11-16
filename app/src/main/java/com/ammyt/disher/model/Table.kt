package com.ammyt.disher.model

import java.io.Serializable


data class Table(
        var name: String,
        var dishes: MutableList<Dish>?) : Serializable {

    constructor(name: String) : this(name, null)

    override fun toString() = name

    val count: Int
        get() = dishes?.size ?: 0

    fun addDish(dish: Dish) {
        if (dishes != null) {
            dishes?.add(dish)
        }
        else {
            dishes = mutableListOf(dish)
        }
    }

    fun deleteDish(dish: Dish) {
        dishes?.remove(dish)
    }

    fun replaceDishes(newDishes: MutableList<Dish>?) {
        dishes = newDishes
    }
}