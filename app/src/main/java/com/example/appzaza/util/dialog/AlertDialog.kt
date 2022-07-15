package com.example.appzaza.util.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.util.*

object PopupUtil {

    fun alertShow(context: Context?, Content: String?): Boolean {
        return alertShow(context, Content, null)
    }

    private fun alertShow(
        Con: Context?,
        Content: String?,
        listener: DialogInterface.OnClickListener?
    ): Boolean {
        if (Con != null) {
            val build = AlertDialog.Builder(Con)
            val contextView = TextView(Con)
//            contextView.setTextSize(
//                TypedValue.COMPLEX_UNIT_PX,
//                Con.resources.getDimension(com.fws.mobile.R.dimen.FontSizeM)
//            )
            contextView.text = Content ?: ""
            contextView.gravity = Gravity.CENTER
            build.setTitle("NOTIFICATION")
            build.setView(contextView)
            build.setPositiveButton(
                "OK"
            ) { dialog, which ->
                dialog.dismiss()
                listener?.onClick(dialog, which)
            }
            build.create().show()
            return true
        }
        return false
    }

    fun showSimpleAlert(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Simple Alert")
            .setMessage("This is a simple alert dialog")
            .setPositiveButton("OK") { dialog, which ->
                Toast.makeText(context, "OK is pressed", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(context, "Cancel is pressed", Toast.LENGTH_LONG).show()
            }
            .setNeutralButton("Neutral") { dialog, which ->
                Toast.makeText(context, "Neutral is pressed", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    fun showListAlert(context: Context) {
        val items = arrayOf("Bird", "Cat", "Dog", "Fish", "Chicken")

        AlertDialog.Builder(context)
            .setTitle("List of Pets Dialog")
            .setItems(items) { dialog, which ->
                Toast.makeText(context, "${items[which]} is pressed", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(context, "Cancel is pressed", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    fun showMultipleChoicesAlert(context: Context) {
        val selectedList = ArrayList<Int>()

        val items = arrayOf("Bird", "Cat", "Dog", "Fish", "Chicken")

        AlertDialog.Builder(context)
            .setTitle("Multiple Choices Dialog")
            .setMultiChoiceItems(items, null) { dialog, which, isChecked ->
                if (isChecked) {
                    selectedList.add(which)
                } else if (selectedList.contains(which)) {
                    selectedList.remove(which)
                }
            }
            .setPositiveButton("OK") { dialog, which ->
                val selectedItems = ArrayList<String>()

                for (i in selectedList.indices) {
                    selectedItems.add(items[selectedList.get(i)])
                }
                Toast.makeText(context,
                    "Selected items: " + Arrays.toString(selectedItems.toArray()),
                    Toast.LENGTH_SHORT).show();
            }
            .show()
    }

//    fun dialogAlert(){
//            val dialogAlert = FragmentDialogAlert()
//            val fragmentTransaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
//            fragmentTransaction.addToBackStack("")
//            val screen: Fragment = getSupportFragmentManager().findFragmentByTag("sslCertDialog")
//            if (screen == null) {
//                Handler().postDelayed({
//                    try {
//                        fragmentTransaction.add(dialogAlert, "sslCertDialog")
//                        fragmentTransaction.commitAllowingStateLoss()
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }, 200)
//            }
//    }

}
