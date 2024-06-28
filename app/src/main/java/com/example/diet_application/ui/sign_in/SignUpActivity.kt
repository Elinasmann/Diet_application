package com.example.diet_application.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.diet_application.db.User
import com.example.diet_application.databinding.SignUpBinding
import java.lang.Math.pow

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
                            if (binding.login.text.length > 20 || binding.password.text.length > 20
                                || binding.login.text.length < 4 || binding.password.text.length < 4) {
                                Toast.makeText(
                                    this,
                                    "Логин и пароль должны быть от 4 до 20 символов каждый",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.age.text.toString().toInt() < 18 || binding.age.text.toString().toInt() > 80) {
                                Toast.makeText(
                                    this,
                                    "Возраст введен неправильно. Введите число от 18 до 80",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.weight.text.toString().toFloat() < 35 || binding.weight.text.toString().toFloat() > 145) {
                                Toast.makeText(
                                    this,
                                    "Вес введен неправильно. Введите число от 35 до 145",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.height.text.toString().toFloat() < 145 || binding.height.text.toString().toFloat() > 210) {
                                Toast.makeText(
                                    this,
                                    "Рост введен неправильно. Введите число от 145 до 210",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (binding.physicalActivityRatio.text.toString().toFloat() < 1.2 || binding.physicalActivityRatio.text.toString().toFloat() > 1.9) {
                                Toast.makeText(
                                    this,
                                    "КФА введен неправильно. Введите число от 1.2 до 1.9",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val heightM = binding.height.text.toString().toFloat()/100
                                val imt = binding.weight.text.toString().toFloat()/(heightM*heightM)
                                if (imt < 16 || imt > 30) {
                                    Toast.makeText(
                                        this,
                                        "Ваше здоровье требует медицинского вмешательства. Возвращайтесь к нам позже",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.insert(
                                        User(
                                            id = 0,
                                            login = binding.login.text.toString().trim(),
                                            password = binding.password.text.toString().trim(),
                                            gender = if (binding.Male.isChecked) {
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