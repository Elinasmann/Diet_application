package com.example.diet_application.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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

        binding.buttonCreate.setOnClickListener {

            //check is login exists to avoid exception
            //returns list of users if that users exist with the name passed as argument below
            viewModel.checkLoginExists(binding.login.text.toString().trim())
                .observe(this) {
                    if (it.isEmpty()) {
                        val reg = ("([0-9]*[.])?[0-9]+").toRegex()

                        if (binding.login.text.toString().trim().isNotEmpty() &&
                            binding.password.text.toString().trim().isNotEmpty() &&
                            binding.age.text.toString().trim().isNotEmpty() &&
                            binding.weight.text.toString().trim().isNotEmpty() &&
                            binding.height.text.toString().trim().isNotEmpty() &&
                            binding.physicalActivityRatio.text.toString().trim().isNotEmpty() &&
                            reg.matches(binding.weight.text.toString()) &&
                            reg.matches(binding.height.text.toString()) &&
                            reg.matches(binding.physicalActivityRatio.text.toString())
                        ) {
                            if (binding.age.text.toString().toInt() < 16 || binding.age.text.toString().toInt() > 80) {
                                Toast.makeText(
                                    this,
                                    "Возраст введен неправильно. Введите число от 16 до 80",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.weight.text.toString().toFloat() < 40 || binding.weight.text.toString().toFloat() > 200) {
                                Toast.makeText(
                                    this,
                                    "Вес введен неправильно. Введите число от 40 до 200",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.height.text.toString().toFloat() < 140 || binding.height.text.toString().toFloat() > 220) {
                                Toast.makeText(
                                    this,
                                    "Рост введен неправильно. Введите число от 140 до 220",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.physicalActivityRatio.text.toString().toFloat() < 1 || binding.physicalActivityRatio.text.toString().toFloat() > 3) {
                                Toast.makeText(
                                    this,
                                    "КФА введен неправильно. Введите число от 1 до 3",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val idGender = binding.radio.checkedRadioButtonId

                                viewModel.insert(
                                    User(
                                        id = 0,
                                        login = binding.login.text.toString().trim(),
                                        password = binding.password.text.toString().trim(),
                                        gender = if (idGender == 0) {
                                            "male"
                                        } else {
                                            "female"
                                        },
                                        age = binding.age.text.toString().toInt(),
                                        weight = binding.weight.text.toString().toFloat(),
                                        height = binding.height.text.toString().toFloat(),
                                        physicalActivityRatio = binding.physicalActivityRatio.text.toString()
                                            .toFloat(),
                                        doExercises = binding.checkIfExercises.isChecked
                                    )
                                )

                                Toast.makeText(
                                    this,
                                    "Аккаунт создан",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Handler(Looper.getMainLooper()).postDelayed({
                                    val showContent = Intent(this, SignInActivity::class.java)
                                    startActivity(showContent)
                                    this.finish()
                                }, 1000)
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Все поля должны быть заполнены. Анкета не должна содержать букв",
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