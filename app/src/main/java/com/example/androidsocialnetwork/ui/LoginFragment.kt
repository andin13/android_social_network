package com.example.androidsocialnetwork.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidsocialnetwork.R
import com.example.androidsocialnetwork.databinding.FragmentLoginBinding
import com.example.androidsocialnetwork.di.appComponent
import javax.inject.Inject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var viewModelFactory: LoginViewModel.Factory

    val navController by lazy { findNavController() }

    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewModel.action.observe(viewLifecycleOwner){
            it.invoke {
                when(it){
                    is LoginViewActions.ToUserList -> navController.navigate(R.id.action_login_to_usersListFragment)
                }
            }
        }

        viewModel.result.observe(viewLifecycleOwner) {
            binding.result.text = it
        }

        viewModel.resultAuth.observe(viewLifecycleOwner) {
            binding.resultAuth.text = it
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.loginProgressBar.visibility = View.VISIBLE
                false -> binding.loginProgressBar.visibility = View.GONE
            }

        }

        viewModel.authMe()

    }

    private fun initView(){
        with(binding){

            email.addTextChangedListener {
                viewModel.setLogin(it.toString())
            }

            password.addTextChangedListener {
                viewModel.setPassword(it.toString())
            }

            loginBtn.setOnClickListener {
                viewModel.login()
            }

            authBtn.setOnClickListener {
                viewModel.authMe()
            }

            logoutBtn.setOnClickListener {
                viewModel.logout()
            }

        }
    }
}