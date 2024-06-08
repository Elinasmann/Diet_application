package com.example.diet_application.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_application.Product
import com.example.diet_application.R
import com.example.diet_application.StockProduct

class ProductAdapter(
    val context: Context,
    private val productClickDeleteInterface: ProductClickDeleteInterface,
    private val productClickInterface: ProductClickInterface
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // creating a variable for all product list
    private val allProducts = ArrayList<StockProduct>()

    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating an initializing all variables which added in layout file
        val product = itemView.findViewById<TextView>(R.id.product_name)
        val date = itemView.findViewById<TextView>(R.id.expiration_date)
        val delete = itemView.findViewById<ImageView>(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating layout file for each item of recycler view
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   setting data to item of recycler view
        holder.product.text = allProducts[position].title
        holder.date.text = allProducts[position].expirationDate
        //   adding click listener to   delete image view icon
        holder.delete.setOnClickListener {
            //   calling a interface and  passing a position to it
            productClickDeleteInterface.onDeleteIconClick(allProducts[position])
        }

        //   adding click listener to   recycler view item
        holder.itemView.setOnClickListener {
            //   calling a note click interface and  passing a position to it
            productClickInterface.onClick(allProducts[position])
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allProducts.size
    }

    // use to update   list
    fun updateList(newList: List<StockProduct>) {
        //   clearing array list
        allProducts.clear()
        //   adding a new list to   all list
        allProducts.addAll(newList)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}

interface ProductClickDeleteInterface {
    // creating a method for click action on delete image view
    fun onDeleteIconClick(item: StockProduct)
}

interface ProductClickInterface {
    // creating a method for click action on recycler view item for updating it
    fun onClick(item: StockProduct)
}