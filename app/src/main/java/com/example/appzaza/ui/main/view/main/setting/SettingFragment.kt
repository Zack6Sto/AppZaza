package com.example.appzaza.ui.main.view.main.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.appzaza.data.api.ApiHelperImpl
import com.example.appzaza.data.api.RetrofitBuilder
import com.example.appzaza.data.sharedpreferences.SharedPreference
import com.example.appzaza.databinding.FragmentSettingBinding
import com.example.appzaza.ui.main.adapter.MainAdapter
import com.example.appzaza.ui.main.viewmodel.MainViewModel
import com.example.appzaza.ui.main.viewstate.MainState
import com.example.appzaza.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
private const val TAG = "SettingFragment"
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    lateinit var session: SharedPreference
    private var adapter = MainAdapter(arrayListOf())
    private lateinit var mainViewModel: MainViewModel

//    val TAG = "SettingFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        setDataUserSharedPreference()
//        setUI()
        setViewModel()
        observeViewModel()
        setOnClicks()
    }

    private fun setDataUserSharedPreference() {
        session = SharedPreference(requireContext())
        session.checkLogin()
        val user: HashMap<String, String> = session.getUserDetails()

        val name: String = user[SharedPreference.KEY_NAME]!!
        val pass: String = user[SharedPreference.KEY_PASS]!!

//        binding.userName.text = "Name: $name"
//        binding.password.text ="Pass: $pass"
    }
//
//    private fun setUI() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.run {
//            addItemDecoration(
//                DividerItemDecoration(
//                    binding.recyclerView.context,
//                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
//                )
//            )
//        }
//        binding.recyclerView.adapter = adapter
//    }
//
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

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when(it){
                    is MainState.Idle ->{

                    }
                    is MainState.Loading ->{
//                        binding.buttonFetchUser.visibility = View.GONE
//                        binding.progressBar.visibility = View.VISIBLE

                        Log.e(TAG,"observeViewModel Loading")
                    }

                    is MainState.Users ->{
//                        binding.progressBar.visibility = View.GONE
//                        binding.buttonFetchUser.visibility = View.GONE

////                        binding.userName.visibility = View.GONE
//                        binding.password.visibility = View.GONE
//                        renderList(it.user)

                        Log.e(TAG,"observeViewModel Users")
                    }

                    is MainState.Error ->{
//                        binding.progressBar.visibility = View.GONE
//                        binding.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"observeViewModel Error")
                    }
                    is MainState.DataOnBoarding -> TODO()
                    is MainState.ConfigApp -> TODO()
                    is MainState.Markets -> TODO()
                }
            }
        }
    }

    private fun setOnClicks() {
        //logout
        binding.btnLogout.setOnClickListener {
            session.logoutUser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.e(TAG,"observeViewModel onDestroyView")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG,"observeViewModel onResume")
    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private fun renderList(users: List<User>) {
//        binding.recyclerView.visibility = View.VISIBLE
//        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
//        adapter.notifyDataSetChanged()
//    }
}