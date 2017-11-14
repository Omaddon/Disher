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
import com.ammyt.disher.model.Tables

class TableListActivity :
        AppCompatActivity(),
        TableListFragment.OnTableSelectedListener,
        DishPagerFragment.DishPagerAdapter,
        DishListFragment.OnAddDishToTable {

    private lateinit var tableSelected: Table

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        // TODO descargar lista de posibles platos

        tableSelected = Tables.get(0)

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
        table?.let { tableSelected = table }

        if (dishPagerFragment == null) {
            startActivity(DishPagerActivity.intent(this, position))
        }
        else {
            dishPagerFragment.moveToTable(position)
        }
    }

    override fun fragmentToShow(table: Table): Fragment {
        return DishListFragment.newInstance(table)
    }

    override fun showDishAvailable(table: Table?) {
        val intent = DishesAvailableActivity.intent(this, table)

        startActivityForResult(intent, DishPagerActivity.REQUEST_DISH_AVAILABLE)
    }
}
