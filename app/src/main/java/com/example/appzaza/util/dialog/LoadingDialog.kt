package com.example.appzaza.util.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.appzaza.databinding.DialogAlertBinding
import com.example.appzaza.databinding.LoadingItemBinding

class LoadingDialog(private val activity: Activity) {
    private lateinit var isDialog: AlertDialog
    private lateinit var binding: DialogAlertBinding
    private lateinit var bindingLoading: LoadingItemBinding

    fun startLoading(){
        /**setView*/
        bindingLoading = LoadingItemBinding.inflate(activity.layoutInflater)
        /**setDialog*/
        val builder = AlertDialog.Builder(activity)
        builder.setView(bindingLoading.root)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }

    /**dialog alert*/
    fun startDialogAlert(msg: String){
        binding = DialogAlertBinding.inflate(activity.layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
        builder.setCancelable(false)

        binding.tvAlert.text = msg

        isDialog = builder.create()
        isDialog.show()

        binding.btnClose.setOnClickListener {
            isDialog.dismiss()
        }

    }
}