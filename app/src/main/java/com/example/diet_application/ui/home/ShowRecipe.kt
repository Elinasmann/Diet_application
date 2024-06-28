package com.example.diet_application.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.MainActivity
import com.example.diet_application.databinding.ShowRecipeBinding

class ShowRecipe : AppCompatActivity(), GetTitleProductInterface {
    private lateinit var binding: ShowRecipeBinding
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = ShowRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // getting data passed via an intent
        val id = intent.getIntExtra("itemId", 0)

        viewModel.getRecipeById(id).observe(this) {
            //   setting data to edit text.
            binding.showRecipeTitle.text = it.title
            binding.recipeDescription.text = it.description
            binding.showProteinsNumber.text = it.proteins.toString()
            binding.showLipidsNumber.text = it.lipids.toString()
            binding.showCarbohydratesNumber.text = it.carbohydrates.toString()
        }

        val ingredients: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.ingredientsOfRecipe.setLayoutManager(ingredients)
        ingredientsAdapter = IngredientsAdapter(this, this)
        binding.ingredientsOfRecipe.adapter = ingredientsAdapter
        
        viewModel.getIngredientsByRecipeId(id).observe(this) { list ->
            list?.let {
                //  updating  list.
                ingredientsAdapter.updateList(it)
            }
        }

        binding.backBtn.setOnClickListener {
            val showContent = Intent(this, MainActivity::class.java)
            startActivity(showContent)
            this.finish()
        }
    }

    override fun getTitleProduct(id: Int): String {
        return viewModel.getProductTitleById(id)
    }
}