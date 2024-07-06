package com.example.diet_application.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diet_application.CurrentUser
import com.example.diet_application.MainActivity
import com.example.diet_application.databinding.FragmentProfileBinding
import com.example.diet_application.ui.products.CRUDActivity
import com.example.diet_application.ui.sign_in.SignInActivity
import kotlin.math.round

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.getUserLoginById(CurrentUser.getId()).observe(viewLifecycleOwner) {
            binding.userLogin.text = "@$it"
        }
        profileViewModel.getUserResultsById(CurrentUser.getId()).observe(viewLifecycleOwner) {
            binding.caloriesNumber.text = round(it.calories).toString()
            binding.proteinsNumber.text = round(it.proteins).toString()
            binding.lipidsNumber.text = round(it.lipids).toString()
            binding.carbohydratesNumber.text = round(it.carbohydrates).toString()
        }


        binding.backBtn.setOnClickListener {
            val intent = Intent (activity, SignInActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity?.startActivity(intent)
            activity?.finish()
        }
        binding.updateParametrs.setOnClickListener {
            val intent = Intent (activity, UpdateParams::class.java)
            activity?.startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}