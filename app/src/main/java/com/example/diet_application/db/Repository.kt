package com.example.diet_application

import androidx.lifecycle.LiveData

class Repository(private val Dao: MainDao) {

    val allProducts: LiveData<List<Product>> = Dao.getAllProducts()

    suspend fun insert(item: Product) {
        Dao.insert(item)
    }

    suspend fun delete(item: Product) {
        Dao.delete(item)
    }

    suspend fun update(item: Product) {
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