package com.ammyt.disher.model

import com.ammyt.disher.Allergen
import java.io.Serializable


data class Dish(
        var name: String,
        var image: Int,
        var allergen: List<Allergen.AllergenList>?,
        var price: Float,
        var description: String? = "No description available.",
        var options: String?) : Serializable