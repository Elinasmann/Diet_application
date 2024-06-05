package com.example.diet_application

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : Product)

    @Update
    suspend fun update(item: Product)

    @Delete
    suspend fun delete(item: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>



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