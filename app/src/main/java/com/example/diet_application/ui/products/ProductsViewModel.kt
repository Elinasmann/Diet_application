package com.example.diet_application.ui.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet_application.MainDatabase
import com.example.diet_application.Product
import com.example.diet_application.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel (application: Application) : AndroidViewModel(application) {

    val allProducts : LiveData<List<Product>>
    private val repository : Repository

    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
        allProducts = repository.allProducts
    }


    fun deleteNote (item: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }

    fun updateNote(item: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }

    fun addNote(item: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
}