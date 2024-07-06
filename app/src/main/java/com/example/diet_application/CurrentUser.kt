package com.example.diet_application

object CurrentUser {
    private var userId: Int = 0
    private var isCreateNewSchedule: Boolean = false

    fun setId(newId: Int) {
        this.userId = newId
    }
    fun getId(): Int {
        return this.userId
    }

    fun setSchedule(isCreate: Boolean) {
        this.isCreateNewSchedule = isCreate
    }
    fun getSchedule(): Boolean {
        return this.isCreateNewSchedule
    }
}