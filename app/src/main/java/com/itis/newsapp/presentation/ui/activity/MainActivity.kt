package com.itis.newsapp.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.itis.newsapp.presentation.base.BindingActivity
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.base.navigation.BottomNavOwner
import com.itis.newsapp.presentation.viewmodel.connection.InternetUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity :
    BindingActivity<com.itis.newsapp.databinding.ActivityMainBinding>(),
    BottomNavOwner {

    override val layout: Int = com.itis.newsapp.R.layout.activity_main

    lateinit var host: NavHostFragment

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        host = supportFragmentManager
            .findFragmentById(com.itis.newsapp.R.id.host) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        InternetUtil.init(application).observe(this, Observer {
            if(it) {
                hideDisconnect()
            }
            if(it && host is BindingFragment<*>) {
                Log.d("TAG", "connection")
                hideDisconnect()
                (supportFragmentManager
                    .findFragmentById(com.itis.newsapp.R.id.host) as BindingFragment<*>).onRetry()
            } else if (!it) {
                Log.d("TAG", "disconnect")
                showDisconnect()
            }
        })
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav_view.setupWithNavController(navController)
    }

    override fun setNavigationIconVisibility(isVisible: Boolean) {
        if(isVisible) {
            toolbar.setNavigationIcon(com.itis.newsapp.R.drawable.ic_arrow_back_white_24dp)
        } else {
            toolbar.setNavigationIcon(null)
        }
    }

    override fun getCurrentItemId(): Int {
        return bottom_nav_view.selectedItemId
    }

    override fun showWaitProgressDialog(message: String?) {
        Log.d("TAG", "loading")
        layout_wait.visibility = View.VISIBLE
        layout_connectivity.visibility = View.GONE
        lyaout_main.visibility = View.GONE
//        hideFragment()

    }

    override fun hideWaitProgressDialog() {
        Log.d("TAG", "hideLoading")
        layout_wait.visibility = View.GONE
        lyaout_main.visibility = View.VISIBLE
//        showFragment()
    }

    private fun showDisconnect() {
        layout_connectivity.visibility = View.VISIBLE
        layout_wait.visibility = View.GONE
        lyaout_main.visibility = View.GONE
    }

    private fun hideDisconnect() {
        layout_connectivity.visibility = View.GONE
        lyaout_main.visibility = View.VISIBLE
    }

    private fun hideFragment() {
        host.fragmentManager?.beginTransaction()
            ?.hide(host)
            ?.commit()
    }

    private fun showFragment() {
        host.fragmentManager?.beginTransaction()
            ?.show(host)
            ?.commit()
    }

}