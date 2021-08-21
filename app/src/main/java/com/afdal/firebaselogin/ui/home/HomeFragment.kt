package com.afdal.firebaselogin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.btnHomeSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_global_nested_graph_login)
        }
        binding.btnHomeDetails.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}