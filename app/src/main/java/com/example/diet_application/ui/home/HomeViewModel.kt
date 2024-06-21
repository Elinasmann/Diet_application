package com.example.diet_application.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diet_application.MainDatabase
import com.example.diet_application.Product
import com.example.diet_application.ProductsOfRecipe
import com.example.diet_application.Recipe
import com.example.diet_application.Repository
import com.example.diet_application.StockProduct
import com.example.diet_application.User
import kotlinx.coroutines.runBlocking

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    val allRecipes : LiveData<List<Recipe>>
    private val repository : Repository

    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
        allRecipes = repository.allRecipes
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