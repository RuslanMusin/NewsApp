package com.itis.newsapp.presentation.base.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itis.newsapp.FixAndroidInjection
import com.itis.newsapp.presentation.base.BaseView
import com.itis.newsapp.presentation.dialogs.BaseDialog
import com.itis.newsapp.presentation.dialogs.ProgressDialog
import com.itis.newsapp.presentation.dialogs.builder.DialogBuilder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseView, HasSupportFragmentInjector {

    companion object {
        private const val TAG = "BaseActivity"
        private const val TAG_ERROR_DIALOG = "TextDialog"
        private const val TAG_PROGRESS_DIALOG = "ProgressDialog"
        private const val TAG_INFO_DIALOG = "IconDialog"
    }

    protected abstract val layout: Int
    private var isProgressVisible = false

    var infoDialog: BaseDialog? = null
    var errorDialog: BaseDialog? = null

    @field:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FixAndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Log.v(TAG, "${javaClass.name} - onCreate()")
        onViewPrepare(savedInstanceState)
    }

    override fun onDestroy() {
        onViewDestroy()
        super.onDestroy()
        Log.v(TAG, "${javaClass.name} - onDestroy()")
    }

    protected open fun onViewPrepare(savedInstanceState: Bundle?) {
        setContentView(layout)
    }

    protected open fun onViewDestroy() {

    }

    override fun setToolbarTitle(text: String) {
        (this as AppCompatActivity).supportActionBar?.title = text
    }

    override fun setToolbarTitle(text: Int) {
        (this as AppCompatActivity).supportActionBar?.setTitle(text)
    }

    protected open fun onOpenSupport() {

    }

    override fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = currentFocus
        if (view != null)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        if (!isProgressVisible) {
            hideWaitProgressDialog()
        }
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
        supportFragmentManager.apply {
            if(!isStateSaved) {
                if(infoDialog != null) {
                    infoDialog?.dismiss()
                }
                infoDialog = DialogBuilder(this@BaseActivity)
                    .setStyle(DialogBuilder.Style.INFO)
                    .setMessage(message)
                    .setTitle(getString(title))
                    .build(hashCode() - 1)
                infoDialog?.show(this,
                    TAG_INFO_DIALOG
                )
            }
        }
    }

    override fun showInfoDialog(title: Int, message: Int) {
        supportFragmentManager.apply {
            if(!isStateSaved) {
                if(infoDialog != null) {
                    infoDialog?.dismiss()
                }
                infoDialog = DialogBuilder(this@BaseActivity)
                    .setStyle(DialogBuilder.Style.INFO)
                    .setMessage(getString(message))
                    .setTitle(getString(title))
                    .build(hashCode() - 1)
                infoDialog?.show(this,
                    TAG_INFO_DIALOG
                )
            }
        }
    }

    override fun showErrorDialog(message: Int, isSupport: Boolean, title: Int) {
        showErrorDialog(getString(message), isSupport, title)
    }

    override fun showErrorDialog(message: String, isSupport: Boolean, title: Int) {
        supportFragmentManager.apply {
            if (!isStateSaved) {
                if(errorDialog != null) {
                    errorDialog?.dismiss()
                }
                errorDialog = DialogBuilder(this@BaseActivity)
                    .setStyle(DialogBuilder.Style.ERROR)
                    .setMessage(message)
                    .setTitle(getString(title))
                    .build(hashCode() - 1)
                errorDialog?.show(this,
                    TAG_ERROR_DIALOG
                )
            }
        }
    }


    override fun showDisconnectView() {
    }

    override fun hideDisconnectView() {
    }
}