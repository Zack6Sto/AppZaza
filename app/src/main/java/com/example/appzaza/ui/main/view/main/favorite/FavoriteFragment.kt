package com.example.appzaza.ui.main.view.main.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appzaza.data.api.ApiHelperImpl
import com.example.appzaza.data.api.RetrofitBuilder
import com.example.appzaza.data.model.ConfigData
import com.example.appzaza.data.model.User
import com.example.appzaza.data.sharedpreferences.SharedPreference
import com.example.appzaza.databinding.FragmentFavoriteBinding
import com.example.appzaza.ui.main.adapter.MainAdapter
import com.example.appzaza.ui.main.intent.MainIntent
import com.example.appzaza.ui.main.viewmodel.MainViewModel
import com.example.appzaza.ui.main.viewstate.MainState
import com.example.appzaza.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
private const val TAG = "FavoriteFragment"
@ExperimentalCoroutinesApi
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    lateinit var session: SharedPreference
    private var adapter = MainAdapter(arrayListOf())
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        setDataUserSharedPreference()
        setUI()
        setViewModel()
//        setLoadConfig()
        observeViewModel()
        setupClicks()
    }

    private fun setLoadConfig() {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchConfigApp)
            }
    }

    private fun setDataUserSharedPreference() {
        session = SharedPreference(requireContext())
        session.checkLogin()
        val user: HashMap<String, String> = session.getUserDetails()

        val name: String = user[SharedPreference.KEY_NAME]!!
        binding.userName.text = name
    }

    private fun setUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.recyclerView.adapter = adapter
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

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when(it){
                    is MainState.Idle ->{

                    }
                    is MainState.Loading ->{
                        renderShowProgressBar()
                        Log.e(TAG,"observeViewModel Loading")
                    }

                    is MainState.Users ->{
                        renderList(it.user)
                        Log.e(TAG,"observeViewModel Users")
                    }

                    is MainState.Error ->{
                        renderError()
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"observeViewModel Error")
                    }
                    is MainState.DataOnBoarding -> TODO()
                    is MainState.ConfigApp -> {
//                        renderDataConfig(listOf(it.configApp))
                        renderDataConfig(it.configApp)
                        Log.e(TAG,"observeViewModel ConfigApp")
                    }
                    is MainState.Markets -> TODO()
                }
            }
        }
    }

    private fun renderShowProgressBar() {
        binding.buttonFetchUser.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun renderError() {
        binding.progressBar.visibility = View.GONE
        binding.buttonFetchUser.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(users: List<User>) {
        binding.progressBar.visibility = View.GONE
        binding.buttonFetchUser.visibility = View.GONE

        binding.recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }

    private fun renderDataConfig(config: ConfigData?) {
        binding.progressBar.visibility = View.GONE
        binding.buttonFetchUser.visibility = View.VISIBLE
//        binding.userName.text = config[0]?.name
        binding.userName.text = config.toString()
        Log.e(TAG,"renderDataConfig: $config")
    }

    private fun setupClicks() {
        binding.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser("zack"))
            }
        }

        binding.imvFav.setOnClickListener {
            Toast.makeText(activity, "FAV", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        adapter.clearData()
        Log.e(TAG,"observeViewModel onDestroyView")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG,"observeViewModel onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG,"observeViewModel onPause")
    }

}
