package com.example.appzaza.ui.main.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.appzaza.data.api.ApiHelperImpl
import com.example.appzaza.data.api.RetrofitBuilder
import com.example.appzaza.data.sharedpreferences.SharedPreference
import com.example.appzaza.databinding.ActivityLoginBinding
import com.example.appzaza.ui.main.intent.MainIntent
import com.example.appzaza.ui.main.view.MainActivity
import com.example.appzaza.ui.main.viewmodel.MainViewModel
import com.example.appzaza.ui.main.viewstate.MainState
import com.example.appzaza.util.KeyboardUtil
import com.example.appzaza.util.ViewModelFactory
import com.example.appzaza.util.dialog.LoadingDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class LoginActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreference
    private val loadingDialog = LoadingDialog(this)

    val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
        initLoadConfig()
        initSession()
        observeViewModel()
        setupClicks()
        hideKeyboardWhenTouch()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.isDismiss()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        )[MainViewModel::class.java]
    }

    private fun initLoadConfig() {
        lifecycleScope.launch {
            mainViewModel.userIntent.send(MainIntent.FetchConfigApp)
        }

//        loadConfig(object : ApiRetrofitBuilder.CallBackApi<String?> {
//            override fun respondAPI(resp: String?) {
//                Log.w("loadConfig", "respondAPI:"+ resp.toString())
//            }
//
//            override fun respException(error: String?) {
//                Log.w("loadConfig", "respException:"+ error.toString())
//            }
//
//        })

    }

//    private fun loadConfig(callback: ApiRetrofitBuilder.CallBackApi<String?>) {
//        ApiRetrofitBuilder.instance.requestConfigMobileAppFirebase(object :
//            ApiRetrofitBuilder.CallBackApi<String?> {
//
//            override fun respondAPI(resp: String?) {
//                callback.respondAPI(resp!!)
//            }
//
//            override fun respException(error: String?) {
//                callback.respException(error)
//            }
//        })
//    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when(it){
                    is MainState.Idle ->{

                    }
                    is MainState.Loading ->{
                        loadingDialog.startLoading()
                        Log.e(TAG,"observeViewModel Loading")
                    }

                    is MainState.Users ->{

                    }

                    is MainState.Error ->{
                        Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"observeViewModel Error:"+it.error.toString())
                    }
                    is MainState.DataOnBoarding -> {

                    }
                    is MainState.ConfigApp -> {
                        loadingDialog.isDismiss()
//                        binding.tvNameApp.text = it.configApp[0]!!.AppName
                        binding.tvNameApp.text = it.configApp?.AppName
//                        binding.tvNameApp.text = it.configApp.body()?.AppName
                        Log.e(TAG,"observeViewModel ConfigApp:"+it.configApp.toString())
                    }
                    is MainState.Markets -> {
                        Log.e(TAG,"observeViewModel Markets")
                    }
                }
            }
        }
    }


    private fun setupClicks() {

        binding.btnLogin.setOnClickListener {
            checkInputLogin()
            KeyboardUtil.hideKeyboard(this)
        }

        binding.btnRegister.setOnClickListener {

        }

    }

    private fun checkInputLogin(){
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.trim().isNotEmpty() && password.trim().isNotEmpty()){
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchConfigApp)
            }
            sharedPref.createLoginSession(username,password)
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }else if (username.trim().isEmpty() && password.trim().isNotEmpty()){
            loadingDialog.startDialogAlert("Login Failed...\n Please enter username")
        }else if (username.trim().isNotEmpty() && password.trim().isEmpty()){
            loadingDialog.startDialogAlert("Login Failed...\n Please enter password")
        }else{
            loadingDialog.startDialogAlert("Login Failed...\n Please enter both credential")
        }
    }


    private fun initSession() {
        sharedPref = SharedPreference(applicationContext)
        if (sharedPref.isLogIn()){
            val i = Intent(applicationContext, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardWhenTouch() {
        val v = this.window.decorView
        v.setOnClickListener { v -> KeyboardUtil.hideKeyboard(v) }
        v.setOnTouchListener { v, _ ->
            KeyboardUtil.hideKeyboard(v)
            false
        }
    }

}