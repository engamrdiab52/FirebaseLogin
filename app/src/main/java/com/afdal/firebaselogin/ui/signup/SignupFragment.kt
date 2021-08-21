package com.afdal.firebaselogin.ui.signup

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
import com.afdal.firebaselogin.MainActivity.Companion.TAG
import com.afdal.firebaselogin.R
import com.afdal.firebaselogin.databinding.FragmentSignupBinding
import com.afdal.firebaselogin.ui.loginFlow.login.LoginViewModel
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    var validPassword: Boolean = false
    var validEmail: Boolean = false
    var validConfirmPassword: Boolean = false
    var validUserName: Boolean = false


    val viewModel = LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        binding.edtTxtSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s.toString().validator().nonEmpty().atleastOneNumber().atleastOneSpecialCharacters()
                    .atleastOneUpperCase().minLength(8).addErrorCallback {
                        binding.txtLayoutSignupPassword.helperText = it
                        validPassword = false
                        Log.d(TAG, it)
                    }.addSuccessCallback {
                        binding.txtLayoutSignupPassword.helperText = null
                        validPassword = true
                        Log.d(TAG, "SUCCESS")
                    }.check()
            }
        })
        binding.btnSignupSignup.setOnClickListener {
            binding.edtTxtSignupPassword.text.toString().validator().nonEmpty().addErrorCallback {
                binding.txtLayoutSignupPassword.error = it
            }.check()


            binding.edtTxtSignupName.text.toString().validator().nonEmpty().addErrorCallback {
                binding.txtLayoutSignupName.error = it
                validUserName = false
            }.addSuccessCallback {
                binding.txtLayoutSignupName.error = null
                validUserName = true
            }.check()



            binding.edtTxtSignupEmail.text.toString().validator().nonEmpty().validEmail()
                .addErrorCallback {
                    binding.txtLayoutSignupEmail.error = it
                    validEmail = false
                }.addSuccessCallback {
                    binding.txtLayoutSignupEmail.error = null
                    validEmail = true
                }.check()



            binding.edtTxtSignupConfirmPassword.text.toString().validator().nonEmpty()
                .addErrorCallback {
                    validConfirmPassword = false
                }.addSuccessCallback {
                    val str1 = binding.edtTxtSignupConfirmPassword.text.toString()
                    val str2 = binding.edtTxtSignupPassword.text.toString()
                    val matched = str1 == str2
                    if (!matched) {
                        binding.txtLayoutSignupConfirmPassword.error = "Passwords do not match"
                        validConfirmPassword = false
                    } else {
                        binding.txtLayoutSignupConfirmPassword.helperText = "Passwords are matched"
                        validConfirmPassword = true
                    }
                }.check()
            if (validEmail && validPassword && validUserName && validConfirmPassword) {
                if ( viewModel.signUpUser(
                        binding.edtTxtSignupName.text.toString(),
                        binding.edtTxtSignupEmail.text.toString(),
                        binding.edtTxtSignupPassword.text.toString()
                    )){
                    findNavController().navigate(R.id.action_signupFragment_to_verifyEmailFragment)
                }else{
                    //may use singleLiveEvent
                    Toast.makeText(requireContext(), "** INVALID CREDENTIALS **", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
        binding.txtViewSignupSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}