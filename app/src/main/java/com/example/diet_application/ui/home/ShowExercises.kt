package com.example.diet_application.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.CurrentUser
import com.example.diet_application.MainActivity
import com.example.diet_application.databinding.ShowExercisesBinding
import com.example.diet_application.db.Exercise
import com.example.diet_application.db.ScheduleOfExercise
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class ShowExercises : AppCompatActivity(), GetMitutesInterface {
    private lateinit var binding: ShowExercisesBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = ShowExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemsExercises: RecyclerView = binding.listOfExercises
        itemsExercises.layoutManager = LinearLayoutManager(this)
        val exerciseAdapter = ExerciseAdapter(this, this)
        itemsExercises.adapter = exerciseAdapter


        val calendar: Calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        calendar.setTime(formatter.parse(intent.getStringExtra("date")))
        viewModel.getExerciseScheduleByUserId(CurrentUser.getId(), calendar.time).observe(this) {
            for (item in it) {
                val exercise = viewModel.getExerciseById(item.recipeId)
                exerciseAdapter.updateList(exercise)
            }
        }


        binding.visibility.setOnClickListener {
            if (binding.hintForExercises.visibility == GONE) {
                binding.hintForExercises.visibility = VISIBLE
            } else {
                binding.hintForExercises.visibility = GONE
            }
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun getMinutes(item: Exercise): Float? {
        val calendar: Calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        calendar.setTime(formatter.parse(intent.getStringExtra("date")))
        return viewModel.checkIsExerciseInSchedule(item.id, CurrentUser.getId(), calendar.time)?.minutes
    }
}
