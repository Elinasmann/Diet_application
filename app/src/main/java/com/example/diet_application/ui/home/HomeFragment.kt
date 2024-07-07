package com.example.diet_application.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.CurrentUser
import com.example.diet_application.databinding.FragmentHomeBinding
import com.example.diet_application.db.Recipe
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(), RecipeClickInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recipeItems: RecyclerView = binding.listOfRecipes
        recipeItems.layoutManager = LinearLayoutManager(requireContext())
        val recipeItemsAdapter = RecipeAdapter(requireContext(),this)
        recipeItems.adapter = recipeItemsAdapter


        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (viewModel.checkIsRecipeScheduleExist(CurrentUser.getId(), calendar.time) == null) {
            calendar.add(Calendar.DATE, 1)
        }
        binding.showDate.text = formatter.format(calendar.time)

        viewModel.getRecipeScheduleByUserId(CurrentUser.getId(), calendar.time).observe(viewLifecycleOwner) {
            for (item in it) {
                item.recipeId?.let { it1 ->
                    viewModel.getRecipeByIdNormal(it1).observe(viewLifecycleOwner) { recipe ->
                        recipeItemsAdapter.update(recipe)
                    }
                }
            }
        }


        binding.nextDate.setOnClickListener {
            calendar.add(Calendar.DATE, 1)
            if (viewModel.checkIsRecipeScheduleExist(CurrentUser.getId(), calendar.time) != null) {
                viewModel.getRecipeScheduleByUserId(CurrentUser.getId(), calendar.time)
                    .observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.showDate.text = formatter.format(calendar.time)
                            recipeItemsAdapter.clearList()
                            for (item in it) {
                                item.recipeId?.let { it1 ->
                                    viewModel.getRecipeByIdNormal(it1)
                                        .observe(viewLifecycleOwner) { recipe ->
                                            recipeItemsAdapter.update(recipe)
                                        }
                                }
                            }
                        }
                    }
            } else {
                calendar.add(Calendar.DATE, -1)
            }
        }
        binding.previousDate.setOnClickListener {
            calendar.add(Calendar.DATE, -1)
            if (viewModel.checkIsRecipeScheduleExist(CurrentUser.getId(), calendar.time) != null) {
                viewModel.getRecipeScheduleByUserId(CurrentUser.getId(), calendar.time)
                    .observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.showDate.text = formatter.format(calendar.time)
                            recipeItemsAdapter.clearList()
                            for (item in it) {
                                item.recipeId?.let { it1 ->
                                    viewModel.getRecipeByIdNormal(it1)
                                        .observe(viewLifecycleOwner) { recipe ->
                                            recipeItemsAdapter.update(recipe)
                                        }
                                }
                            }
                        }
                    }
            } else {
                calendar.add(Calendar.DATE, 1)
            }
        }

        if (viewModel.isUserNeedExercises(CurrentUser.getId())) {
            binding.buttonShowExercises.setOnClickListener {
                val intent = Intent(activity, ShowExercises::class.java)
                intent.putExtra("date", binding.showDate.text.toString())
                activity?.startActivity(intent)
            }
        } else {
            binding.buttonShowExercises.visibility = GONE
        }

        return root
    }

    override fun onClick(item: Recipe) {
        // opening a new intent and passing a data to it.
        val intent = Intent (activity, ShowRecipe::class.java)
        intent.putExtra("itemId", item.id)
        activity?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}