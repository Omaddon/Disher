package com.ammyt.disher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.ammyt.disher.R
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables

class DishListActivity : AppCompatActivity(),
        DishListFragment.OnAddDishToTable,
        DishListFragment.OnDeviceRotate,
        DishListFragment.OnShowBill {

    companion object {
        private val TABLE_EXTRA = "TABLE_EXTRA"
        private val TABLEINDEX_EXTRA = "TABLEINDEX_EXTRA"
        val REQUEST_DISH_AVAILABLE = 1
        val REQUEST_BILL = 2

        private var table: Table? = null
        var tableIndex: Int = 0

        fun intent(context: Context, table: Table?, tableIndex: Int): Intent {
            val intent = Intent(context, DishListActivity::class.java)
            intent.putExtra(TABLE_EXTRA, table)
            intent.putExtra(TABLEINDEX_EXTRA, tableIndex)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //toolbar.setLogo(R.drawable.icon_disher)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        if (dishListFragment == null) {
            val newTable = intent.getSerializableExtra(TABLE_EXTRA) as? Table
            val newTableIndex = intent.getIntExtra(TABLEINDEX_EXTRA, 0)

            newTable?.let { table = newTable }
            tableIndex = newTableIndex

            val newDishListFragment = DishListFragment.newInstance(newTable, newTableIndex)
            fragmentManager.beginTransaction()
                    .add(R.id.dish_list_fragment, newDishListFragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showDishAvailable(tableIndex: Int) {
        val intent = DishesAvailableActivity.intent(this, tableIndex)

        startActivityForResult(intent, REQUEST_DISH_AVAILABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        if (requestCode == REQUEST_DISH_AVAILABLE) {
            if (resultCode == Activity.RESULT_OK) {

                val newTable = data?.getSerializableExtra(AddDishDetailActivity.TABLE_TO_ADD_DISH) as? Table
                val newTableIndex = data?.getIntExtra(AddDishDetailActivity.TABLE_INDEX_TO_SEND, 0)

                dishListFragment?.let {
                    if (newTable != null) {
                        if (newTableIndex != null) {
                            tableIndex = newTableIndex
                            table = Tables.get(tableIndex)

                            it.showTable(newTable, newTableIndex)

                            Snackbar.make(
                                    dishListFragment.view,
                                    "New Dish added!",
                                    Snackbar.LENGTH_LONG)
                                    .show()
                        }
                    }
                }
            }
        }
        else if (requestCode == REQUEST_BILL) {
            if (resultCode == Activity.RESULT_OK) {

                val newTable = data?.getSerializableExtra(TableBillActivity.NEW_TABLE_DELETED) as? Table

                dishListFragment?.let {
                    if (newTable != null) {
                        Tables.get(tableIndex).replaceDishes(newTable.dishes)

                        it.showTable(newTable, tableIndex)

                        Snackbar.make(
                                dishListFragment.view,
                                "Dishes deleted!",
                                Snackbar.LENGTH_LONG)
                                .show()
                    }
                }
            }
        }
    }

    override fun updateTableToShow() {
        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        dishListFragment?.showTable(table, tableIndex)
    }

    override fun recordMovingTable(newTable: Table, newTableIndex: Int) {
        table = newTable
        tableIndex = newTableIndex
    }

    override fun showBill(table: Table?) {
        table?.let {
            startActivityForResult(TableBillActivity.newIntent(this, table), REQUEST_BILL)
        }
    }
}
