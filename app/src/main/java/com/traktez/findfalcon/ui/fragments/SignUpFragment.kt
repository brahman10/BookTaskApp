package com.traktez.findfalcon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.traktez.findfalcon.R
import com.traktez.findfalcon.databinding.FragmentSignUpBinding
import com.traktez.findfalcon.ui.fragments.SignUpViewModel.Companion.LOGIN
import com.traktez.findfalcon.ui.fragments.SignUpViewModel.Companion.SIGNUP
import com.traktez.findfalcon.utils.makeGone
import com.traktez.findfalcon.utils.makeVisible
import com.traktez.findfalcon.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var _binding: FragmentSignUpBinding
    private val binding get() = _binding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel.iterateJson()
        viewModel.countryList.add(0, "Select Country")
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            viewModel.countryList
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerCountry.adapter = arrayAdapter
        binding.spinnerCountry.setSelection(0)
        initListeners()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.saveDataIntoPreference(it)
                findNavController().navigate(SignUpFragmentDirections.toBookList())
                toast(requireContext(), "Logged in successfully!")

            } else {
                toast(requireContext(), "No user found. Please sign up.")
                changeUI()
            }
        }

        viewModel.signUpLiveData.observe(viewLifecycleOwner) {
            if (it > 0) {
                toast(requireContext(), "Sign up successful")
                changeUI()
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnContinue.setOnClickListener {
                if (validateFields()) {
                    if (viewModel.layoutType == SIGNUP) {
                        viewModel.signUp()
                    } else {
                        viewModel.login()
                    }
                }
            }

            login.setOnClickListener {
                changeUI()
            }

            etMail.addTextChangedListener {
                if (!it.isNullOrEmpty()) {
                    viewModel.username = it.toString()
                }
            }

            etPassword.addTextChangedListener {
                if (!it.isNullOrEmpty()) {
                    viewModel.password = it.toString()
                }
            }
        }
    }

    private fun changeUI() {
        with(binding) {
            if (viewModel.layoutType == SIGNUP) {
                viewModel.country = viewModel.countryList[spinnerCountry.selectedItemPosition]
                viewModel.layoutType = LOGIN
                tvLogo.text = LOGIN
                continueTv.text = LOGIN
                spinnerCountry.makeGone()
                tvSelectCountry.makeGone()
                login.text = HtmlCompat.fromHtml(
                    requireContext().getString(R.string.dont_have_account_signup),
                    0
                )
            } else {
                viewModel.layoutType = SIGNUP
                tvLogo.text = SIGNUP
                continueTv.text = SIGNUP
                spinnerCountry.makeVisible()
                tvSelectCountry.makeVisible()
                login.text = HtmlCompat.fromHtml(
                    requireContext().getString(R.string.already_has_account_login),
                    0
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        with(binding) {
            if (etMail.text.isNullOrEmpty()) {
                etMail.error = "Enter username"
                return false
            }

            if (etPassword.text.isNullOrEmpty()) {
                etPassword.error = "Enter password"
                return false
            }

            if (viewModel.layoutType == SIGNUP) {
                if (!etPassword.text!!.matches(Regex(viewModel.passwordRegex))) {
                    etPassword.error =
                        "Password must contain one number, one special characters, one lowercase letter, and one uppercase letter"
                    return false
                }
                if (spinnerCountry.selectedItemPosition == 0) {
                    toast(requireContext(), "Select country")
                    return false
                }
            }
        }
        return true
    }

}