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
import com.example.diet_application.databinding.FragmentProductsBinding
import com.example.diet_application.ui.cart.CartFragment
import com.example.diet_application.ui.cart.CartViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductsFragment : Fragment(), NoteClickInterface, NoteClickDeleteInterface {

    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var viewModal : ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(ProductsViewModel::class.java)
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //  creating a variable
        // for  recycler view, exit text, button and viewmodel.

        //  initializing
        // all  variables.
        var notesRV: RecyclerView = binding.notesRV
        var addFAB: FloatingActionButton = binding.idFAB

        //  setting layout
        // manager to  recycler view.
        notesRV.layoutManager = LinearLayoutManager(requireContext())

        //  initializing  adapter class.
        val noteRVAdapter = NoteRVAdapter(requireContext(), this, this)

        //  setting
        // adapter to  recycler view.
        notesRV.adapter = noteRVAdapter

        //  calling all notes method
        // from  view modal class to observer the changes on list.
        viewModal.allProducts.observe(viewLifecycleOwner) { list ->
            list?.let {
                //  updating  list.
                noteRVAdapter.updateList(it)
            }
        }
        addFAB.setOnClickListener {
            // adding a click listener for fab button
            // and opening a new intent to add a new note.
            activity?.let{
                val intent = Intent (it, CRUDActivity::class.java)
                it.startActivity(intent)
            }
        }

        return root
    }

    override fun onNoteClick(note: Product) {
        // opening a new intent and passing a data to it.
        val intent = Intent (activity, CRUDActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.measure)
        intent.putExtra("noteId", note.id)
        activity?.startActivity(intent)
    }

    override fun onDeleteIconClick(note: Product) {
        // in on note click method we are calling delete
        // method from  view modal to delete  not.
        viewModal.deleteNote(note)
        // displaying a toast message
        Toast.makeText(requireContext(), "${note.title} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}