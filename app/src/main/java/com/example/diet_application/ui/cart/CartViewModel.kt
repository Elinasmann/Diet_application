package com.example.diet_application.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet_application.MainDatabase
import com.example.diet_application.Product
import com.example.diet_application.ProductInCart
import com.example.diet_application.Recipe
import com.example.diet_application.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartViewModel (application: Application) : AndroidViewModel(application) {
    val allProducts : LiveData<List<Product>>
    private val repository : Repository
    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
        allProducts = repository.allProducts
    }
    fun update(item: ProductInCart) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }

    fun insert(item: ProductInCart) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun getProductTitleById(id: Int): String = runBlocking {
        repository.getProductTitleById(id)
    }

    fun getAllProductsInCartByUserId(id: Int): LiveData<List<ProductInCart>> {
        return repository.getAllProductsInCartByUserId(id)
    }
}