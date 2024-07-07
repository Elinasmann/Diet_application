package com.example.diet_application.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.db.ProductsOfRecipe
import com.example.diet_application.db.Recipe
import com.example.diet_application.Repository
import com.example.diet_application.db.Exercise
import com.example.diet_application.db.ScheduleOfExercise
import com.example.diet_application.db.ScheduleOfRecipe
import com.example.diet_application.db.StockProduct
import com.example.diet_application.db.UserResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    val allRecipes : LiveData<List<Recipe>>
    val allExercises : LiveData<List<Exercise>>
    private val repository : Repository

    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
        allRecipes = repository.allRecipes
        allExercises = repository.allExercises
    }

    fun isUserNeedExercises(id: Int): Boolean = runBlocking {
        repository.isUserNeedExercises(id)
    }


    fun getRecipeById(id: Int): Recipe? = runBlocking {
        repository.getRecipeById(id)
    }
    fun getRecipeByIdNormal(id: Int): LiveData<Recipe> {
        return repository.getRecipeByIdNormal(id)
    }
    fun getProductTitleById(id: Int): String = runBlocking {
        repository.getProductTitleById(id)
    }
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>> {
        return repository.getIngredientsByRecipeId(id)
    }
    fun add(item: ScheduleOfRecipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
    fun getRecipeScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfRecipe>> {
        return repository.getRecipeScheduleByUserId(id, date)
    }
    fun checkIsRecipeScheduleExist(id: Int, date: Date): ScheduleOfRecipe? = runBlocking {
        repository.checkIsRecipeScheduleExist(id, date)
    }


    fun getExerciseScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfExercise>> {
        return repository.getExerciseScheduleByUserId(id, date)
    }
    fun getExerciseById(id: Int): Exercise = runBlocking {
        repository.getExerciseById(id)
    }
    fun checkIsExerciseInSchedule(exerciseId: Int, userId: Int, date: Date): ScheduleOfExercise? = runBlocking {
        repository.checkIsExerciseInSchedule(exerciseId, userId, date)
    }
}