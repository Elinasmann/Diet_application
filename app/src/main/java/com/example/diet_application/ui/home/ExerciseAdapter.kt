package com.example.diet_application.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.R
import com.example.diet_application.db.Exercise

class ExerciseAdapter (val context: Context) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    // creating a variable for all Exercise list
    private val allExercises = ArrayList<Exercise>()

    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating an initializing all variables which added in layout file
        val exercise = itemView.findViewById<TextView>(R.id.exercise_title)
        val description = itemView.findViewById<TextView>(R.id.exercise_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating layout file for each item of recycler view
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.exercise_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   setting data to item of recycler view
        holder.exercise.text = allExercises[position].title
        holder.description.text = allExercises[position].description
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allExercises.size
    }

    // use to update   list
    fun updateList(newList: List<Exercise>) {
        //   clearing array list
        allExercises.clear()
        val temp = newList.subList(10, 14)
        //   adding a new list to   all list
        allExercises.addAll(temp)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}