package com.ammyt.disher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.ammyt.disher.R
import com.ammyt.disher.adapter.DishesAvailableRecyclerViewAdapter
import com.ammyt.disher.model.DishesAvailable

class DishesAvailableActivity : AppCompatActivity() {

    private lateinit var dishesAvailableRecyclerView: RecyclerView

    companion object {
        private val REQUEST_DISH_FOR_ADD = 1
        private val TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA = "TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA"

        fun intent(context: Context, tableIndex: Int): Intent {
            val intent = Intent(context, DishesAvailableActivity::class.java)
            intent.putExtra(TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA, tableIndex)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dishes_available)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dishesAvailableRecyclerView = findViewById(R.id.dishes_available_recycler_view)
        dishesAvailableRecyclerView.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.dishes_available_recycler_colums))
        dishesAvailableRecyclerView.itemAnimator = DefaultItemAnimator()

        val adapter = DishesAvailableRecyclerViewAdapter()
        dishesAvailableRecyclerView.adapter = adapter

        adapter.onClickListener = View.OnClickListener { v: View? ->
            val tableIndex = intent.getIntExtra(TABLE_FOR_DISH_AVAILABLE_LIST_EXTRA, 0)
            val position = dishesAvailableRecyclerView.getChildAdapterPosition(v)

            val intent = AddDishDetailActivity.intent(this, tableIndex, DishesAvailable.getDish(position))

            startActivityForResult(intent, REQUEST_DISH_FOR_ADD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_DISH_FOR_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    setResult(Activity.RESULT_OK, data)
                }
                else {
                    setResult(Activity.RESULT_CANCELED)
                }

                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
