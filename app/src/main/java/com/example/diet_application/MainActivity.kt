package com.example.diet_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.diet_application.databinding.ActivityMainBinding
import com.example.diet_application.db.Recipe
import com.example.diet_application.db.ScheduleOfExercise
import com.example.diet_application.db.ScheduleOfRecipe
import com.example.diet_application.db.UserResults
import com.example.diet_application.ui.sign_in.SignInViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        // calendar for the dates
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val currentDate = Date(year-1900, month, day)
        //first - create (if not create yet) schedule of recipes
        viewModel.getRecipeScheduleByUserId(CurrentUser.getId(), currentDate).observe(this) { list ->
            c.setTime(currentDate)
            if (list.isEmpty()) {
                val results = viewModel.getUserResultsByIdBlocking(CurrentUser.getId())
                val dbRecipesSize = viewModel.getCountOfRecipes()
                repeat (4) {
                    var randomIdRecipe: Int
                    var allCalories = 0.0F
                    do {
                        randomIdRecipe = (1..<dbRecipesSize).random()
                        val check = viewModel.checkIsRecipeInSchedule(randomIdRecipe, CurrentUser.getId(), c.time)
                        if (check == null) {
                            val recipe = viewModel.getRecipeById(randomIdRecipe)
                            if (recipe != null) {
                                if ((recipe.calories < results.calories / 2) && (allCalories + recipe.calories < results.calories * 1.1)) {
                                    viewModel.add(ScheduleOfRecipe(0, CurrentUser.getId(), recipe.id, null, c.time))
                                    allCalories += recipe.calories
                                }
                            }
                        }
                    } while (allCalories < results.calories)
                    c.add(Calendar.DATE, 1)
                }
                CurrentUser.setSchedule(true)
            }
        }
        //check was the new schedule create for cart items
        if (CurrentUser.getSchedule()) {
            viewModel.deleteCartByUserId(CurrentUser.getId())
            CurrentUser.setSchedule(false)
        }

        //second - create (if not create yet) schedule of exercises
        //id = 31 - walking, id = 32 - running
        if (viewModel.isUserNeedExercises(CurrentUser.getId())) {
            viewModel.getExerciseScheduleByUserId(CurrentUser.getId(), currentDate).observe(this) { list ->
                c.setTime(currentDate)
                if (list.isEmpty()) {
                    repeat (4) {
                        val exercises = viewModel.getExerciseIdListByType("Растяжка")
                        var randomIdExercise: Int
                        var i = 0
                        do {
                            randomIdExercise = (exercises[0]..<(exercises[0]+exercises.size)).random()
                            val check = viewModel.checkIsExerciseInSchedule(randomIdExercise, CurrentUser.getId(), c.time)
                            if (check == null) {
                                viewModel.add(ScheduleOfExercise(0, CurrentUser.getId(), randomIdExercise, 0.75F, c.time))
                                i++
                            }
                        } while (i < 5)
                        if (it != 3) {
                            val exercises1 = viewModel.getExerciseIdListByType("Кардио")
                            viewModel.add(ScheduleOfExercise(0, CurrentUser.getId(), 31, 10F, c.time))
                            i = 0
                            do {
                                randomIdExercise = (exercises1[0]+2..<(exercises1[0]+exercises1.size)).random()
                                val check = viewModel.checkIsExerciseInSchedule(randomIdExercise, CurrentUser.getId(), c.time)
                                if (check == null) {
                                    viewModel.add(ScheduleOfExercise(0, CurrentUser.getId(), randomIdExercise, 0.5F, c.time))
                                    i++
                                }
                            } while (i < 10)
                        } else {
                            val exercises1 = viewModel.getExerciseIdListByType("Силовые")
                            viewModel.add(ScheduleOfExercise(0, CurrentUser.getId(), 31, 10F, c.time))
                            viewModel.add(ScheduleOfExercise(0, CurrentUser.getId(), 32, 10F, c.time))
                            i = 0
                            do {
                                randomIdExercise = (exercises1[0]..<(exercises1[0]+exercises1.size)).random()
                                val check = viewModel.checkIsExerciseInSchedule(randomIdExercise, CurrentUser.getId(), c.time)
                                if (check == null) {
                                    viewModel.add(ScheduleOfExercise(0, CurrentUser.getId(), randomIdExercise, 0.5F, c.time))
                                    i++
                                }
                            } while (i < 10)
                        }
                        c.add(Calendar.DATE, 1)
                    }
                }
            }
        }


        //add navigation to the bottom of fragments
        val navView: BottomNavigationView = binding.navView

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.mobile_navigation)
        navController.graph=graph
        navView.setupWithNavController(navController)
    }
}