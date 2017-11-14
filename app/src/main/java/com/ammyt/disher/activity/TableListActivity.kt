package com.ammyt.disher.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    private var tableSelectedIndex: Int = 0

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
                val dishListFragment = DishListFragment.newInstance(Tables.get(tableSelectedIndex), tableSelectedIndex)
                fragmentManager.beginTransaction()
                        .add(R.id.dish_list_fragment, dishListFragment)
                        .commit()
            }
        }
    }

    override fun onTableSelected(table: Table?, position: Int) {
        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        if (dishListFragment == null) {
            startActivity(DishListActivity.intent(this, table, position))
        }
        else {
            tableSelectedIndex = position
            table?.let { dishListFragment.showTable(it, position) }

        }
    }

    override fun showDishAvailable(tableIndex: Int) {
        val intent = DishesAvailableActivity.intent(this, tableIndex)

        startActivityForResult(intent, DishListActivity.REQUEST_DISH_AVAILABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DishListActivity.REQUEST_DISH_AVAILABLE) {
            if (resultCode == Activity.RESULT_OK) {

                val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment
                val newTable = data?.getSerializableExtra(AddDishDetailActivity.TABLE_TO_ADD_DISH) as? Table
                val tableIndex = data?.getIntExtra(AddDishDetailActivity.TABLE_INDEX_TO_SEND, 0)

                dishListFragment?.let {
                    if (newTable != null) {
                        if (tableIndex != null) {
                            it.showTable(newTable, tableIndex)
                        }
                    }
                }
            }
        }
    }
}
