package com.ammyt.disher.activity

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.ammyt.disher.R
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.fragment.DishPagerFragment
import com.ammyt.disher.model.Table

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

        if (fragmentManager.findFragmentById(R.id.dish_pager_fragment) == null) {
            val tableIndex = intent.getIntExtra(TABLE_INDEX_EXTRA, 0)
            val dishPagerFragment = DishPagerFragment.newInstance(tableIndex)
            fragmentManager.beginTransaction()
                    .add(R.id.dish_pager_fragment, dishPagerFragment)
                    .commit()
        }
    }

    override fun fragmentToShow(table: Table): Fragment {
        return DishListFragment.newInstance(table)
    }
}
