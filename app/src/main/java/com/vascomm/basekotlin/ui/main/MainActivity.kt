package com.vascomm.basekotlin.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.base.BaseActivity
import com.vascomm.basekotlin.databinding.ActivityMainBinding
import com.vascomm.basekotlin.util.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private var currentNavController: LiveData<NavController>? = null

    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            initBottomNavigationBar()
        }
    }

    private fun initBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_test
        )

        val controller = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        controller.observe(this) { navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                // Optional: handle destination changes if needed
            }
        }

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initBottomNavigationBar()
    }
}
