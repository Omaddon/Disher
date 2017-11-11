package com.ammyt.disher.model

import java.io.Serializable


object Tables : Serializable {

    private var tables: List<Table> = listOf(
            Table("Table 1", mutableListOf()),
            Table("Table 2", mutableListOf()),
            Table("Table 3", mutableListOf()),
            Table("Table 4", mutableListOf()),
            Table("Table 5", mutableListOf())
    )

    val count: Int
        get() = tables.size

    operator fun get(i: Int) = tables[i]

    fun toArray() = tables.toTypedArray()
}