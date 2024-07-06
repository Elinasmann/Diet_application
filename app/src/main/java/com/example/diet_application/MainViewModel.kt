package com.example.diet_application

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diet_application.db.Exercise
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.db.ProductInCart
import com.example.diet_application.db.ProductsOfRecipe
import com.example.diet_application.db.Recipe
import com.example.diet_application.db.ScheduleOfExercise
import com.example.diet_application.db.ScheduleOfRecipe
import com.example.diet_application.db.UserResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : Repository

    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
    }


    fun getUserResultsByIdBlocking(id: Int): UserResults = runBlocking {
        repository.getUserResultsByIdBlocking(id)
    }
    fun isUserNeedExercises(id: Int): Boolean = runBlocking {
        repository.isUserNeedExercises(id)
    }


    fun getRecipeById(id: Int): Recipe? = runBlocking {
        repository.getRecipeById(id)
    }
    fun checkIsRecipeInSchedule(recipeId: Int, userId: Int, date: Date): ScheduleOfRecipe? = runBlocking {
        repository.checkIsRecipeInSchedule(recipeId, userId, date)
    }
    fun getCountOfRecipes(): Int = runBlocking {
        repository.getCountOfRecipes()
    }
    fun add(item: ScheduleOfRecipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
    fun getRecipeScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfRecipe>> {
        return repository.getRecipeScheduleByUserId(id, date)
    }

    fun deleteCartByUserId(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCartByUserId(id)
    }


    fun getExerciseScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfExercise>> {
        return repository.getExerciseScheduleByUserId(id, date)
    }
    fun getExerciseIdListByType(type: String): List<Int> = runBlocking {
        repository.getExerciseIdListByType(type)
    }
    fun add(item: ScheduleOfExercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
    fun checkIsExerciseInSchedule(exerciseId: Int, userId: Int, date: Date): ScheduleOfExercise? = runBlocking {
        repository.checkIsExerciseInSchedule(exerciseId, userId, date)
    }
}