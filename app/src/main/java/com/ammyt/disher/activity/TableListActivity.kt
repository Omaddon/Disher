package com.ammyt.disher.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ammyt.disher.R
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.fragment.DishPagerFragment
import com.ammyt.disher.fragment.TableListFragment
import com.ammyt.disher.model.Table

class TableListActivity :
        AppCompatActivity(),
        TableListFragment.OnTableSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        if (findViewById<View>(R.id.table_list_fragment) != null) {
            if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                val tableListFragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.table_list_fragment, tableListFragment)
                        .commit()
            }
        }

        if (findViewById<View>(R.id.dish_pager_fragment) != null) {
            if (fragmentManager.findFragmentById(R.id.dish_pager_fragment) == null) {
                val dishPagerFragment = DishPagerFragment.newInstance(0)
                fragmentManager.beginTransaction()
                        .add(R.id.dish_pager_fragment, dishPagerFragment)
                        .commit()
            }
        }
    }

    override fun onTableSelected(table: Table?, position: Int) {
        val dishPagerFragment = fragmentManager.findFragmentById(R.id.dish_pager_fragment) as? DishPagerFragment

        if (dishPagerFragment == null) {
            startActivity(DishPagerActivity.intent(this, position))
        }
        else {
            dishPagerFragment.moveToTable(position)
        }
    }
}
