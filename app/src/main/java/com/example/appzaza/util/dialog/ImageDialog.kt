package com.example.appzaza.util.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.appzaza.data.model.User
import com.example.appzaza.databinding.FragmentImageDialogBinding

class ImageDialog(val users: User) : DialogFragment() {

    private lateinit var _binding: FragmentImageDialogBinding
//    private lateinit var images : List<String>
//    companion object {
//        fun newInstance(images: List<String>?) = ImageDialog().apply {
//            this.images = images!!
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageDialogBinding.inflate(inflater,container,false)
        return _binding.root

    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iniView()
    }


    private fun iniView() {
        Glide.with(requireContext()).load(users.avatar).into(_binding.largeImage)
        Log.e("ImageDialog",": $users")
    }

    override fun onResume() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        super.onResume()
    }
}