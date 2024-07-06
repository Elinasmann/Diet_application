package com.example.diet_application.ui.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diet_application.db.MainDatabase
import com.example.diet_application.Repository
import com.example.diet_application.db.Product
import com.example.diet_application.db.ProductInCart
import com.example.diet_application.db.StockProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : Repository

    init {
        val dao = MainDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
    }


    fun delete(item: StockProduct) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }
    fun update(item: StockProduct) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }
    fun add(item: StockProduct) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun getAllStockProducts(id: Int): LiveData<List<StockProduct>> {
        return repository.getAllStockProducts(id)
    }
    fun getProductsByTitle(name: String): LiveData<List<Product>> {
        return repository.getProductsByTitle(name)
    }
    fun add(item: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
}