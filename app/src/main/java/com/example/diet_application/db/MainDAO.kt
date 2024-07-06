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
import com.example.diet_application.db.ScheduleOfExercise
import com.example.diet_application.db.ScheduleOfRecipe
import com.example.diet_application.db.StockProduct
import com.example.diet_application.db.User
import com.example.diet_application.db.UserResults
import java.util.Date

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : StockProduct)
    @Update
    suspend fun update(item: StockProduct)
    @Delete
    suspend fun delete(item: StockProduct)
    @Query("SELECT * FROM stock_products WHERE user_id LIKE :id ORDER BY id ASC")
    fun getAllStockProducts(id: Int): LiveData<List<StockProduct>>
    @Query("SELECT * FROM products WHERE title LIKE '%' || :name || '%'")
    fun getProductsByTitle(name: String): LiveData<List<Product>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : Product)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: User)
    @Query("SELECT * FROM users WHERE login LIKE :login")
    fun checkLoginExists(login: String): LiveData<List<User>>
    @Query("SELECT * FROM users WHERE id LIKE :id")
    suspend fun getUserById(id: Int): User
    @Query("SELECT * FROM users WHERE login LIKE :login AND password LIKE :password")
    fun checkIfUserIsRegistered(login: String, password: String): LiveData<List<User>>
    @Query("SELECT login FROM users WHERE id LIKE :id")
    fun getUserLoginById(id: Int): LiveData<String>
    @Query("SELECT * FROM user_results WHERE user_id LIKE :id")
    fun getUserResultsById(id: Int): LiveData<UserResults>
    @Query("SELECT * FROM user_results WHERE user_id LIKE :id")
    suspend fun getUserResultsByIdBlocking(id: Int): UserResults
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: UserResults)
    @Query("SELECT do_exercises FROM users WHERE id LIKE :id")
    suspend fun isUserNeedExercises(id: Int): Boolean
    @Update
    suspend fun update(item: User)
    @Delete
    suspend fun delete(item: UserResults)


    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<List<Recipe>>
    @Query("SELECT * FROM recipes WHERE id LIKE :id")
    suspend fun getRecipeById(id: Int): Recipe?
    @Query("SELECT * FROM recipes WHERE id LIKE :id")
    fun getRecipeByIdNormal(id: Int): LiveData<Recipe>
    @Query("SELECT * FROM schedule_of_recipes WHERE recipe_id LIKE :recipeId AND user_id LIKE :userId AND date LIKE :date")
    suspend fun checkIsRecipeInSchedule(recipeId: Int, userId: Int, date: Date): ScheduleOfRecipe?
    @Query("SELECT * FROM products_of_recipe WHERE recipe_id LIKE :id")
    fun getIngredientsByRecipeId(id: Int): LiveData<List<ProductsOfRecipe>>
    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getCountOfRecipes(): Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ScheduleOfRecipe)
    @Query("SELECT * FROM schedule_of_recipes WHERE user_id LIKE :id AND date LIKE :date ORDER BY id ASC")
    fun getRecipeScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfRecipe>>

    @Query("SELECT * FROM schedule_of_exercises WHERE user_id LIKE :id AND date LIKE :date ORDER BY id ASC")
    fun getExerciseScheduleByUserId(id: Int, date: Date): LiveData<List<ScheduleOfExercise>>
    @Query("SELECT * FROM exercises WHERE id LIKE :id")
    suspend fun getExerciseById(id: Int): Exercise
    @Query("SELECT id FROM exercises WHERE type LIKE :type ORDER BY id ASC")
    suspend fun getExerciseIdListByType(type: String): List<Int>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ScheduleOfExercise)
    @Query("SELECT * FROM schedule_of_exercises WHERE exercise_id LIKE :exerciseId AND user_id LIKE :userId AND date LIKE :date")
    suspend fun checkIsExerciseInSchedule(exerciseId: Int, userId: Int, date: Date): ScheduleOfExercise?


    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>
    @Query("SELECT title FROM products WHERE id LIKE :id")
    suspend fun getProductTitleById(id: Int): String
    @Query("SELECT product_id FROM products_of_recipe WHERE recipe_id LIKE :id")
    fun getProductOfRecipeByRecipeId(id: Int): LiveData<List<Int>>
    @Query("SELECT * FROM products_in_cart WHERE user_id LIKE :id ORDER BY id ASC")
    fun getAllProductsInCartByUserId(id: Int): LiveData<List<ProductInCart>>
    @Query("SELECT * FROM products_in_cart WHERE user_id LIKE :id ORDER BY id ASC")
    suspend fun getAllProductsInCartByUserIdCoroutine(id: Int): List<ProductInCart>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ProductInCart)
    @Update
    suspend fun update(item: ProductInCart)
    @Query("DELETE FROM products_in_cart WHERE user_id LIKE :id")
    suspend fun deleteCartByUserId(id: Int)
    @Query("SELECT * FROM products_in_cart WHERE product_id LIKE :productId AND user_id LIKE :userId")
    suspend fun checkIsProductInCart(productId: Int, userId: Int): List<ProductInCart>
}