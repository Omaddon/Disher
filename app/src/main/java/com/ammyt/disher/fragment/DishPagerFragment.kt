package com.ammyt.disher.fragment


import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ammyt.disher.R
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables


class DishPagerFragment : Fragment() {

    lateinit var root: View
    val pager by lazy { root.findViewById<ViewPager>(R.id.dish_view_pager) }
    var initialTableIndex: Int = 0

    private var getItemPagerAdapter: GetItemPagerAdapter? = null

    companion object {
        val TABLE_INDEX_ARG = "TABLE_INDEX_ARG"

        fun newInstance(tableIndex: Int): DishPagerFragment {
            val arguments = Bundle()
            arguments.putInt(TABLE_INDEX_ARG, tableIndex)

            val dishPagerFragment = DishPagerFragment()
            dishPagerFragment.arguments = arguments

            return dishPagerFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        inflater.let {
            root = it!!.inflate(R.layout.fragment_dish_pager, container, false)

            initialTableIndex = arguments?.getInt(TABLE_INDEX_ARG) ?: 0

            val adapter = object : FragmentPagerAdapter(fragmentManager) {
                override fun getItem(position: Int): Fragment {
                    return getItemPagerAdapter!!.fragmentToShow(Tables.get(position))
                }

                override fun getCount(): Int {
                    return Tables.count
                }

                override fun getPageTitle(position: Int): CharSequence {
                    return Tables.get(position).name
                }
            }

            pager.adapter = adapter

            pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    updateTableInfo(position)
                }
            })

            pager.currentItem = initialTableIndex
            updateTableInfo(initialTableIndex)
        }

        return root
    }

    private fun updateTableInfo(position: Int) {
        // TODO actualizar el nombre de la mesa en la futura supportActionBar
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is GetItemPagerAdapter) {
            getItemPagerAdapter = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        getItemPagerAdapter = null
    }

    fun moveToTable(position: Int) {
        pager.currentItem = position
    }

    interface GetItemPagerAdapter {
        fun fragmentToShow(table: Table): Fragment
    }


}
