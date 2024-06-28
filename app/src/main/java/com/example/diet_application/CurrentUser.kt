package com.example.diet_application

object CurrentUser {
    var userId: Int = 0

    fun setId(newId: Int) {
        this.userId = newId
    }

    fun getId(): Int {
        return this.userId
    }
}