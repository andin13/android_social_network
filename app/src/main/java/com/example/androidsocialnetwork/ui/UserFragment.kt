package com.example.androidsocialnetwork.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.androidsocialnetwork.databinding.FragmentUserBinding
import com.example.androidsocialnetwork.di.appComponent
import javax.inject.Inject

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val args: UserFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: UserViewModel.Factory

    private val viewModel: UserViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.currentUserName.text = it.fullName
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.userLoading.visibility = View.VISIBLE
                false -> binding.userLoading.visibility = View.GONE
            }
        }

        viewModel.getProfile(args.userId)

        viewModel.profile.observe(viewLifecycleOwner) {
            if (it !== null) {
                Glide
                    .with(requireContext())
                    .load(it.photos?.small?: "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=")
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
                    .into(binding.currentUserImage)
            }
        }
    }

}