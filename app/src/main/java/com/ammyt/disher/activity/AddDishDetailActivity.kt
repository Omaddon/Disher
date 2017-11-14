package com.ammyt.disher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ammyt.disher.R
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.Tables

class AddDishDetailActivity : AppCompatActivity() {

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
            val image = findViewById<ImageView>(R.id.dish_detail_image)
            val name = findViewById<TextView>(R.id.dish_detail_name)

            image.setImageResource(it.image ?: R.drawable.error)
            name.text = it.name
        }
        val addDishButton = findViewById<Button>(R.id.add_dish_button)
        addDishButton.setOnClickListener { addDish(dish) }

        findViewById<Button>(R.id.cancel_dish_button)?.setOnClickListener { cancelDish() }
    }

    private fun cancelDish() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun addDish(dish: Dish?) {
        if (dish != null) {
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
