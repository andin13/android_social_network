package com.example.androidsocialnetwork.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsocialnetwork.data.entities.UserItem
import com.example.androidsocialnetwork.databinding.UserItemBinding

class UserListAdapter(private val listener: (Int) -> Unit): PagingDataAdapter<UserItem, UserListAdapter.ViewHolder>(UsersDiffCallback()) {

    inner class ViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            with(binding) {
                userName.text = user.name
                userStatus.text = user.status?:"no status"
                root.setOnClickListener {
                    listener(user.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(UserItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class UsersDiffCallback : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem == newItem
    }
}