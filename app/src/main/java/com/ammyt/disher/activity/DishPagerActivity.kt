package com.ammyt.disher.activity

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.View
import com.ammyt.disher.R
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.fragment.DishPagerFragment
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables

class DishPagerActivity : AppCompatActivity(), DishPagerFragment.DishPagerAdapter {

    companion object {
        private val TABLE_INDEX_EXTRA = "TABLE_INDEX_EXTRA"

        fun intent(context: Context, tableIndex: Int): Intent {
            val intent = Intent(context, DishPagerActivity::class.java)
            intent.putExtra(TABLE_INDEX_EXTRA, tableIndex)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_pager)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //toolbar.setLogo(R.drawable.icon_disher)
        setSupportActionBar(toolbar)

        val tableIndex = intent.getIntExtra(TABLE_INDEX_EXTRA, 0)

        if (fragmentManager.findFragmentById(R.id.dish_pager_fragment) == null) {
            val dishPagerFragment = DishPagerFragment.newInstance(tableIndex)
            fragmentManager.beginTransaction()
                    .add(R.id.dish_pager_fragment, dishPagerFragment)
                    .commit()
        }

        findViewById<FloatingActionButton>(R.id.show_dishes_available).setOnClickListener { v: View? ->
            val table = Tables.get(tableIndex)

            startActivity(DishesAvailableActivity.intent(this, table))
        }
    }

    override fun fragmentToShow(table: Table): Fragment {
        return DishListFragment.newInstance(table)
    }
}
