package com.ammyt.disher.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.ammyt.disher.R
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.fragment.TableListFragment
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables

class TableListActivity :
        AppCompatActivity(),
        TableListFragment.OnTableSelectedListener,
        DishListFragment.OnAddDishToTable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        // TODO descargar lista de posibles platos

        if (findViewById<View>(R.id.table_list_fragment) != null) {
            if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                val tableListFragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.table_list_fragment, tableListFragment)
                        .commit()
            }
        }

        if (findViewById<View>(R.id.dish_list_fragment) != null) {
            if (fragmentManager.findFragmentById(R.id.dish_list_fragment) == null) {
                val dishListFragment = DishListFragment.newInstance(Tables.get(0))
                fragmentManager.beginTransaction()
                        .add(R.id.dish_list_fragment, dishListFragment)
                        .commit()
            }
        }
    }

    override fun onTableSelected(table: Table?, position: Int) {
        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        if (dishListFragment == null) {
            // TODO revisar, modificado Tables.get(position) en lugar de table
            startActivity(DishListActivity.intent(this, Tables.get(position)))
        }
        else {
            table?.let { dishListFragment.showTable(it) }

        }
    }

    override fun showDishAvailable(table: Table?) {
        val intent = DishesAvailableActivity.intent(this, table)

        startActivityForResult(intent, DishListActivity.REQUEST_DISH_AVAILABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO implementar
    }
}
