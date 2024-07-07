package com.example.diet_application.ui.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.diet_application.CurrentUser
import com.example.diet_application.MainActivity
import com.example.diet_application.databinding.UpdateUserParametersBinding
import com.example.diet_application.db.User
import com.example.diet_application.db.UserResults
import com.example.diet_application.ui.sign_in.SignInActivity

class UpdateParams : AppCompatActivity() {
    private lateinit var binding: UpdateUserParametersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val viewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding = UpdateUserParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonUpdate.setOnClickListener {
            val reg = ("([0-9]*[.])?[0-9]+").toRegex()
            if (binding.age.text.toString().trim().isNotEmpty() &&
                binding.weight.text.toString().trim().isNotEmpty() &&
                binding.height.text.toString().trim().isNotEmpty() &&
                binding.physicalActivityRatio.text.toString().trim().isNotEmpty() &&
                reg.matches(binding.weight.text.toString()) &&
                reg.matches(binding.height.text.toString()) &&
                reg.matches(binding.physicalActivityRatio.text.toString())
            ) {
                if (binding.age.text.toString().toInt() < 18 || binding.age.text.toString().toInt() > 80) {
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
                    if (imt < 16 || imt > 31) {
                        Toast.makeText(
                            this,
                            "Ваше здоровье требует медицинского вмешательства. Возвращайтесь к нам позже",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val user = viewModel.getUserById(CurrentUser.getId())
                        viewModel.update(User(
                            user.id,
                            user.login,
                            user.password,
                            user.gender,
                            age = binding.age.text.toString().toInt(),
                            weight = binding.weight.text.toString().toFloat(),
                            height = binding.height.text.toString().toFloat(),
                            physicalActivityRatio = binding.physicalActivityRatio.text.toString().toFloat(),
                            doExercises = binding.checkIfExercises.isChecked))
                        val results = viewModel.getUserResultsByIdBlocking(user.id)
                        viewModel.delete(results)


                        Toast.makeText(
                            this,
                            "Параметры обновлены",
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
        }


        binding.visibility.setOnClickListener {
            if (binding.kfaHint.visibility == View.GONE) {
                binding.kfaHint.visibility = View.VISIBLE
            } else {
                binding.kfaHint.visibility = View.GONE
            }
        }

        binding.backBtn.setOnClickListener {
            val showContent = Intent(this, MainActivity::class.java)
            startActivity(showContent)
            this.finish()
        }
    }
}