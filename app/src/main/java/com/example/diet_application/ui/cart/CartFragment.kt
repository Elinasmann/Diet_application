package com.example.diet_application.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.CurrentUser
import com.example.diet_application.db.ProductInCart
import com.example.diet_application.databinding.FragmentCartBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CartFragment : Fragment(), CartInterface {

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

        val cartItems: RecyclerView = binding.listOfProducts
        cartItems.layoutManager = LinearLayoutManager(requireContext())
        val cartAdapter = CartAdapter(requireContext(),this)
        cartItems.adapter = cartAdapter


        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (viewModel.checkIsRecipeScheduleExist(CurrentUser.getId(), calendar.time) == null) {
            calendar.add(Calendar.DATE, 1)
        }

        val list = viewModel.getAllProductsInCartByUserIdCoroutine(CurrentUser.getId())
        if (list.isEmpty()) {
            repeat (4) {
                viewModel.getRecipeScheduleByUserId(CurrentUser.getId(), calendar.time).observe(viewLifecycleOwner) {
                    for (item in it) {
                        val recipeId = item.recipeId
                        if (recipeId != null) {
                            viewModel.getIngredientsByRecipeId(recipeId).observe(viewLifecycleOwner) { products ->
                                for (product in products) {
                                    val productCart = viewModel.checkIsProductInCart(product.productId, CurrentUser.getId())
                                    if (productCart.isEmpty()) {
                                        viewModel.add(ProductInCart(0, CurrentUser.getId(), product.productId))
                                    }
                                }
                            }
                        }
                    }
                }
                calendar.add(Calendar.DATE, 1)
            }
        } else {
            cartAdapter.updateList(list)
        }


        return root
    }

    override fun onClickSet(item: ProductInCart) {
        viewModel.update(ProductInCart(item.id, item.userId, item.productId, true))
    }
    override fun onClickRemove(item: ProductInCart) {
        viewModel.update(ProductInCart(item.id, item.userId, item.productId, false))
    }
    override fun getProductTitle(item: ProductInCart): String {
        return viewModel.getProductTitleById(item.productId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}