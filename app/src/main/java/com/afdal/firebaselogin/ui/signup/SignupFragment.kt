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
import com.afdal.firebaselogin.ui.loginFlow.login.FirebaseResponseStatus
import com.google.firebase.auth.FirebaseAuth
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private var validPassword: Boolean = false
    private var validEmail: Boolean = false
    private var validConfirmPassword: Boolean = false
    private var validUserName: Boolean = false
    private var mAuth: FirebaseAuth? = null


    var viewModel = SignUpViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        viewModel = SignUpViewModel()
        mAuth = FirebaseAuth.getInstance()
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

        viewModel.firebaseUser.observe(viewLifecycleOwner,  {
            if (it != null) {
                findNavController().navigate(R.id.action_signupFragment_to_verifyEmailFragment)
            }
        })

        viewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                FirebaseResponseStatus.LOADING -> {
                    binding.signupCircularProgress.visibility = View.VISIBLE
                }
                FirebaseResponseStatus.ERROR -> {
                    binding.tvSigupError.visibility = View.VISIBLE
                    binding.signupCircularProgress.visibility = View.INVISIBLE
                }
                FirebaseResponseStatus.DONE ->{
                    binding.tvSigupError.visibility = View.INVISIBLE
                    binding.signupCircularProgress.visibility = View.INVISIBLE
                }
            }
        })
        binding.btnSignupSignup.setOnClickListener {

            validateNameFied()
            validtePasswordField()
            validateEmailField()
            vaidateConfirmPasswordField()

            if (validEmail && validUserName && validPassword && validConfirmPassword) {
                val password = binding.edtTxtSignupPassword.text.toString()
                val email = binding.edtTxtSignupEmail.text.toString()
                mAuth?.let {
                    viewModel.signUpUser(it, email, password)
                }

            }else {
                //may use singleLiveEvent
                Toast.makeText(requireContext(), "** INVALID CREDENTIALS **", android.widget.Toast.LENGTH_LONG)
                    .show()
            }

        }
        binding.txtViewSignupSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun validtePasswordField() {
        binding.edtTxtSignupPassword.text.toString().validator().atleastOneNumber().atleastOneSpecialCharacters()
            .atleastOneUpperCase().minLength(8).nonEmpty().addErrorCallback {
            binding.txtLayoutSignupPassword.error = it
        }.check()

        /*s.toString().validator().nonEmpty().atleastOneNumber().atleastOneSpecialCharacters()
                    .atleastOneUpperCase().minLength(8).addErrorCallback {
                        binding.txtLayoutSignupPassword.helperText = it
                        validPassword = false
                        Log.d(TAG, it)*/
    }

    private fun validateEmailField() {
        binding.edtTxtSignupEmail.text.toString().validator().nonEmpty().validEmail()
            .addErrorCallback {
                binding.txtLayoutSignupEmail.error = it
                validEmail = false
            }.addSuccessCallback {
                binding.txtLayoutSignupEmail.error = null
                validEmail = true
            }.check()

    }

    private fun validateNameFied() {
        binding.edtTxtSignupName.text.toString().validator().nonEmpty().addErrorCallback {
            binding.txtLayoutSignupName.error = it
            validUserName = false
        }.addSuccessCallback {
            binding.txtLayoutSignupName.error = null
            validUserName = true
        }.check()

    }

    private fun vaidateConfirmPasswordField() {
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
    }

}