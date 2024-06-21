package com.example.diet_application.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.ProductsOfRecipe
import com.example.diet_application.R
import com.example.diet_application.Recipe
import com.example.diet_application.StockProduct
import com.example.diet_application.databinding.IngredientsOfRecipeBinding
import com.example.diet_application.databinding.ShowRecipeBinding
import com.example.diet_application.ui.products.ProductClickInterface

class IngredientsAdapter (
    val context: Context,
    private val getTitleProductInterface: GetTitleProductInterface
) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private val allIngredients = ArrayList<ProductsOfRecipe>()

    // creating a view holder class
    inner class ViewHolder(val binding: IngredientsOfRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating layout file for each item of recycler view
        val binding = IngredientsOfRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(allIngredients[position]){
                binding.ingredient.text = getTitleProductInterface.getTitleProduct(this.productId)
                binding.amountOfIngredient.text = this.measureAmount.toString()
                binding.titleAmountOfIngredient.text = this.measureTitle
            }
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allIngredients.size
    }

    // use to update   list
    fun updateList(newList: List<ProductsOfRecipe>) {
        //   clearing array list
        allIngredients.clear()
        //   adding a new list to   all list
        allIngredients.addAll(newList)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}

interface GetTitleProductInterface {
    // creating a method for click action on recycler view item for updating it
    fun getTitleProduct(id: Int): String
}