package com.example.androidsocialnetwork.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidsocialnetwork.databinding.FragmentUsersListBinding
import com.example.androidsocialnetwork.di.appComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersListFragment : Fragment() {

    private lateinit var binding: FragmentUsersListBinding

    @Inject
    lateinit var viewModelFactory: UsersListViewModel.Factory

    private val viewModel: UsersListViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
//        viewModel.getUsers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usersList.adapter = UserListAdapter {
            viewModel.showProfile(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.usersListProgress.visibility = View.VISIBLE
                false -> binding.usersListProgress.visibility = View.GONE
            }
        }
        //viewModel.usersFlow

        viewModel.viewAction.observe(viewLifecycleOwner) {
            it.invoke {
                when(it){
                    is UsersListViewActions.ToProfileScreen -> toProfileScreen(it.userId)
                }
            }
        }

        lifecycleScope.launch{
            viewModel.usersFlow.collectLatest {
                (binding.usersList.adapter as UserListAdapter).submitData(lifecycle,it)
            }
        }

    }

    private fun toProfileScreen(userId: Int){
        val action = UsersListFragmentDirections.actionUsersListFragmentToUserFragment(userId)
        findNavController().navigate(action)
    }

}