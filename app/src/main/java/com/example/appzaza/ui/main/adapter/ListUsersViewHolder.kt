package com.example.appzaza.ui.main.adapter

import com.example.appzaza.base.BaseViewHolder
import com.example.appzaza.data.model.User
import com.example.appzaza.databinding.ItemLayoutBinding
import com.example.appzaza.util.loadImage

class ListUsersViewHolder constructor(
    private val binding :ItemLayoutBinding
    ):BaseViewHolder<User,ItemLayoutBinding>(binding) {

    override fun bind() {
        getRowItem()?.let {
            binding.textViewUserName.text = it.name
            binding.textViewUserEmail.text = it.email
            binding.imageViewAvatar.loadImage(it.avatar)
        }
    }
}