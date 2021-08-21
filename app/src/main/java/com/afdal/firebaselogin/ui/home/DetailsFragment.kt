package com.afdal.firebaselogin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
 private lateinit var binding : FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }

}