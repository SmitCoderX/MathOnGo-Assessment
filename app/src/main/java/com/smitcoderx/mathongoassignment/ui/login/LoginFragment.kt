package com.smitcoderx.mathongoassignment.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.smitcoderx.mathongoassignment.BuildConfig
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentLoginBinding
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var credentialManager: CredentialManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        credentialManager = CredentialManager.create(requireContext())

        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                val result = signIn()
                result?.credential?.data?.let { it1 -> loginViewModel.getCredentialData(it1) }
            }
        }

        loginViewModel.loginLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    hideLoading()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }

                is ResponseState.Loading -> {
                    showLoading()
                }

                is ResponseState.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private suspend fun signIn(): GetCredentialResponse? {
        val result = try {
            credentialManager.getCredential(
                request = createSignInRequest(),
                context = requireContext(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result
    }

    private fun createSignInRequest(): GetCredentialRequest {
        val option = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true).setServerClientId(BuildConfig.webClientId).build()

        return GetCredentialRequest.Builder().addCredentialOption(option).build()
    }

    private fun hideLoading() {
        binding.apply {
            loading.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }


    private fun showLoading() {
        binding.apply {
            btnLogin.visibility = View.INVISIBLE
            loading.visibility = View.VISIBLE
        }
    }
}