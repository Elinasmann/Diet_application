package com.example.diet_application.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    var text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }

    var userList : MutableLiveData<List<User>> = MutableLiveData()
    init {
        userList.value = UserData.getUsers()
    }
}