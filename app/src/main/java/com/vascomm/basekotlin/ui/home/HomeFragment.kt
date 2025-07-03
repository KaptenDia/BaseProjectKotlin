package com.vascomm.basekotlin.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.base.BaseFragment
import com.vascomm.basekotlin.data.remote.model.UserResponse
import com.vascomm.basekotlin.databinding.FragmentHomeBinding
import com.vascomm.basekotlin.util.Status
import com.vascomm.basekotlin.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun inflateBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        observeModel()
        binding.searchUser.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearchUser(query)
                binding.searchUser.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Bisa juga panggil search saat teks berubah, tapi hati-hati agar tidak terlalu sering panggil API
                // performSearchUser(newText)
                return false
            }
        })
    }

    private fun observeModel() {
        viewModel.user.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { user ->
                        LogUtils.d("$this SUCCESS")
                        updateUI(user)
                    }
                    hideLoading()
                }

                Status.LOADING -> {
                    LogUtils.d("$this LOADING")
                    showLoading()
                }

                Status.ERROR -> {
                    LogUtils.d("$this ERROR")
                    LogUtils.d("$this ${response.message}")
                    context?.showMessage(response.message) {
                        Toast.makeText(context, "OK ditekan!", Toast.LENGTH_SHORT).show()
                    }
                    hideLoading()
                }
            }
        }
    }

    private fun updateUI(user: UserResponse) {
        binding.apply {
            imgAvatar.load(user.avatarUrl) {
                scale(Scale.FIT)
                placeholder(R.drawable.ic_baseline_home_24)
                error(R.drawable.ic_baseline_home_24)
                fallback(R.drawable.ic_baseline_home_24)
                crossfade(true)
            }

            tvUserName.text = user.name ?: "Unknown"
            tvUserEmail.text = user.email ?: "No Email"
            tvFollowers.text = "Followers: ${user.followers ?: 0}"
            tvFollowing.text = "Following: ${user.following ?: 0}"
            tvUserBio.text = user.bio ?: "Bio pengguna"
        }
    }

    fun performSearchUser(user: String?) {
        user?.let {
            if (it.isNotBlank()) {
                viewModel.getUser(it)
            }
        }
    }

}
