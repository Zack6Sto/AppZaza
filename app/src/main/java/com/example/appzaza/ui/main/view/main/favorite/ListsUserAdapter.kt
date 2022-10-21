package com.example.appzaza.ui.main.view.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.appzaza.base.BaseRecyclerAdapter
import com.example.appzaza.data.model.User
import com.example.appzaza.databinding.ItemLayoutBinding
import com.example.appzaza.ui.main.adapter.ListUsersViewHolder

class ListsUserAdapter: BaseRecyclerAdapter<User, ItemLayoutBinding, ListUsersViewHolder>(ListUsersItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUsersViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListUsersViewHolder(binding = binding)
    }
}

class ListUsersItemDiffUtil : DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}