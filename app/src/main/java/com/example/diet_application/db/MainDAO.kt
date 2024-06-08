package com.example.diet_application

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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
}