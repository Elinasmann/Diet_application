package com.example.diet_application.ui.products

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.Product
import com.example.diet_application.StockProduct
import com.example.diet_application.databinding.FragmentProductsBinding
import com.example.diet_application.ui.cart.CartFragment
import com.example.diet_application.ui.cart.CartViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductsFragment : Fragment(), ProductClickInterface, ProductClickDeleteInterface {

    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var viewModel : ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(ProductsViewModel::class.java)
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //  initializing
        // all  variables.
        var itemsRV: RecyclerView = binding.listOfProducts
        var addFAB: FloatingActionButton = binding.buttonCreate

        //  setting layout
        // manager to  recycler view.
        itemsRV.layoutManager = LinearLayoutManager(requireContext())

        //  initializing  adapter class.
        val itemRVAdapter = ProductAdapter(requireContext(), this, this)

        //  setting
        // adapter to  recycler view.
        itemsRV.adapter = itemRVAdapter

        //  calling all items method
        // from  view modal class to observer the changes on list.
        viewModel.allProducts.observe(viewLifecycleOwner) { list ->
            list?.let {
                //  updating  list.
                itemRVAdapter.updateList(it)
            }
        }
        addFAB.setOnClickListener {
            // adding a click listener for fab button
            // and opening a new intent to add a new item.
            val intent = Intent (activity, CRUDActivity::class.java)
            intent.putExtra("itemType", "Add")
            activity?.startActivity(intent)
        }

        return root
    }

    override fun onClick(item: StockProduct) {
        // opening a new intent and passing a data to it.
        val intent = Intent (activity, CRUDActivity::class.java)
        intent.putExtra("itemType", "Edit")
        intent.putExtra("itemId", item.id)
        intent.putExtra("itemTitle", item.title)
        intent.putExtra("itemDate", item.expirationDate)
        intent.putExtra("itemDescription", item.description)
        activity?.startActivity(intent)
    }

    override fun onDeleteIconClick(item: StockProduct) {
        // in on item click method we are calling delete
        // method from  view modal to delete  not.
        viewModel.delete(item)
        // displaying a toast message
        Toast.makeText(requireContext(), "${item.title} удален", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}