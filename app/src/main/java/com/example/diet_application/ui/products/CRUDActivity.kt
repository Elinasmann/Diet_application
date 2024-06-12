package com.example.diet_application.ui.products

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.diet_application.MainActivity
import com.example.diet_application.Product
import com.example.diet_application.R
import com.example.diet_application.StockProduct
import com.example.diet_application.databinding.CrudProductsBinding
import com.example.diet_application.databinding.SignInBinding
import com.example.diet_application.ui.sign_in.SignInActivity
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class CRUDActivity : AppCompatActivity() {
    private lateinit var binding: CrudProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // initializing view modal
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductsViewModel::class.java)

        binding = CrudProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var productID: Int = -1
        // getting data passed via an intent
        val getType = intent.getStringExtra("itemType")
        if (getType.equals("Edit")) {
            //   setting data to edit text.
            val title = intent.getStringExtra("itemTitle")
            val date = intent.getStringExtra("itemDate")
            val description = intent.getStringExtra("itemDescription")
            productID = intent.getIntExtra("itemId", -1)
            binding.productName.setText(title)
            binding.expirationDate.setText(date)
            if (description.toString().isNotEmpty())
                binding.description.setText(description)
        }

        // adding click listener to save button
        binding.buttonSave.setOnClickListener {
            val reg = ("(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[1,2])\\/(19|20)\\d{2}").toRegex()
            // getting title and desc from edit text
            val title = binding.productName.text.toString()
            val date = binding.expirationDate.text.toString()
            val description = binding.description.text.toString()
            // checking the type and then saving or updating the data
            if (!(title.isNotEmpty() && reg.matches(date))) {
                Toast.makeText(this, "Данные введены неправильно", Toast.LENGTH_LONG).show()
            } else {
                if (getType.equals("Edit")) {
                    val updatedProduct = StockProduct(productID, null, title, description, date)
                    viewModel.update(updatedProduct)
                    Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_LONG).show()
                } else {
                    // if the string is not empty > calling a add method to add data to room database
                    viewModel.add(StockProduct(0, null, title, description, date))
                    Toast.makeText(this, "$title добавлен", Toast.LENGTH_LONG).show()
                }

                val showContent = Intent(this, MainActivity::class.java)
                startActivity(showContent)
                this.finish()
            }
        }

        binding.backBtn.setOnClickListener {
            val showContent = Intent(this, MainActivity::class.java)
            startActivity(showContent)
            this.finish()
        }
    }
}