package com.example.diet_application.ui.sign_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.Repository
import com.example.diet_application.db.User
import com.example.diet_application.db.UserResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignInViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    
    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
    }
    
    fun insert(item: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
    fun insert(item: UserResults) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun checkIfUserIsRegistered(login: String, password: String): LiveData<List<User>> {
        return repository.checkIfUserIsRegistered(login, password)
    }
    fun checkLoginExists(login: String): LiveData<List<User>> {
        return repository.checkLoginExists(login)
    }
    fun getUserResultsById(id: Int): LiveData<UserResults> {
        return repository.getUserResultsById(id)
    }
}