package com.ammyt.disher.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ammyt.disher.R
import com.ammyt.disher.fragment.TableListFragment
import com.ammyt.disher.model.Table

class TableListActivity : AppCompatActivity(), TableListFragment.OnTableSelectedListener {

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
    }

    override fun onTableSelected(table: Table?, position: Int) {
        // TODO: navigation to DishesList
    }
}
