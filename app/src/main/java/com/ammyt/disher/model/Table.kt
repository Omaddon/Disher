package com.ammyt.disher.model


data class Table(
        var name: String,
        var dishes: MutableList<Dish>) {

    override fun toString() = name
}