package com.ammyt.disher.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ViewSwitcher
import com.ammyt.disher.*
import com.ammyt.disher.Allergen.drawableDish
import com.ammyt.disher.Allergen.toAllergen
import com.ammyt.disher.fragment.DishListFragment
import com.ammyt.disher.fragment.TableListFragment
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.DishesAvailable
import com.ammyt.disher.model.Table
import com.ammyt.disher.model.Tables
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONObject
import java.net.URL
import java.util.*

// TODO cuenta de la mesa!!
class TableListActivity :
        AppCompatActivity(),
        TableListFragment.OnTableSelectedListener,
        DishListFragment.OnAddDishToTable, DishListFragment.OnDeviceRotate{

    private lateinit var viewSwitcher: ViewSwitcher

    companion object {
        private var tableSelected: Table? = null
        private var tableSelectedIndex: Int = 0
    }

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        VIEW(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        viewSwitcher = findViewById(R.id.view_switcher)
        viewSwitcher.setInAnimation(this, android.R.anim.fade_in)
        viewSwitcher.setOutAnimation(this, android.R.anim.fade_out)

        if (DishesAvailable.getDishesAvailable().isEmpty()) {
            updateDishesAvailable()
        }
        else {
            viewSwitcher.displayedChild = VIEW_INDEX.VIEW.index
        }

        if (findViewById<View>(R.id.table_list_fragment) != null) {
            if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                val tableListFragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.table_list_fragment, tableListFragment)
                        .commit()
            }
        }

        if (findViewById<View>(R.id.dish_list_fragment) != null) {
            val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

            tableSelected = Tables.get(tableSelectedIndex)

            if (dishListFragment == null) {
                val newDishListFragment = DishListFragment.newInstance(tableSelected, tableSelectedIndex)
                fragmentManager.beginTransaction()
                        .add(R.id.dish_list_fragment, newDishListFragment)
                        .commit()
            }
        }
    }

    private fun updateDishesAvailable() {

        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index

        async(UI) {
            val dishesAvailable: Deferred<List<Dish>?> = bg {
                downloadDishesAvailable()
            }

            val downloadedDishes = dishesAvailable.await()

            if (downloadedDishes != null) {
                viewSwitcher.displayedChild = VIEW_INDEX.VIEW.index

                val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment
                val viewToSnackbar: View

                if (dishListFragment != null) {
                    viewToSnackbar = dishListFragment.view
                }
                else {
                    viewToSnackbar = findViewById<View>(android.R.id.content)
                }

                Snackbar.make(
                        viewToSnackbar,
                        "Dishes list downloaded!",
                        Snackbar.LENGTH_LONG)
                        .show()

            }
            else {
                AlertDialog.Builder(this@TableListActivity)
                        .setTitle("Error")
                        .setMessage("Error downloading dishes from API.")
                        .setPositiveButton("Retry?", { dialog, which ->
                            dialog.dismiss()
                            updateDishesAvailable()
                        })
                        .setNegativeButton("Exit", { dialog, which ->
                            finish()
                        })
                        .show()
            }
        }
    }

    private fun downloadDishesAvailable(): List<Dish>? {
        try {

            // Just for teacher review
            Thread.sleep(1500)

            val url = URL(dishesURL)
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

            val jsonRoot = JSONObject(jsonString)
            val dishList = jsonRoot.getJSONArray("dishes")

            for (dishIndex in 0..dishList.length() - 1 ) {

                val jsonDish = dishList.getJSONObject(dishIndex)

                val name = jsonDish.getString("name")
                val jsonImage = jsonDish.getInt("image")
                val price = jsonDish.getDouble("price").toFloat()
                val description = jsonDish.getString("description")

                val jsonAllergen = jsonDish.getJSONArray("allergen")
                val allergenList: MutableList<Allergen.AllergenList> = mutableListOf()

                for (allergenIndex in 0..jsonAllergen.length() - 1) {
                    val allergen = toAllergen(jsonAllergen.getInt(allergenIndex))
                    allergen?.let { allergenList.add(it) }
                }

                val image = drawableDish(jsonImage)

                val newDish = Dish(
                        name,
                        image,
                        allergenList,
                        price,
                        description,
                        null
                )

                DishesAvailable.addDish(newDish)
            }

            return DishesAvailable.getDishesAvailable()
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    override fun onTableSelected(table: Table?, position: Int) {
        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        if (dishListFragment == null) {
            startActivity(DishListActivity.intent(this, table, position))
        }
        else {
            table?.let {
                tableSelectedIndex = position
                tableSelected = Tables.get(tableSelectedIndex)
                dishListFragment.showTable(it, position)
            }

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
                            tableSelected = newTable
                            tableSelectedIndex = tableIndex

                            it.showTable(newTable, tableIndex)

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
    }

    override fun updateTableToShow() {
        val dishListFragment = fragmentManager.findFragmentById(R.id.dish_list_fragment) as? DishListFragment

        dishListFragment?.showTable(tableSelected, tableSelectedIndex)
    }

    override fun recordMovingTable(newTable: Table, newTableIndex: Int) {
        tableSelected = newTable
        tableSelectedIndex = newTableIndex
    }
}
