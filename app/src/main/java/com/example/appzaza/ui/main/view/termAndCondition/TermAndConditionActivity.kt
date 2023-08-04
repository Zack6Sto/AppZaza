package com.example.appzaza.ui.main.view.termAndCondition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebViewClient
import com.example.appzaza.base.BaseActivity
import com.example.appzaza.databinding.ActivityTermAndConditionBinding
import com.example.appzaza.ui.main.view.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TermAndConditionActivity : BaseActivity<ActivityTermAndConditionBinding>() {
    override val bindLayout: (LayoutInflater) -> ActivityTermAndConditionBinding
        get() = ActivityTermAndConditionBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initWebView()
        setOnClick()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.webViewClient = WebViewClient() // ตั้งค่าให้ใช้ WebViewClient ของเรา

        // เปิดใช้ JavaScript (ถ้าต้องการ)
        binding.webView.settings.javaScriptEnabled = true

        // ตั้งค่าให้ WebView สามารถเข้าถึงได้จาก JavaScript (ถ้าต้องการ)
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true

        // เรียกใช้ URL และโหลดเว็บเพจ
        val url = "https://www.google.co.th/?hl=th"
        binding.webView.loadUrl(url)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setOnClick() {
        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.btnConfirm.isEnabled = isChecked
        }

        binding.btnConfirm.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

}