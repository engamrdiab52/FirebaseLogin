package com.afdal.firebaselogin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afdal.firebaselogin.MainActivity
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = HomeViewModel()
       mAuth = FirebaseAuth.getInstance()
        binding.btnHomeSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_global_nested_graph_login)
        }
        binding.btnHomeDetails.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
        }
        binding.btnHomeSignout.setOnClickListener {
            Log.d(MainActivity.TAG,"Before : "+ mAuth?.currentUser?.email.toString() )
            mAuth?.let { it1 -> viewModel.signOut(it1) }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

}