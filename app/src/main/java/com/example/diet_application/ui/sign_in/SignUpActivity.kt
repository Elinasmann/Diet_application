package com.example.diet_application.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.diet_application.R
import com.example.diet_application.User
import com.example.diet_application.databinding.SignInBinding
import com.example.diet_application.databinding.SignUpBinding
import com.example.diet_application.ui.cart.CartViewModel
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val viewModel =
            ViewModelProvider(this).get(SignInViewModel::class.java)

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createBtn.setOnClickListener {

            //check is login exists to avoid exception
            //returns list of users if that users exist with the name passed as argument below
            viewModel.checkLoginExists(binding.login.text.toString().trim())
                .observe(this) {
                    if (it.isEmpty()) {
                        if (binding.login.text.toString().trim().isNotEmpty() &&
                            binding.password.text.toString().trim().isNotEmpty()
                        ) {
                            viewModel.insert(
                                User(
                                    id = 0,
                                    login = binding.login.text.toString().trim(),
                                    password = binding.password.text.toString().trim()
                                )
                            )
                            Toast.makeText(
                                this,
                                "Аккаунт создан",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            Handler(Looper.getMainLooper()).postDelayed({
                                val showContent = Intent(this, SignInActivity::class.java)
                                startActivity(showContent)
                                this.finish()
                            }, 1000)
                        } else {
                            Toast.makeText(
                                this,
                                "Все поля должны быть заполнены",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Такой пользователь уже существует",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

        }

        binding.backBtn.setOnClickListener {
            val showContent = Intent(this, SignInActivity::class.java)
            startActivity(showContent)
            this.finish()
        }
    }
}