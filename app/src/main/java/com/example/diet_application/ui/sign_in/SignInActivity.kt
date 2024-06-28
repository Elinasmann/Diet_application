package com.example.diet_application.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diet_application.MainActivity
import com.example.diet_application.R
import com.example.diet_application.databinding.ActivityMainBinding
import com.example.diet_application.databinding.SignInBinding
import com.example.diet_application.db.UserResults

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
//        this.deleteDatabase("diet_database")
        val viewModel =
            ViewModelProvider(this).get(SignInViewModel::class.java)

        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLogin.setOnClickListener {

            if (binding.login.text.toString().trim().isNotEmpty() &&
                binding.password.text.toString().trim().isNotEmpty()
            ) {

                //check if user is registered in the app or not
                viewModel.checkIfUserIsRegistered(
                    binding.login.text.toString().trim(),
                    binding.password.text.toString().trim()
                )
                    .observe(this) { loginDetailsList ->
                        //fetch data and returns list of users if registered
                        //check if user already exist in database
                        if (loginDetailsList.isNotEmpty()) {
                            viewModel.getUserResultsById(loginDetailsList[0].id).observe(this) {results ->
                                if (results == null) {
                                    var mifSanGeorFormula = 9.99*loginDetailsList[0].weight + 6.25*loginDetailsList[0].height + 4.92*loginDetailsList[0].age
                                    mifSanGeorFormula = if (loginDetailsList[0].gender == "male") {
                                        mifSanGeorFormula + 5
                                    } else {
                                        mifSanGeorFormula - 161
                                    }
                                    mifSanGeorFormula *= loginDetailsList[0].physicalActivityRatio

                                    val heightM = loginDetailsList[0].height/100
                                    val imt = loginDetailsList[0].weight/(heightM*heightM)
                                    when {
                                        imt < 18.5 -> mifSanGeorFormula *= 1.1
                                        imt > 25 -> mifSanGeorFormula *= 0.9
                                    }

                                    viewModel.insert(
                                        UserResults(
                                            userId = loginDetailsList[0].id,
                                            calories = mifSanGeorFormula.toFloat(),
                                            proteins = (mifSanGeorFormula*0.15/4).toFloat(),
                                            lipids = (mifSanGeorFormula*0.2/9).toFloat(),
                                            carbohydrates = (mifSanGeorFormula*0.65/4).toFloat()
                                        )
                                    )
                                }
                            }

                            Toast.makeText(
                                this,
                                "Вход выполнен успешно",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("id", loginDetailsList[0].id)
                            startActivity(intent)
                            this.finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Такого пользователя не существует",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Все поля должны быть заполнены",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //sign up user, if does not exist already
        binding.signUp.setOnClickListener {
            val showContent = Intent(this, SignUpActivity::class.java)
            startActivity(showContent)
            this.finish()
        }
    }
}