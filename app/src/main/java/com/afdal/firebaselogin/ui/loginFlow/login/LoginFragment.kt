package com.afdal.firebaselogin.ui.loginFlow.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afdal.firebaselogin.MainActivity.Companion.TAG
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentLoginBinding
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        //**************************************
        /*  binding.editTextLoginPassword.addTextChangedListener(object : TextWatcher {
              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                  //   TODO("Not yet implemented")
              }

              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                  // TODO("Not yet implemented")
              }

              override fun afterTextChanged(s: Editable?) {
                  s.toString().validator().nonEmpty().atleastOneNumber().atleastOneSpecialCharacters()
                      .atleastOneUpperCase().minLength(8).addErrorCallback {
                          binding.textLayoutLoginPassword.helperText = it
                          Log.d(TAG, it)
                      }.addSuccessCallback {
                          _validEmail = true
                          Log.d(TAG, "SUCCESS")
                      }.check()
              }

          })*/


        //*******************************
        binding.buttonLogin.setOnClickListener {
            val validEmail: Boolean =
                binding.editTextLoginEmail.validator().nonEmpty().validEmail().check()
            val validPassword: Boolean = binding.editTextLoginPassword.nonEmpty()

            if (validEmail && validPassword) {
                validateCredentials()
            } else {
                validateInput()
            }
        }
        binding.tvLoginSignupClickable.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        binding.tvLoginForgetPasswordClickable.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newCredentialsFragment)
        }
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

    private fun validateInput() {
        binding.editTextLoginPassword.validator().nonEmpty().addErrorCallback {
            binding.textLayoutLoginPassword.error = it
            Log.d(TAG, it)
        }.addSuccessCallback {
            Log.d(TAG, "non empty")
            binding.textLayoutLoginPassword.error = null
        }.check()
        binding.editTextLoginEmail.validator().nonEmpty().validEmail().addErrorCallback {
            binding.textLayoutLoginEmail.error = it
            Log.d(TAG, it)
        }.addSuccessCallback {
            binding.textLayoutLoginEmail.error = null
            Log.d(TAG, "SUCCESS")
        }.check()
    }

    private fun validateCredentials() {
        val email = binding.editTextLoginEmail.text.toString()
        val password = binding.editTextLoginPassword.text.toString()
        if (viewModel.loginUser(email, password)) {
            //may use singleLiveEvent
            findNavController().navigate(R.id.action_global_nested_graph_home)
        } else {
            //may use singleLiveEvent
            Toast.makeText(requireContext(), "** INVALID CREDENTIALS **", Toast.LENGTH_LONG)
                .show()
        }
    }

}