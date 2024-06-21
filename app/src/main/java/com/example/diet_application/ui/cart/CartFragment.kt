package com.example.diet_application.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.MainDatabase
import com.example.diet_application.ProductInCart
import com.example.diet_application.databinding.FragmentCartBinding
import com.example.diet_application.databinding.FragmentHomeBinding



class CartFragment : Fragment(), CartClickInterface {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var viewModel : CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CartViewModel::class.java)
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //  initializing
        // all  variables.
        var itemsRV: RecyclerView = binding.rvList

        //  setting layout
        // manager to  recycler view.
        itemsRV.layoutManager = LinearLayoutManager(requireContext())

        //  initializing  adapter class.
        val itemRVAdapter = CartAdapter(requireContext(),this)

        //  setting
        // adapter to  recycler view.
        itemsRV.adapter = itemRVAdapter

        //  calling all items method
        // from  view modal class to observer the changes on list.
        viewModel.getAllProductsInCartByUserId(1).observe(viewLifecycleOwner) { list ->
            list?.let {
                //  updating  list.
                itemRVAdapter.updateList(it)
            }
        }

        return root
    }

    override fun onClick(item: ProductInCart) {
        // opening a new intent and passing a data to it.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}