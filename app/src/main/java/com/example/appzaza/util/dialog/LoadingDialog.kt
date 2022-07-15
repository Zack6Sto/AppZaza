package com.example.appzaza.util.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.appzaza.R
import kotlinx.android.synthetic.main.dialog_alert.view.*
import kotlinx.android.synthetic.main.dialog_custom_layout.view.tv_alert

class LoadingDialog(private val activity: Activity) {
    private lateinit var isDialog: AlertDialog

    fun startLoading(){
        /**setView*/
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item,null)
        /**setDialog*/
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }

    /**dialog alert*/
    fun startDialogAlert(msg: String){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_alert,null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)


        dialogView.tv_alert.text = msg

        isDialog = builder.create()
        isDialog.show()

        dialogView.btn_close.setOnClickListener {
            isDialog.dismiss()
        }

    }
}