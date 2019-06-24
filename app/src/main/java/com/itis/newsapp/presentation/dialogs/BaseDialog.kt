package com.itis.newsapp.presentation.dialogs

import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment(){

    var positiveListener: ((code: Int) -> Unit)? = null
    var negativeListener: ((code: Int) -> Unit)? = null

}