package com.itis.newsapp.presentation.dialogs

import android.app.AlertDialog
import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment(){

    var positiveListener: ((code: Int) -> Unit)? = null
    var negativeListener: ((code: Int) -> Unit)? = null

    fun setTitle(title: String) {
        dialog?.setTitle(title)
    }

    fun setMessage(message: String) {
        (dialog as? AlertDialog)?.setMessage(message)
    }

}