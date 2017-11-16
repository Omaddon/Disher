package com.ammyt.disher.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ammyt.disher.Allergen
import com.ammyt.disher.R
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables

class DishDetailActivity : AppCompatActivity() {

    companion object {
        val DISH_FOR_DETAIL = "DISH_FOR_DETAIL"
        val TABLE_FOR_DETAIL = "TABLE_FOR_DETAIL"

        fun newIntent(context: Context, dish: Dish?, table: Table?): Intent {
            val intent = Intent(context, DishDetailActivity::class.java)
            intent.putExtra(DISH_FOR_DETAIL, dish)
            intent.putExtra(TABLE_FOR_DETAIL, table)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)

        val dish = intent.getSerializableExtra(DISH_FOR_DETAIL) as? Dish
        val table = intent.getSerializableExtra(TABLE_FOR_DETAIL) as? Table

        dish?.let {
            val image = findViewById<ImageView>(R.id.dish_image)
            val name = findViewById<TextView>(R.id.dish_name)
            val price = findViewById<TextView>(R.id.dish_price)
            val description = findViewById<TextView>(R.id.dish_options)
            val options = findViewById<TextView>(R.id.dish_options)

            image.setImageResource(it.image)
            name.text = it.name
            price.text = getString(R.string.dish_price, it.price)
            description.text = it.description
            options.text = it.options

            updateAllergen(it)
        }

        findViewById<Button>(R.id.ok_dish_button).setOnClickListener { okDish() }
        findViewById<Button>(R.id.delete_dish_button)?.setOnClickListener { deleteDish(dish, table) }
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

    private fun okDish() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun deleteDish(dish: Dish?, table: Table?) {
        if (dish != null && table != null) {
            AlertDialog.Builder(this)
                    .setTitle("Caution!!")
                    .setMessage("Dish will be delete!! Are you sure?")
                    .setPositiveButton("YES", { dialog, which ->
                        dialog.dismiss()

                        table.deleteDish(dish)

                        val returnIntent = Intent()
                        returnIntent.putExtra(TABLE_FOR_DETAIL, table)

                        setResult(Activity.RESULT_OK, returnIntent)
                        finish()
                    })
                    .setNegativeButton("CANCEL", { dialog, which ->
                        dialog.dismiss()
                    })
                    .show()
        }
        else {
            okDish()
        }
    }
}
