package com.ammyt.disher.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ammyt.disher.R
import com.ammyt.disher.adapter.DishRecyclerViewAdapter
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.Table


class DishListFragment : Fragment() {

    lateinit var root: View
    lateinit var dishRecyclerView: RecyclerView

    companion object {
        private val TABLE_ARG = "TABLE_ARG"

        fun newInstance(table: Table): DishListFragment {
            val argument = Bundle()
            argument.putSerializable(TABLE_ARG, table)

            val dishListFragment = DishListFragment()
            dishListFragment.arguments = argument

            return dishListFragment
        }
    }

    var table: Table? = null
        set(value) {
            field = value

            value?.let {
                dishes = value.dishes
            }
        }

    var dishes: MutableList<Dish>? = null
        set(value) {
            field = value

            if (value != null) {

                val adapter = DishRecyclerViewAdapter(value)
                dishRecyclerView.adapter = adapter

                adapter.onClickListener = View.OnClickListener { v: View? ->
                    // TODO navegar al detalle del plato o no hacer nada (opcional)
                }

                table?.dishes = value
            }
            else {
                updateDishList()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        inflater.let {
            root = it!!.inflate(R.layout.fragment_dish_list, container, false)

            dishRecyclerView = root.findViewById(R.id.dish_recycler_view)
            dishRecyclerView.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.recycler_columns))
            dishRecyclerView.itemAnimator = DefaultItemAnimator()

            if (arguments != null) {
                table = arguments.getSerializable(TABLE_ARG) as? Table
            }
        }

        return root
    }

    private fun updateDishList() {

        // MOCK data
        var mockDishes = mutableListOf<Dish>()

        for (i in 0..7) {
            val dish = Dish(
                    "Test ${i}",
                    i,
                    null,
                    42.2f
            )

            mockDishes.add(dish)
        }

        dishes = mockDishes
    }
}
