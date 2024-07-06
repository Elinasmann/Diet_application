package com.example.diet_application.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.db.Product
import com.example.diet_application.db.ProductInCart
import com.example.diet_application.Repository
import com.example.diet_application.db.ProductsOfRecipe
import com.example.diet_application.db.ScheduleOfRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

class CartViewModel (application: Application) : AndroidViewModel(application) {
    val allProducts : LiveData<List<Product>>
    private val repository : Repository
    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
        allProducts = repository.allProducts
    }
    fun update(item: ProductInCart) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }
    fun add(item: ProductInCart) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun getProductTitleById(id: Int): String = runBlocking {
        repository.getProductTitleById(id)
    }
    fun getAllProductsInCartByUserId(id: Int): LiveData<List<ProductInCart>> {
        return repository.getAllProductsInCartByUserId(id)
    }
    fun getAllProductsInCartByUserIdCoroutine(id: Int): List<ProductInCart> = runBlocking {
        repository.getAllProductsInCartByUserIdCoroutine(id)
    }
    fun getRecipeScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfRecipe>> {
        return repository.getRecipeScheduleByUserId(id, date)
    }
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>> {
        return repository.getIngredientsByRecipeId(id)
    }
    fun checkIsProductInCart(productId: Int, userId: Int): List<ProductInCart> = runBlocking {
        repository.checkIsProductInCart(productId, userId)
    }
}