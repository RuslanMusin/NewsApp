package com.itis.newsapp.presentation.base

import com.arellomobile.mvp.MvpView
import com.itis.newsapp.R

interface BaseView : MvpView {
    fun setToolbarTitle(text: String)

    fun setToolbarTitle(text: Int)

    fun setNavigationIconVisibility(isVisible: Boolean)

    fun showErrorDialog(message: String, isSupport: Boolean = false, title: Int = R.string.error)

    fun showErrorDialog(message: Int, isSupport: Boolean = false, title: Int = R.string.error)

    fun showWaitProgressDialog(message: String? = null)

    fun hideWaitProgressDialog()

    fun hideKeyboard()

    fun showInfoDialog(title: Int, message: String)

    fun showInfoDialog(title: Int, message: Int)

    fun showDisconnectView()

    fun hideDisconnectView()
}