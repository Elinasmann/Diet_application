package com.example.diet_application

import androidx.lifecycle.LiveData
import com.example.diet_application.db.Exercise
import com.example.diet_application.db.Product
import com.example.diet_application.db.ProductInCart
import com.example.diet_application.db.ProductsOfRecipe
import com.example.diet_application.db.Recipe
import com.example.diet_application.db.ScheduleOfExercise
import com.example.diet_application.db.ScheduleOfRecipe
import com.example.diet_application.db.StockProduct
import com.example.diet_application.db.User
import com.example.diet_application.db.UserResults
import java.util.Date

class Repository(private val Dao: MainDao) {

    fun getAllStockProducts(id: Int): LiveData<List<StockProduct>> {
        return Dao.getAllStockProducts(id)
    }
    suspend fun insert(item: StockProduct) {
        Dao.insert(item)
    }
    suspend fun delete(item: StockProduct) {
        Dao.delete(item)
    }
    suspend fun update(item: StockProduct) {
        Dao.update(item)
    }
    fun getProductsByTitle(name: String): LiveData<List<Product>> {
        return Dao.getProductsByTitle(name)
    }
    suspend fun insert(item: Product) {
        Dao.insert(item)
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
    fun getUserLoginById(id: Int): LiveData<String> {
        return Dao.getUserLoginById(id)
    }
    suspend fun getUserById(id: Int): User {
        return Dao.getUserById(id)
    }
    fun getUserResultsById(id: Int): LiveData<UserResults> {
        return Dao.getUserResultsById(id)
    }
    suspend fun getUserResultsByIdBlocking(id: Int): UserResults {
        return Dao.getUserResultsByIdBlocking(id)
    }
    suspend fun insert(item: UserResults) {
        Dao.insert(item)
    }
    suspend fun isUserNeedExercises(id: Int): Boolean {
        return Dao.isUserNeedExercises(id)
    }
    suspend fun update(item: User) {
        Dao.update(item)
    }
    suspend fun delete(item: UserResults) {
        Dao.delete(item)
    }


    val allExercises: LiveData<List<Exercise>> = Dao.getAllExercises()
    val allRecipes: LiveData<List<Recipe>> = Dao.getAllRecipes()
    suspend fun getRecipeById(id: Int): Recipe? {
        return Dao.getRecipeById(id)
    }
    fun getRecipeByIdNormal(id: Int): LiveData<Recipe> {
        return Dao.getRecipeByIdNormal(id)
    }
    suspend fun checkIsRecipeInSchedule(recipeId: Int, userId: Int, date: Date): ScheduleOfRecipe? {
        return Dao.checkIsRecipeInSchedule(recipeId, userId, date)
    }
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>> {
        return Dao.getIngredientsByRecipeId(id)
    }
    suspend fun getCountOfRecipes(): Int {
        return Dao.getCountOfRecipes()
    }
    suspend fun insert(item: ScheduleOfRecipe) {
        Dao.insert(item)
    }
    fun getRecipeScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfRecipe>> {
        return Dao.getRecipeScheduleByUserId(id, date)
    }

    fun getExerciseScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfExercise>> {
        return Dao.getExerciseScheduleByUserId(id, date)
    }
    suspend fun getExerciseById(id: Int): Exercise {
        return Dao.getExerciseById(id)
    }
    suspend fun getExerciseIdListByType(type: String): List<Int> {
        return Dao.getExerciseIdListByType(type)
    }
    suspend fun insert(item: ScheduleOfExercise) {
        Dao.insert(item)
    }
    suspend fun checkIsExerciseInSchedule(exerciseId: Int, userId: Int, date: Date): ScheduleOfExercise? {
        return Dao.checkIsExerciseInSchedule(exerciseId, userId, date)
    }


    val allProducts: LiveData<List<Product>> = Dao.getAllProducts()
    fun getAllProductsInCartByUserId(id: Int): LiveData<List<ProductInCart>> {
        return Dao.getAllProductsInCartByUserId(id)
    }
    suspend fun getAllProductsInCartByUserIdCoroutine(id: Int): List<ProductInCart> {
        return Dao.getAllProductsInCartByUserIdCoroutine(id)
    }
    suspend fun insert(item: ProductInCart) {
        Dao.insert(item)
    }
    suspend fun update(item: ProductInCart) {
        Dao.update(item)
    }
    suspend fun deleteCartByUserId(id: Int) {
        Dao.deleteCartByUserId(id)
    }
    suspend fun getProductTitleById(id: Int): String {
        return Dao.getProductTitleById(id)
    }
    fun getProductOfRecipeByRecipeId(id: Int): LiveData<List<Int>> {
        return Dao.getProductOfRecipeByRecipeId(id)
    }
    suspend fun checkIsProductInCart(productId: Int, userId: Int): List<ProductInCart> {
        return Dao.checkIsProductInCart(productId, userId)
    }
}