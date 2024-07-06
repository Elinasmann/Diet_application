package com.example.diet_application.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.Repository
import com.example.diet_application.db.StockProduct
import com.example.diet_application.db.User
import com.example.diet_application.db.UserResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
    }

    fun getUserLoginById(id: Int): LiveData<String> {
        return repository.getUserLoginById(id)
    }
    fun getUserResultsById(id: Int): LiveData<UserResults> {
        return repository.getUserResultsById(id)
    }
    fun getUserById(id: Int): User = runBlocking {
        repository.getUserById(id)
    }
    fun update(item: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }

    fun getUserResultsByIdBlocking(id: Int): UserResults = runBlocking {
        repository.getUserResultsByIdBlocking(id)
    }
    fun delete(item: UserResults) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }
}