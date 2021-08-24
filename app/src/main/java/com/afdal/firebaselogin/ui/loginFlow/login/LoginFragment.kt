package com.afdal.firebaselogin.ui.loginFlow.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afdal.firebaselogin.MainActivity.Companion.TAG
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


class LoginFragment : Fragment() {

    private var validPassword: Boolean = false
    private var validEmail: Boolean = false
    private var mAuth: FirebaseAuth? = null
    private lateinit var binding: FragmentLoginBinding
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

      /*  binding.tvLoading.visibility = View.GONE
        binding.tvError.visibility = View.GONE*/
        //Observers
        //***************************************
        viewModel.firebaseUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(R.id.action_global_nested_graph_home)
            }
        })
        //**************************************
        binding.buttonLogin.setOnClickListener {
            validtePasswordField()
            validateEmailField()

            if (validEmail && validPassword) {
                val email = binding.editTextLoginEmail.text.toString()
                val password = binding.editTextLoginPassword.text.toString()
                mAuth?.let { viewModel.signIn(requireActivity(), it, email, password) }
            } else {
                //may use singleLiveEvent
                Toast.makeText(requireContext(), "** INVALID CREDENTIALS **", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.tvLoginSignupClickable.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        binding.tvLoginForgetPasswordClickable.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newCredentialsFragment)
        }

        //*************************************
        viewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                FirebaseResponseStatus.LOADING -> {
                    binding.tvLoading.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.VISIBLE
                }
                FirebaseResponseStatus.ERROR -> {
                    binding.tvError.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.INVISIBLE
                }
                FirebaseResponseStatus.DONE ->{
                    binding.tvError.visibility = View.INVISIBLE
                    binding.progressCircular.visibility = View.INVISIBLE
                }
            }
        })
        //***********************************
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        //  (activity as MainActivity).setDrawerLocked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //   (activity as MainActivity).setDrawerUnlocked()
    }

    private fun validtePasswordField() {
        binding.editTextLoginPassword.validator().nonEmpty().addErrorCallback {
            validPassword = false
            binding.textLayoutLoginPassword.error = it

            Log.d(TAG, it)
        }.addSuccessCallback {
            validPassword = true
            binding.textLayoutLoginPassword.error = null
        }.check()
    }

    private fun validateEmailField() {
        binding.editTextLoginEmail.validator().nonEmpty().validEmail().addErrorCallback {
            validEmail = false
            binding.textLayoutLoginEmail.error = it
            Log.d(TAG, it)
        }.addSuccessCallback {
            validEmail = true
            binding.textLayoutLoginEmail.error = null
        }.check()
    }
}