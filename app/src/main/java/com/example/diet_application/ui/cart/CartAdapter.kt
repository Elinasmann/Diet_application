package com.example.diet_application.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.R
import com.example.diet_application.db.Product
import com.example.diet_application.db.ProductInCart


class CartAdapter (
    val context: Context,
    private val CartClickInterface: CartClickInterface
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    // creating a variable for all Recipe list
    private val allProductsInCart = ArrayList<ProductInCart>()
    private val allProducts = ArrayList<Product>()
    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating an initializing all variables which added in layout file
        val product_title = itemView.findViewById<TextView>(R.id.product_title_in_cart)
        val check = itemView.findViewById<TextView>(R.id.check_cart)
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
        //   setting data to item of recycler view
        holder.product_title.text = allProducts[position].title
        //   adding click listener to   recycler view item
        holder.check.setOnClickListener {
            //   calling a note click interface and  passing a position to it
            CartClickInterface.onClick(allProductsInCart[position])
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allProductsInCart.size
    }

    // use to update   list
    fun updateList(newList: List<ProductInCart>) {
        //   clearing array list
        allProductsInCart.clear()
        //   adding a new list to   all list
        allProductsInCart.addAll(newList)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}
interface CartClickInterface {
    // creating a method for click action on recycler view item for updating it
    fun onClick(item: ProductInCart)
}