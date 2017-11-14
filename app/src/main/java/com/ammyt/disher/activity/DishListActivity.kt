package com.ammyt.disher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ammyt.disher.R
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.model.Table

class DishListActivity : AppCompatActivity(), DishListFragment.OnAddDishToTable {

    companion object {
        private val TABLE_EXTRA = "TABLE_EXTRA"
        private val TABLEINDEX_EXTRA = "TABLEINDEX_EXTRA"
        val REQUEST_DISH_AVAILABLE = 1

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

        if (fragmentManager.findFragmentById(R.id.dish_list_fragment) == null) {
            val table = intent.getSerializableExtra(TABLE_EXTRA) as? Table
            val tableIndex = intent.getIntExtra(TABLEINDEX_EXTRA, 0)

            val dishListFragment = DishListFragment.newInstance(table, tableIndex)
            fragmentManager.beginTransaction()
                    .add(R.id.dish_list_fragment, dishListFragment)
                    .commit()
        }
    }

    override fun showDishAvailable(tableIndex: Int) {
        val intent = DishesAvailableActivity.intent(this, tableIndex)

        startActivityForResult(intent, REQUEST_DISH_AVAILABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_DISH_AVAILABLE) {
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
