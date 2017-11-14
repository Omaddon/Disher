package com.ammyt.disher.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ammyt.disher.*
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

class TableListActivity :
        AppCompatActivity(),
        TableListFragment.OnTableSelectedListener,
        DishListFragment.OnAddDishToTable {

    private var tableSelectedIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        if (DishesAvailable.getDishesAvailable().isEmpty()) {
            updateDishesAvailable()
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
            if (fragmentManager.findFragmentById(R.id.dish_list_fragment) == null) {
                val dishListFragment = DishListFragment.newInstance(Tables.get(tableSelectedIndex), tableSelectedIndex)
                fragmentManager.beginTransaction()
                        .add(R.id.dish_list_fragment, dishListFragment)
                        .commit()
            }
        }
    }

    private fun updateDishesAvailable() {

        // TODO avisar al usuario de que vamos a descargar datos
        async(UI) {
            val dishesAvailable: Deferred<List<Dish>?> = bg {
                downloadDishesAvailable()
            }

            val downloadedDishes = dishesAvailable.await()

            // TODO avisar al usuario de descarga éxito/fallo
            if (downloadedDishes != null) {
                // Ha ido bien. Avisamos al usuario de la descarga (snackBar)
            }
            else {
                // Error en la descarga
            }
        }
    }

    private fun downloadDishesAvailable(): List<Dish>? {
        try {

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
                val allergenList: MutableList<Allergen> = mutableListOf()

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
                        description
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
