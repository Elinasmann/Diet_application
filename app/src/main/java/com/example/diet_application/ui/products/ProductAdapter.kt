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

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    // creating a variable for all product list
    private val allNotes = ArrayList<Product>()

    // creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating an initializing all variables which added in layout file
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
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
        holder.noteTV.setText(allNotes.get(position).title)
        holder.dateTV.setText("Last Updated : " + allNotes.get(position).measure)
        //   adding click listener to   delete image view icon
        holder.deleteIV.setOnClickListener {
            //   calling a interface and  passing a position to it
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        //   adding click listener to   recycler view item
        holder.itemView.setOnClickListener {
            //   calling a note click interface and  passing a position to it
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        // returning   list size
        return allNotes.size
    }

    // use to update   list
    fun updateList(newList: List<Product>) {
        //   clearing array list
        allNotes.clear()
        //   adding a new list to   all list
        allNotes.addAll(newList)
        //   calling notify data change method to notify   adapter
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface {
    // creating a method for click action on delete image view
    fun onDeleteIconClick(note: Product)
}

interface NoteClickInterface {
    // creating a method for click action on recycler view item for updating it
    fun onNoteClick(note: Product)
}