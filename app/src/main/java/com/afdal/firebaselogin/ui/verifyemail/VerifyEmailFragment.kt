package com.afdal.firebaselogin.ui.verifyemail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentVerifyEmailBinding
import com.afdal.firebaselogin.ui.loginFlow.login.LoginViewModel


class VerifyEmailFragment : Fragment() {
    private lateinit var binding: FragmentVerifyEmailBinding
    val viewModel = LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verify_email, container, false)
        binding.btnVerifyEmailVerifyEmail.setOnClickListener {
            if (viewModel.verifyEmail()) {
                findNavController().navigate(R.id.action_verifyEmailFragment_to_loginFragment)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}