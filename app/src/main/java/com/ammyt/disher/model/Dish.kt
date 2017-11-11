package com.ammyt.disher.model

import com.ammyt.disher.R
import java.io.Serializable


data class Dish(
        var name: String,
        var image: Int?,
        var allergen: List<Allergen>?,
        var price: Float,
        var description: String? = "No description available.") : Serializable {

    enum class Allergen {
        CELERY,
        CRUSTACEAN,
        EGG,
        FISH,
        GLUTEN,
        LUPIN,
        MILK,
        MOLLUSC,
        MUSTARD,
        PEANUT,
        SESAMO,
        SOYA,
        SULPHITE,
        TREENUTS
    }

    fun drawableAllergen(allergen: Allergen) = when (allergen) {
        Allergen.CELERY         -> R.drawable.allergen_celery
        Allergen.CRUSTACEAN     -> R.drawable.allergen_crustacean
        Allergen.EGG            -> R.drawable.allergen_egg
        Allergen.FISH           -> R.drawable.allergen_fish
        Allergen.GLUTEN         -> R.drawable.allergen_gluten
        Allergen.LUPIN          -> R.drawable.allergen_lupin
        Allergen.MILK           -> R.drawable.allergen_milk
        Allergen.MOLLUSC        -> R.drawable.allergen_mollusc
        Allergen.MUSTARD        -> R.drawable.allergen_mustard
        Allergen.PEANUT         -> R.drawable.allergen_peanut
        Allergen.SESAMO         -> R.drawable.allergen_sesamo
        Allergen.SOYA           -> R.drawable.allergen_soya
        Allergen.SULPHITE       -> R.drawable.allergen_sulphite
        Allergen.TREENUTS       -> R.drawable.allergen_treenuts
    }

    fun drawableDish(image: Int?) = when (image) {
        0       -> R.drawable.food_beer
        1       -> R.drawable.food_cesar_salad
        2       -> R.drawable.food_chicken
        3       -> R.drawable.food_coke
        4       -> R.drawable.food_croquette
        5       -> R.drawable.food_dimsum
        6       -> R.drawable.food_macaroni
        7       -> R.drawable.food_paella
        8       -> R.drawable.food_sushi
        9       -> R.drawable.food_wine
        10      -> R.drawable.food_cheese_cake
        11      -> R.drawable.food_cod
        12      -> R.drawable.food_eggs
        13      -> R.drawable.food_sundae
        else    -> R.drawable.error
    }
}