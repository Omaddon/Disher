package com.ammyt.disher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ammyt.disher.Allergen
import com.ammyt.disher.R
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.Tables

class AddDishDetailActivity : AppCompatActivity() {

    private val options by lazy { findViewById<EditText>(R.id.dish_options) }

    companion object {
        val TABLE_TO_ADD_DISH = "TABLE_TO_ADD_DISH"
        val TABLE_INDEX_TO_SEND = "TABLE_INDEX_TO_SEND"
        private val DISH_TO_SHOW = "DISH_TO_SHOW"

        fun intent(context: Context, tableIndex: Int, dish: Dish): Intent {
            val intent = Intent(context, AddDishDetailActivity::class.java)
            intent.putExtra(TABLE_TO_ADD_DISH, tableIndex)
            intent.putExtra(DISH_TO_SHOW, dish)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish_detail)

        val dish = intent.getSerializableExtra(DISH_TO_SHOW) as? Dish

        dish?.let {
            val image = findViewById<ImageView>(R.id.dish_image)
            val name = findViewById<TextView>(R.id.dish_name)
            val price = findViewById<TextView>(R.id.dish_price)
            val description = findViewById<TextView>(R.id.dish_description)

            image.setImageResource(it.image)
            name.text = it.name
            price.text = getString(R.string.dish_price, it.price)
            description.text = it.description
            options.text.clear()

            updateAllergen(it)
        }

        findViewById<Button>(R.id.add_dish_button).setOnClickListener { addDish(dish) }
        findViewById<Button>(R.id.cancel_dish_button)?.setOnClickListener { cancelDish() }
    }

    private fun updateAllergen(dish: Dish) {
        val allergenImageList = mutableListOf<ImageView>()

        allergenImageList.add(findViewById(R.id.allergen_celery_image))
        allergenImageList.add(findViewById(R.id.allergen_crustacean_image))
        allergenImageList.add(findViewById(R.id.allergen_egg_image))
        allergenImageList.add(findViewById(R.id.allergen_fish_image))
        allergenImageList.add(findViewById(R.id.allergen_gluten_image))
        allergenImageList.add(findViewById(R.id.allergen_lupin_image))
        allergenImageList.add(findViewById(R.id.allergen_milk_image))
        allergenImageList.add(findViewById(R.id.allergen_mollusc_image))
        allergenImageList.add(findViewById(R.id.allergen_mustard_image))
        allergenImageList.add(findViewById(R.id.allergen_peanut_image))
        allergenImageList.add(findViewById(R.id.allergen_sesamo_image))
        allergenImageList.add(findViewById(R.id.allergen_soya_image))
        allergenImageList.add(findViewById(R.id.allergen_sulphite_image))
        allergenImageList.add(findViewById(R.id.allergen_treenuts_image))

        val allergenListSize = dish.allergen?.size

        for (i in 0 until allergenImageList.size) {
            setDisableAllergen(allergenImageList[i])

            if (allergenListSize != null) {
                for (allergenIndex in 0 until allergenListSize) {
                    val image = Allergen.imageToAllergen(allergenImageList[i].id)
                    val allergen = dish.allergen?.get(allergenIndex)

                    if (image == allergen) {
                        setEnableAllergen(allergenImageList[i])
                    }
                }
            }
        }
    }

    private fun setDisableAllergen(allergen: ImageView) {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)

        allergen.setColorFilter(colorMatrixFilter)
        allergen.alpha = 0.25f
    }

    private fun setEnableAllergen(allergen: ImageView) {
        allergen.clearColorFilter()
        allergen.alpha = 1f
    }


    private fun cancelDish() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun addDish(dish: Dish?) {
        if (dish != null) {
            if (options.text.length > 0) {
                dish.options = options.text.toString()
            }

            val tableIndex = intent.getIntExtra(TABLE_TO_ADD_DISH, 0)
            val table = Tables.get(tableIndex)
            table.addDish(dish)

            val returnIntent = Intent()
            returnIntent.putExtra(TABLE_TO_ADD_DISH, table)
            returnIntent.putExtra(TABLE_INDEX_TO_SEND, tableIndex)

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        else {
            cancelDish()
        }
    }
}
