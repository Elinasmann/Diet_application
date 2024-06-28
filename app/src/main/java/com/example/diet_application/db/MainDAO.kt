package com.example.diet_application

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diet_application.db.Exercise
import com.example.diet_application.db.Product
import com.example.diet_application.db.ProductInCart
import com.example.diet_application.db.ProductsOfRecipe
import com.example.diet_application.db.Recipe
import com.example.diet_application.db.StockProduct
import com.example.diet_application.db.User
import com.example.diet_application.db.UserResults

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : StockProduct)
    @Update
    suspend fun update(item: StockProduct)
    @Delete
    suspend fun delete(item: StockProduct)
    @Query("SELECT * FROM stock_products ORDER BY id ASC")
    fun getAllStockProducts(): LiveData<List<StockProduct>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: User)
    @Query("SELECT * FROM users WHERE login LIKE :login")
    fun checkLoginExists(login: String): LiveData<List<User>>
    //return list of all users having same login and password
    @Query("SELECT * FROM users WHERE login LIKE :login AND " + "password LIKE :password")
    fun checkIfUserIsRegistered(
        login: String,
        password: String
    ): LiveData<List<User>>
    @Query("SELECT login FROM users WHERE id LIKE :id")
    fun getUserLoginById(id: Int): LiveData<String>
    @Query("SELECT * FROM user_results WHERE user_id LIKE :id")
    fun getUserResultsById(id: Int): LiveData<UserResults>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: UserResults)


    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<List<Recipe>>
    @Query("SELECT * FROM recipes WHERE id LIKE :id")
    fun getRecipeById(id: Int): LiveData<Recipe>
    @Query("SELECT * FROM products_of_recipe WHERE recipe_id LIKE :id")
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>>


    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>
    @Query("SELECT title FROM products WHERE id LIKE :id")
    suspend fun getProductTitleById(id: Int): String
    @Query("SELECT product_id FROM products_of_recipe WHERE recipe_id LIKE :id")
    fun getProductOfRecipeByRecipeId(id: Int): LiveData<List<Int>>
    @Query("SELECT * FROM products_in_cart WHERE user_id LIKE :id")
    fun getAllProductsInCartByUserId(id: Int): LiveData<List<ProductInCart>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ProductInCart)
    @Update
    suspend fun update(item: ProductInCart)
}