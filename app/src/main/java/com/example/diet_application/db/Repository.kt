package com.example.diet_application

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class Repository(private val Dao: MainDao) {

    val allStockProducts: LiveData<List<StockProduct>> = Dao.getAllStockProducts()

    suspend fun insert(item: StockProduct) {
        Dao.insert(item)
    }

    suspend fun delete(item: StockProduct) {
        Dao.delete(item)
    }

    suspend fun update(item: StockProduct) {
        Dao.update(item)
    }



    suspend fun insert(item: User) {
        Dao.insert(item)
    }

    fun checkLoginExists(login: String): LiveData<List<User>> {
        return Dao.checkLoginExists(login)
    }

    fun checkIfUserIsRegistered(
        login: String,
        password: String
    ): LiveData<List<User>> {
        return Dao.checkIfUserIsRegistered(login, password)
    }


    val allRecipes: LiveData<List<Recipe>> = Dao.getAllRecipes()
    fun getRecipeById(id: Int): LiveData<Recipe> {
        return Dao.getRecipeById(id)
    }
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>> {
        return Dao.getIngredientsByRecipeId(id)
    }


    val allProducts: LiveData<List<Product>> = Dao.getAllProducts()
    fun getAllProductsInCartByUserId(id: Int): LiveData<List<ProductInCart>> {
        return Dao.getAllProductsInCartByUserId(id)
    }
    suspend fun insert(item: ProductInCart) {
        Dao.insert(item)
    }
    suspend fun update(item: ProductInCart) {
        Dao.update(item)
    }
    suspend fun getProductTitleById(id: Int): String {
        return Dao.getProductTitleById(id)
    }
    fun getProductOfRecipeByRecipeId(id: Int): LiveData<List<Int>> {
        return Dao.getProductOfRecipeByRecipeId(id)
    }
}