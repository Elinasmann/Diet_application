package com.example.diet_application.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.R
import com.example.diet_application.databinding.ExerciseItemBinding
import com.example.diet_application.databinding.RecipeItemBinding
import com.example.diet_application.db.Exercise
import com.example.diet_application.db.ScheduleOfExercise
import java.util.Date

class ExerciseAdapter (
    val context: Context,
    private val getMinutesInterface: GetMitutesInterface
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    // creating a variable for all Exercise list
    private val allExercises = ArrayList<Exercise>()

    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ExerciseItemBinding.bind(itemView)
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
        with(holder) {
            binding.exerciseTitle.text = allExercises[position].title
            binding.minutes.text = getMinutesInterface.getMinutes(allExercises[position]).toString()
            binding.type.text = allExercises[position].type
            binding.exerciseDescription.text = allExercises[position].description
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allExercises.size
    }

    // use to update   list
    fun updateList(item: Exercise) {
        //   clearing array list
        allExercises.add(item)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}

interface GetMitutesInterface {
    fun getMinutes(item: Exercise): Float?
}