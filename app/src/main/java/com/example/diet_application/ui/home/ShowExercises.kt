package com.example.diet_application.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.CurrentUser
import com.example.diet_application.MainActivity
import com.example.diet_application.databinding.ShowExercisesBinding


class ShowExercises : AppCompatActivity() {
    private lateinit var binding: ShowExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = ShowExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  initializing
        // all  variables.
        var itemsRV: RecyclerView = binding.listOfExercises

        //  setting layout
        // manager to  recycler view.
        itemsRV.layoutManager = LinearLayoutManager(this)

        //  initializing  adapter class.
        val exerciseAdapter = ExerciseAdapter(this)

        //  setting
        // adapter to  recycler view.
        itemsRV.adapter = exerciseAdapter

        //  calling all items method
        // from  view modal class to observer the changes on list.
        viewModel.allExercises.observe(this) { list ->
            list?.let {
                //  updating  list.
                exerciseAdapter.updateList(it)
            }
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}