package com.ammyt.disher.model

import com.ammyt.disher.R
import java.io.Serializable


object Tables : Serializable {

    private var tables: MutableList<Table> = mutableListOf(
            Table("Table 1", mutableListOf(Dish("Dish 1", R.drawable.food_beer, null, 1.4f, "No Des", null))),
            Table("Table 2"),
            Table("Table 3"),
            Table("Table 4"),
            Table("Table 5")
    )

    val count: Int
        get() = tables.size

    operator fun get(i: Int): Table = tables[i]

    fun toArray() = tables.toTypedArray()
}