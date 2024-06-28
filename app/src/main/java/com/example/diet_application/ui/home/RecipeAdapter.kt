package com.example.diet_application.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.R
import com.example.diet_application.db.Recipe

class RecipeAdapter (
    val context: Context,
    private val RecipeClickInterface: RecipeClickInterface
) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    // creating a variable for all Recipe list
    private val allRecipes = ArrayList<Recipe>()

    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating an initializing all variables which added in layout file
        val recipe = itemView.findViewById<TextView>(R.id.recipe_title)
        val calories = itemView.findViewById<TextView>(R.id.calories_number)
        val proteins = itemView.findViewById<TextView>(R.id.proteins_number)
        val lipids = itemView.findViewById<TextView>(R.id.lipids_number)
        val carbohydrates = itemView.findViewById<TextView>(R.id.carbohydrates_number)
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
        //   setting data to item of recycler view
        holder.recipe.text = allRecipes[position].title
        holder.calories.text = allRecipes[position].calories.toString()
        holder.proteins.text = allRecipes[position].proteins.toString()
        holder.lipids.text = allRecipes[position].lipids.toString()
        holder.carbohydrates.text = allRecipes[position].carbohydrates.toString()
        //   adding click listener to   recycler view item
        holder.itemView.setOnClickListener {
            //   calling a note click interface and  passing a position to it
            RecipeClickInterface.onClick(allRecipes[position])
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allRecipes.size
    }

    // use to update   list
    fun updateList(newList: List<Recipe>) {
        //   clearing array list
        allRecipes.clear()
        val temp = newList.subList(6, 8)
        //   adding a new list to   all list
        allRecipes.addAll(temp)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}

interface RecipeClickInterface {
    // creating a method for click action on recycler view item for updating it
    fun onClick(item: Recipe)
}