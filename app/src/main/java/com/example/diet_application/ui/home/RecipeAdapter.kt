package com.example.diet_application.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.R
import com.example.diet_application.databinding.RecipeItemBinding
import com.example.diet_application.db.Recipe

class RecipeAdapter (
    val context: Context,
    private val recipeClickInterface: RecipeClickInterface
) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    // creating a variable for all Recipe list
    private val allRecipes = ArrayList<Recipe>()

    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecipeItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating layout file for each item of recycler view
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recipe_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            //   setting data to item of recycler view
            binding.recipeTitle.text = allRecipes[position].title
            binding.caloriesNumber.text = allRecipes[position].calories.toString()
            binding.proteinsNumber.text = allRecipes[position].proteins.toString()
            binding.lipidsNumber.text = allRecipes[position].lipids.toString()
            binding.carbohydratesNumber.text = allRecipes[position].carbohydrates.toString()
            //   adding click listener to   recycler view item
            holder.itemView.setOnClickListener {
                //   calling a note click interface and  passing a position to it
                recipeClickInterface.onClick(allRecipes[position])
            }
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allRecipes.size
    }

    // use to update   list
    fun update(item: Recipe) {
        //   clearing array list
        //   adding a new list to   all list
        allRecipes.add(item)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
    fun clearList() {
        allRecipes.clear()
    }
}

interface RecipeClickInterface {
    // creating a method for click action on recycler view item for updating it
    fun onClick(item: Recipe)
}