package com.example.appzaza.ui.main.view.main.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appzaza.data.model.MenuModel
import com.example.appzaza.databinding.ItemMenuBinding
import com.example.appzaza.util.loadImage

class SettingAdapter(private val menuModel: ArrayList<MenuModel>):RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.tvNameMenu.text = menuModel[position].nameMenu
//        holder.binding.imvMenu.loadImage(menuModel[position].imageMenu)
    }

    override fun getItemCount(): Int {
        return menuModel.size
    }

}