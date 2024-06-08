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
}