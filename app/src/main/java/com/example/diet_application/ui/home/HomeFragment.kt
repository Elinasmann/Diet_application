package com.example.diet_application.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.CurrentUser
import com.example.diet_application.db.Recipe
import com.example.diet_application.databinding.FragmentHomeBinding
import com.example.diet_application.ui.products.CRUDActivity

class HomeFragment : Fragment(), RecipeClickInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //  initializing
        // all  variables.
        var itemsRV: RecyclerView = binding.listOfRecipes

        //  setting layout
        // manager to  recycler view.
        itemsRV.layoutManager = LinearLayoutManager(requireContext())

        //  initializing  adapter class.
        val itemRVAdapter = RecipeAdapter(requireContext(),this)

        //  setting
        // adapter to  recycler view.
        itemsRV.adapter = itemRVAdapter

        //  calling all items method
        // from  view modal class to observer the changes on list.
        viewModel.allRecipes.observe(viewLifecycleOwner) { list ->
            list?.let {
                //  updating  list.
                itemRVAdapter.updateList(it)
            }
        }

        binding.buttonShowExercises.setOnClickListener {
            val intent = Intent (activity, ShowExercises::class.java)
            activity?.startActivity(intent)
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