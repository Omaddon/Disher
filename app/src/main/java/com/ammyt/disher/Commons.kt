package com.ammyt.disher


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

fun toAllergen(allergenId: Int?) = when (allergenId) {
    0   -> Allergen.CELERY
    1   -> Allergen.CRUSTACEAN
    2   -> Allergen.EGG
    3   -> Allergen.FISH
    4   -> Allergen.GLUTEN
    5   -> Allergen.LUPIN
    6   -> Allergen.MILK
    7   -> Allergen.MOLLUSC
    8   -> Allergen.MUSTARD
    9   -> Allergen.PEANUT
    10  -> Allergen.SESAMO
    11  -> Allergen.SOYA
    12  -> Allergen.SULPHITE
    13  -> Allergen.TREENUTS
    else -> null
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