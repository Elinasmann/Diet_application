package com.example.diet_application.ui.cart

data class User (
    var name: String = "",
    var description: String = "",
    var box: Boolean = false
)

object UserData {

    fun getUsers() = listOf(
        User("Marilyn Monroe", "American actress, singer, model"),
        User("Abraham Lincoln", "US President during American civil war"),
        User("Mother Teresa", "Macedonian Catholic missionary nun"),
        User("John F. Kennedy ", "US President 1961 – 1963")
    )
}