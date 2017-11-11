package com.ammyt.disher.model

import java.io.Serializable


object Tables : Serializable {

    private var tables: List<Table> = listOf(
            Table("Table 1"),
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