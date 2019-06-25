package com.itis.newsapp.presentation.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.itis.newsapp.FixAndroidInjection
import com.itis.newsapp.R
import com.itis.newsapp.logger.Logger
import com.itis.newsapp.presentation.dialogs.ProgressDialog
import com.itis.newsapp.presentation.dialogs.builder.DialogBuilder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseView, HasSupportFragmentInjector {

    companion object {
        private const val TAG = "MoxyActivity"
        private const val TAG_ERROR_DIALOG = "TextDialog"
        private const val TAG_PROGRESS_DIALOG = "ProgressDialog"
        private const val TAG_INFO_DIALOG = "IconDialog"
    }

    protected abstract val layout: Int
    private var isProgressVisible = false

    @field:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FixAndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Logger.v(TAG, "${javaClass.name} - onCreate()")
         setContentView(layout)
         onViewPrepare(savedInstanceState)
    }

    override fun onDestroy() {
        onViewDestroy()
        super.onDestroy()
        Logger.v(TAG, "${javaClass.name} - onDestroy()")
    }

    protected open fun onViewPrepare(savedInstanceState: Bundle?) {

    }

    protected open fun onViewDestroy() {

    }

    override fun setToolbarTitle(text: String) {
        supportActionBar?.title = text
    }

    override fun setToolbarTitle(text: Int) {
        supportActionBar?.setTitle(text)
    }

    protected open fun onOpenSupport() {

    }

    override fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = currentFocus
        if (view != null)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showErrorDialog(message: Int, isSupport: Boolean, title: Int) {
        showErrorDialog(getString(message), isSupport, title)
    }

    override fun showErrorDialog(message: String, isSupport: Boolean, title: Int) {
        supportFragmentManager?.apply {
            if (!isStateSaved) {
                val current = findFragmentByTag(TAG_ERROR_DIALOG) as? DialogFragment
                val transaction = beginTransaction()
                if (current != null)
                    transaction.remove(current)
                DialogBuilder(this@BaseActivity)
                    .setStyle(DialogBuilder.Style.ERROR)
                    .setMessage(message)
                    .setTitle(getString(title))
                    .build(hashCode() - 1)
                    .show(transaction, TAG_ERROR_DIALOG)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isProgressVisible) {
            hideWaitProgressDialog()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun showWaitProgressDialog(message: String?) {
        isProgressVisible = true
        if (!supportFragmentManager.isStateSaved) {
            ProgressDialog.getInstance(message).show(
                supportFragmentManager,
                TAG_PROGRESS_DIALOG
            )
        }
    }

    override fun hideWaitProgressDialog() {
        isProgressVisible = false
        if (!supportFragmentManager.isStateSaved) {
            val dialog =
                supportFragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG) as? ProgressDialog
            dialog?.dismiss()
        }
    }

    override fun showInfoDialog(title: Int, message: String) {
        if (!supportFragmentManager.isStateSaved) {
            DialogBuilder(this)
                .setStyle(DialogBuilder.Style.INFO)
                .setTitle(getString(title))
                .setMessage(message)
                .build(hashCode())
                .show(supportFragmentManager, TAG_INFO_DIALOG)
        }
    }

    override fun showInfoDialog(title: Int, message: Int) {
        if (!supportFragmentManager.isStateSaved) {
            DialogBuilder(this)
                .setStyle(DialogBuilder.Style.INFO)
                .setTitle(getString(title))
                .setMessage(getString(message))
                .build(hashCode())
                .show(supportFragmentManager, TAG_INFO_DIALOG)
        }
    }
}