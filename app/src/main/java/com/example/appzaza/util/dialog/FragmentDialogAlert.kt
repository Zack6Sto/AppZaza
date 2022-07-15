package com.example.appzaza.util.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appzaza.R
import com.example.appzaza.databinding.FragmentDialogAlertBinding

class FragmentDialogAlert : DialogFragment() {

    private var _binding: FragmentDialogAlertBinding? = null

//    private var _binding: ResultProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dialog_alert, container, false)
        _binding = FragmentDialogAlertBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}