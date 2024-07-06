package com.example.diet_application.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.R
import com.example.diet_application.databinding.CartItemBinding
import com.example.diet_application.databinding.RecipeItemBinding
import com.example.diet_application.db.Product
import com.example.diet_application.db.ProductInCart


class CartAdapter (
    val context: Context,
    private val cartInterface: CartInterface
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    // creating a variable for all Recipe list
    private val allProductsInCart = ArrayList<ProductInCart>()
    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CartItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating layout file for each item of recycler view
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cart_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            //   setting data to item of recycler view
            binding.productTitleInCart.text = cartInterface.getProductTitle(allProductsInCart[position])
            binding.checkBuy.setChecked(allProductsInCart[position].checkBuy)
            binding.checkBuy.setOnCheckedChangeListener { _, isChecked ->
                //   calling a note click interface and  passing a position to it
                if (isChecked) {
                    cartInterface.onClickSet(allProductsInCart[position])
                } else {
                    cartInterface.onClickRemove(allProductsInCart[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allProductsInCart.size
    }

    // use to update   list
    fun updateList(newList: List<ProductInCart>) {
        allProductsInCart.clear()
        //   adding a new list to   all list
        allProductsInCart.addAll(newList)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}
interface CartInterface {
    // creating a method for click action on recycler view item for updating it
    fun onClickSet(item: ProductInCart)
    fun onClickRemove(item: ProductInCart)
    fun getProductTitle(item: ProductInCart): String
}