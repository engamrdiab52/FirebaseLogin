package com.afdal.firebaselogin.ui.newcredentials

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afdal.firebaselogin.MainActivity
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentNewCredentialsBinding
import com.afdal.firebaselogin.ui.loginFlow.login.LoginViewModel
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

class NewCredentialsFragment : Fragment() {
    private lateinit var binding: FragmentNewCredentialsBinding
    var validPassword: Boolean = false
    var validConfirmPassword: Boolean = false
    val viewModel = LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_credentials, container, false)
        binding.editTxtCredentialsPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s.toString().validator().nonEmpty().atleastOneNumber().atleastOneSpecialCharacters()
                    .atleastOneUpperCase().minLength(8).addErrorCallback {
                        binding.textLayoutCredentialsPassword.helperText = it
                        validPassword = false
                        Log.d(MainActivity.TAG, it)
                    }.addSuccessCallback {
                        validPassword = true
                        Log.d(MainActivity.TAG, "SUCCESS")
                    }.check()
            }

        })
        binding.btnNewCredentialsUpdate.setOnClickListener {
            binding.editTxtCredentialsPassword.text.toString().validator().nonEmpty()
                .addErrorCallback {
                    binding.textLayoutCredentialsPassword.error = it
                }.addSuccessCallback {
                    binding.textLayoutCredentialsPassword.error = null
                }.check()


            binding.editTxtCredentialsConfirmPassword.text.toString().validator().nonEmpty()
                .addErrorCallback {
                    validConfirmPassword = false
                }.addSuccessCallback {
                    val str1 = binding.editTxtCredentialsConfirmPassword.text.toString()
                    val str2 = binding.editTxtCredentialsPassword.text.toString()
                    val matched = str1 == str2
                    if (!matched) {
                        binding.textLayoutCredentialsConfirmPassword.error =
                            "Passwords do not match"
                        validConfirmPassword = false
                    } else {
                        binding.textLayoutCredentialsConfirmPassword.helperText =
                            "Passwords are matched"
                        validConfirmPassword = true
                    }
                }.check()
            if (validPassword && validConfirmPassword) {
                if (viewModel.updatePassword(binding.editTxtCredentialsPassword.text.toString())){
                    findNavController().navigate(R.id.action_newCredentialsFragment_to_loginFragment)
                }else{
                    //may use singleLiveEvent
                    Toast.makeText(requireContext(), "** INVALID CREDENTIALS **", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}