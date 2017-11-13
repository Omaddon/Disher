package com.ammyt.disher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ammyt.disher.R
import com.ammyt.disher.adapter.DishesAvailableRecyclerViewAdapter
import com.ammyt.disher.model.DishesAvailable
import com.ammyt.disher.model.Table

class DishesAvailableActivity : AppCompatActivity() {

    private lateinit var dishesAvailableRecyclerView: RecyclerView

    companion object {
        private val REQUEST_DISH_FOR_ADD = 1
        private val TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA = "TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA"

        fun intent(context: Context, table: Table): Intent {
            val intent = Intent(context, DishesAvailableActivity::class.java)
            intent.putExtra(TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA, table)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dishes_available)

        dishesAvailableRecyclerView = findViewById(R.id.dishes_available_recycler_view)
        dishesAvailableRecyclerView.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.dishes_available_recycler_colums))
        dishesAvailableRecyclerView.itemAnimator = DefaultItemAnimator()

        val adapter = DishesAvailableRecyclerViewAdapter()
        dishesAvailableRecyclerView.adapter = adapter

        adapter.onClickListener = View.OnClickListener { v: View? ->
            val table = intent.getSerializableExtra(TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA) as? Table
            val position = dishesAvailableRecyclerView.getChildAdapterPosition(v)

            val intent = AddDishDetailActivity.intent(this, table, DishesAvailable.getDish(position))

            startActivityForResult(intent, REQUEST_DISH_FOR_ADD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_DISH_FOR_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                val returnIntent = Intent()
                val table = data?.getSerializableExtra(AddDishDetailActivity.TABLE_TO_ADD_DISH)

                if (table != null) {
                    returnIntent.putExtra(AddDishDetailActivity.TABLE_TO_ADD_DISH, table)
                    setResult(Activity.RESULT_OK, returnIntent)
                }
                else {
                    setResult(Activity.RESULT_CANCELED)
                }

                finish()
            }
            else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}
