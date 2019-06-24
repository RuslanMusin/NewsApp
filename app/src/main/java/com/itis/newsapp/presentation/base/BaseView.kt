package com.itis.newsapp.presentation.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.newsapp.R

interface BaseView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setToolbarTitle(text: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setToolbarTitle(text: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorDialog(message: String, isSupport: Boolean = false, title: Int = R.string.error)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorDialog(message: Int, isSupport: Boolean = false, title: Int = R.string.error)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showWaitProgressDialog(message: String? = null)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideWaitProgressDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideKeyboard()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInfoDialog(title: Int, message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInfoDialog(title: Int, message: Int)
}