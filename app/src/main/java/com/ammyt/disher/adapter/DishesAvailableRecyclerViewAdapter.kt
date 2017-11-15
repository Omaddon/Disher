package com.ammyt.disher.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ammyt.disher.Allergen
import com.ammyt.disher.R
import com.ammyt.disher.model.Dish
import com.ammyt.disher.model.DishesAvailable


class DishesAvailableRecyclerViewAdapter :
        RecyclerView.Adapter<DishesAvailableRecyclerViewAdapter.DishesAvailableViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onBindViewHolder(holder: DishesAvailableViewHolder?, position: Int) {
        holder?.bindDishAvailable(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishesAvailableViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_dishes_available, parent, false)
        view.setOnClickListener(onClickListener)

        return DishesAvailableViewHolder(view)
    }

    override fun getItemCount(): Int {
        return DishesAvailable.count
    }


    inner class DishesAvailableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dishName = itemView.findViewById<TextView>(R.id.dish_available_name)
        private val dishImage = itemView.findViewById<ImageView>(R.id.dish_image)
        private val dishPrice = itemView.findViewById<TextView>(R.id.dish_price)

        private val allergenImageList = mutableListOf<ImageView>()


        fun bindDishAvailable(position: Int) {

            val dish: Dish = DishesAvailable.getDish(position)

            dishName.text = dish.name
            dishImage.setImageResource(dish.image)
            dishPrice.text = itemView.context.getString(R.string.dish_price, dish.price)

            allergenImageList.add(itemView.findViewById(R.id.allergen_celery_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_crustacean_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_egg_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_fish_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_gluten_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_lupin_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_milk_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_mollusc_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_mustard_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_peanut_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_sesamo_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_soya_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_sulphite_image))
            allergenImageList.add(itemView.findViewById(R.id.allergen_treenuts_image))

            val allergenListSize = dish.allergen?.size

            for (i in 0 until allergenImageList.size) {
                setDisableAllergen(allergenImageList[i])

                if (allergenListSize != null) {
                    for (allergenIndex in 0 until allergenListSize) {
                        val image = Allergen.imageToAllergen(allergenImageList[i].id)
                        val allergen = dish.allergen?.get(allergenIndex)

                        if (image == allergen) {
                            setEnableAllergen(allergenImageList[i])
                        }
                    }
                }
            }
        }

        private fun setDisableAllergen(allergen: ImageView) {
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)
            val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)

            allergen.setColorFilter(colorMatrixFilter)
            allergen.alpha = 0.25f
        }

        private fun setEnableAllergen(allergen: ImageView) {
            allergen.clearColorFilter()
            allergen.alpha = 1f
        }
    }
}