package com.vascomm.basekotlin.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.base.BaseFragment
import com.vascomm.basekotlin.data.remote.model.UserResponse
import com.vascomm.basekotlin.util.Status
import com.vascomm.basekotlin.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        observeModel()
    }

    private fun observeModel() {
        viewModel.user.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { user ->
                        LogUtils.d("$this SUCCESS")
                        LogUtils.d("hehe boayyy")
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
                    context?.showMessage(R.string.home) {
                        Toast.makeText(context, "OK ditekan!", Toast.LENGTH_SHORT).show()
                    }
                    hideLoading()
                }
            }
        })
    }

    private fun updateUI(user: UserResponse) {
        view?.let {
            val imgAvatar = it.findViewById<ImageView>(R.id.imgAvatar)
            val tvUserName = it.findViewById<TextView>(R.id.tvUserName)
            val tvUserEmail = it.findViewById<TextView>(R.id.tvUserEmail)
            val tvUserBio = it.findViewById<TextView>(R.id.tvUserBio)
            val tvFollowers = it.findViewById<TextView>(R.id.tvFollowers)
            val tvFollowing = it.findViewById<TextView>(R.id.tvFollowing)

            // Menampilkan gambar avatar dengan Coil
            imgAvatar.load(user.avatarUrl) {
                scale(Scale.FIT)
                placeholder(R.drawable.ic_baseline_home_24)
                error(R.drawable.ic_baseline_home_24)
                fallback(R.drawable.ic_baseline_home_24)
                crossfade(true)
            }

            // Menampilkan data user
            tvUserName.text = user.name ?: "Unknown"
            tvUserEmail.text = user.email ?: "No Email"
            tvFollowers.text = "Followers: ${user.followers ?: 0}"
            tvFollowing.text = "Following: ${user.following ?: 0}"
            tvUserBio.text =  user.bio ?: "Bio pengguna"
        }
    }
}
