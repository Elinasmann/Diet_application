package com.example.diet_application.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.db.ProductsOfRecipe
import com.example.diet_application.db.Recipe
import com.example.diet_application.Repository
import com.example.diet_application.db.Exercise
import kotlinx.coroutines.runBlocking

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

    fun getRecipeById(id: Int): LiveData<Recipe> {
        return repository.getRecipeById(id)
    }
    fun getProductTitleById(id: Int): String = runBlocking {
        repository.getProductTitleById(id)
    }
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>> {
        return repository.getIngredientsByRecipeId(id)
    }
}