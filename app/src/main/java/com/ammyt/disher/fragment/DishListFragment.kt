package com.ammyt.disher.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.ammyt.disher.R
import com.ammyt.disher.adapter.DishRecyclerViewAdapter
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables


class DishListFragment : Fragment() {

    private lateinit var root: View
    private lateinit var dishRecyclerView: RecyclerView
    private var menu: Menu? = null

    private var tableIndex: Int = 0

    private var onAddDishToTable: OnAddDishToTable? = null
    private var onDeviceRotate: OnDeviceRotate? = null
    private var onShowBill: OnShowBill? = null
    private var onShowDishDetail: OnShowDishDetail? = null

    companion object {
        private val TABLE_ARG = "TABLE_ARG"
        private val TABLEINDEX_ARG = "TABLEINDEX_ARG"

        fun newInstance(table: Table?, tableIndex: Int): DishListFragment {
            val argument = Bundle()
            argument.putSerializable(TABLE_ARG, table)
            argument.putInt(TABLEINDEX_ARG, tableIndex)

            val dishListFragment = DishListFragment()
            dishListFragment.arguments = argument

            return dishListFragment
        }
    }

    private var table: Table? = null
        set(value) {
            field = value

            value?.let {
                dishes = value.dishes
            }
        }

    private var dishes: MutableList<Dish>? = null
        set(value) {
            field = value

            if (value != null) {
                table?.dishes = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        inflater?.let {
            root = it.inflate(R.layout.fragment_dish_list, container, false)

            dishRecyclerView = root.findViewById(R.id.dish_recycler_view)
            dishRecyclerView.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.recycler_columns))
            dishRecyclerView.itemAnimator = DefaultItemAnimator()

            if (arguments != null) {
                table = arguments.getSerializable(TABLE_ARG) as? Table
                tableIndex = arguments.getInt(TABLEINDEX_ARG)
            }

            val adapter = DishRecyclerViewAdapter(dishes)
            dishRecyclerView.adapter = adapter

            setListenerToAdapter(adapter)

            root.findViewById<FloatingActionButton>(R.id.show_dishes_available)?.setOnClickListener { v: View? ->
                onAddDishToTable?.showDishAvailable(tableIndex)
            }

            updateToolbarDishName()
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        this.menu = menu

        inflater?.inflate(R.menu.pager, menu)
        onPrepareShowMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.next -> {
                tableIndex = tableIndex + 1
                val newTable = Tables.get(tableIndex)
                showTable(newTable, tableIndex)

                onDeviceRotate?.recordMovingTable(newTable, tableIndex)

                return true
            }
            R.id.previous -> {
                tableIndex = tableIndex - 1
                val newTable = Tables.get(tableIndex)
                showTable(newTable, tableIndex)

                onDeviceRotate?.recordMovingTable(newTable, tableIndex)

                return true
            }
            R.id.bill -> {
                onShowBill?.showBill(table)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onPrepareShowMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val menuPrev = menu?.findItem(R.id.previous)
        val menuNext = menu?.findItem(R.id.next)

        menuPrev?.setEnabled(tableIndex > 0)
        menuNext?.setEnabled(tableIndex < Tables.count - 1)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnAddDishToTable) {
            onAddDishToTable = context
        }

        if (context is OnDeviceRotate) {
            onDeviceRotate = context
        }

        if (context is OnShowBill) {
            onShowBill = context
        }

        if (context is OnShowDishDetail) {
            onShowDishDetail = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        onAddDishToTable = null
        onDeviceRotate = null
        onShowBill = null
        onShowDishDetail = null
    }

    override fun onResume() {
        super.onResume()

        onDeviceRotate?.updateTableToShow()
    }

    fun showTable(newTable: Table?, newTableIndex: Int) {
        newTable?.let {
            table = newTable
            tableIndex = newTableIndex

            val adapter = DishRecyclerViewAdapter(newTable.dishes)
            dishRecyclerView.adapter = adapter

            setListenerToAdapter(adapter)

            updateToolbarDishName()
            onPrepareShowMenu(menu)
        }
    }

    private fun setListenerToAdapter(adapter: DishRecyclerViewAdapter) {
        adapter.onClickListener = View.OnClickListener { v: View? ->
            val position = dishRecyclerView.getChildAdapterPosition(v)
            val dish = dishes?.get(position)

            onShowDishDetail?.showDishDetail(dish)
        }
    }

    private fun updateToolbarDishName() {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            supportActionBar?.title = table?.name
        }
    }

    interface OnAddDishToTable {
        fun showDishAvailable(tableIndex: Int)
    }

    interface OnDeviceRotate {
        fun updateTableToShow()
        fun recordMovingTable(newTable: Table, newTableIndex: Int)
    }

    interface OnShowBill {
        fun showBill(table: Table?)
    }

    interface OnShowDishDetail {
        fun showDishDetail(dish: Dish?)
    }
}
