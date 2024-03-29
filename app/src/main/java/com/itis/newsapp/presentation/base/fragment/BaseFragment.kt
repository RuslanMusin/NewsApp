package com.itis.newsapp.presentation.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.itis.newsapp.FixAndroidInjection
import com.itis.newsapp.R
import com.itis.newsapp.presentation.base.BaseView
import com.itis.newsapp.presentation.base.activity.BaseActivity
import com.itis.newsapp.presentation.base.viewmodel.IndicatorViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseFragment : Fragment(), BaseView, HasSupportFragmentInjector {

    companion object {
        private const val TAG = "BaseFragment"
        private const val TAG_PROGRESS_DIALOG = "ProgressDialog"
    }

    protected abstract val layout: Int

    open fun getToolbar(): Toolbar? = view?.findViewById(R.id.toolbar)

    @field:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var indicatorViewModel: IndicatorViewModel

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onAttach(context: Context) {
        FixAndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "${javaClass.name} - onCreateView()")
        return inflater.inflate(layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        indicatorViewModel = ViewModelProviders.of(
            this, viewModelFactory)[IndicatorViewModel::class.java]
        observeNetworkState()
        onViewPrepare(savedInstanceState)
    }

    override fun onDestroyView() {
        onViewDestroy()
        super.onDestroyView()
        Log.v(TAG, "${javaClass.name} - onDestroyView()")
    }

    protected open fun onViewPrepare(savedInstanceState: Bundle?) {
    }

    protected open fun onViewDestroy() {
    }

    override fun setToolbarTitle(text: String) {
        activity?.let {
            if (it is BaseView)
                it.setToolbarTitle(text)
        }
    }

    override fun setToolbarTitle(text: Int) {
        activity?.let {
            if (it is BaseView)
                it.setToolbarTitle(text)
        }
    }

    override fun setNavigationIconVisibility(isVisible: Boolean) {
        activity?.let {
            if (it is BaseView)
                it.setNavigationIconVisibility(isVisible)
        }
    }

    override fun showErrorDialog(message: Int, isSupport: Boolean, title: Int) {
        showErrorDialog(getString(message), isSupport, title)
    }

    override fun showErrorDialog(message: String, isSupport: Boolean, title: Int) {
        (activity as? BaseActivity)?.showErrorDialog(message, isSupport, title)
    }

    override fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = activity?.currentFocus
        if (view != null)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showWaitProgressDialog(message: String?) {
        indicatorViewModel.setWaitIndicator(true)
    }

    override fun hideWaitProgressDialog() {
        indicatorViewModel.setWaitIndicator(false)

    }

    override fun showInfoDialog(title: Int, message: String) {
        (activity as? BaseActivity)?.showInfoDialog(title, message)
    }

    override fun showInfoDialog(title: Int, message: Int) {
        (activity as? BaseActivity)?.showInfoDialog(title, message)
    }

    override fun showDisconnectView() {
        indicatorViewModel.setDisconnectIndicator(true)
    }

    override fun hideDisconnectView() {
        indicatorViewModel.setDisconnectIndicator(false)
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    open fun observeNetworkState() {
        indicatorViewModel.connectionLiveData.observe(this, Observer {
            if(it) {
                hideDisconnectView()
                onRetry()
            }
        })
    }

    open fun onRetry() { }

}