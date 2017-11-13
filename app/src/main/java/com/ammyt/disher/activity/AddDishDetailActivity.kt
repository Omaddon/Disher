package com.ammyt.disher.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.ammyt.disher.R
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.Table

class AddDishDetailActivity : AppCompatActivity() {

    companion object {
        val TABLE_TO_ADD_DISH = "TABLE_TO_ADD_DISH"
        val DISH_TO_SHOW = "DISH_TO_SHOW"

        fun intent(context: Context, table: Table?, dish: Dish): Intent {
            val intent = Intent(context, AddDishDetailActivity::class.java)
            intent.putExtra(TABLE_TO_ADD_DISH, table)
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
    }
}
