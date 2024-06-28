package com.example.diet_application.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.Repository
import com.example.diet_application.db.UserResults

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
}