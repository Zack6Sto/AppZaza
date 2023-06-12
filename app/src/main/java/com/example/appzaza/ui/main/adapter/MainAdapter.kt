package com.example.appzaza.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appzaza.data.model.User
import com.example.appzaza.databinding.ItemLayoutBinding
import com.example.appzaza.util.loadImage

class MainAdapter(
    private val users: ArrayList<User>,
    private var onItemSelect: ((users: User) -> Unit)? = null
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }

        fun bind(user: User) {
            binding.textViewUserName.text = user.name
            binding.textViewUserEmail.text = user.email
            binding.imageViewAvatar.loadImage(user.avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])

        holder.itemView.setOnClickListener {
            this.onItemSelect?.invoke(users[position])
        }
    }


    fun addData(list: List<User>) {
        users.addAll(list)
        Log.e("MainAdapter", "addData:$list")
    }

    fun clearData() {
        users.clear()
        Log.e("MainAdapter", "clearData")
    }

    fun setOnItemSelect(onItemSelect: (users: User) -> Unit) {
        this.onItemSelect = onItemSelect
    }

}