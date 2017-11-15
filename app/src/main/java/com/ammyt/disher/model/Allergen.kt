package com.ammyt.disher

object Allergen {

    enum class AllergenList {
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

    fun toAllergen(allergenId: Int?) = when (allergenId) {
        0 -> AllergenList.CELERY
        1 -> AllergenList.CRUSTACEAN
        2 -> AllergenList.EGG
        3 -> AllergenList.FISH
        4 -> AllergenList.GLUTEN
        5 -> AllergenList.LUPIN
        6 -> AllergenList.MILK
        7 -> AllergenList.MOLLUSC
        8 -> AllergenList.MUSTARD
        9 -> AllergenList.PEANUT
        10 -> AllergenList.SESAMO
        11 -> AllergenList.SOYA
        12 -> AllergenList.SULPHITE
        13 -> AllergenList.TREENUTS
        else -> null
    }

    fun imageToAllergen(imageId: Int) = when (imageId) {
        R.id.allergen_celery_image      -> AllergenList.CELERY
        R.id.allergen_crustacean_image  -> AllergenList.CRUSTACEAN
        R.id.allergen_egg_image         -> AllergenList.EGG
        R.id.allergen_fish_image        -> AllergenList.FISH
        R.id.allergen_gluten_image      -> AllergenList.GLUTEN
        R.id.allergen_lupin_image       -> AllergenList.LUPIN
        R.id.allergen_milk_image        -> AllergenList.MILK
        R.id.allergen_mollusc_image     -> AllergenList.MOLLUSC
        R.id.allergen_mustard_image     -> AllergenList.MUSTARD
        R.id.allergen_peanut_image      -> AllergenList.PEANUT
        R.id.allergen_sesamo_image      -> AllergenList.SESAMO
        R.id.allergen_soya_image        -> AllergenList.SOYA
        R.id.allergen_sulphite_image    -> AllergenList.SULPHITE
        R.id.allergen_treenuts_image    -> AllergenList.TREENUTS
        else                            -> null
    }

    fun drawableDish(image: Int?) = when (image) {
        0 -> R.drawable.food_beer
        1 -> R.drawable.food_cesar_salad
        2 -> R.drawable.food_chicken
        3 -> R.drawable.food_coke
        4 -> R.drawable.food_croquette
        5 -> R.drawable.food_dimsum
        6 -> R.drawable.food_macaroni
        7 -> R.drawable.food_paella
        8 -> R.drawable.food_sushi
        9 -> R.drawable.food_wine
        10 -> R.drawable.food_cheese_cake
        11 -> R.drawable.food_cod
        12 -> R.drawable.food_eggs
        13 -> R.drawable.food_sundae
        else -> R.drawable.error
    }
}