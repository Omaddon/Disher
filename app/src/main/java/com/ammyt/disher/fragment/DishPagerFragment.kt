package com.ammyt.disher.fragment


import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.ammyt.disher.R
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables


class DishPagerFragment : Fragment() {

    lateinit var root: View
    val pager by lazy { root.findViewById<ViewPager>(R.id.dish_view_pager) }
    var initialTableIndex: Int = 0

    private var dishPagerAdapter: DishPagerAdapter? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        inflater?.let {
            root = inflater.inflate(R.layout.fragment_dish_pager, container, false)

            initialTableIndex = arguments?.getInt(TABLE_INDEX_ARG) ?: 0

            val adapter = object : FragmentPagerAdapter(fragmentManager) {
                override fun getItem(position: Int): Fragment {
                    return dishPagerAdapter!!.fragmentToShow(Tables.get(position))
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.pager, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.previous -> {
                pager.currentItem--
                return true
            }
            R.id.next -> {
                pager.currentItem++
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val menuPrev = menu?.findItem(R.id.previous)
        val menuNext = menu?.findItem(R.id.next)

        menuPrev?.setEnabled(pager.currentItem > 0)
        menuNext?.setEnabled(pager.currentItem < Tables.count - 1)
    }

    private fun updateTableInfo(position: Int) {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            supportActionBar?.title = Tables.get(position).name
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is DishPagerAdapter) {
            dishPagerAdapter = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        dishPagerAdapter = null
    }

    fun moveToTable(position: Int) {
        pager.currentItem = position
    }

    interface DishPagerAdapter {
        fun fragmentToShow(table: Table): Fragment
    }
}
