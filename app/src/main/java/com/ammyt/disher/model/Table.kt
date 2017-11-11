package com.ammyt.disher.model

import java.io.Serializable


data class Table(
        var name: String,
        var dishes: MutableList<Dish>) : Serializable {

    override fun toString() = name

    val count: Int
        get() = dishes.size
}