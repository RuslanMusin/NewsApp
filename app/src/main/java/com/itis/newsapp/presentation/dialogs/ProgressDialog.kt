package com.itis.newsapp.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.itis.newsapp.R

class ProgressDialog : DialogFragment() {

    companion object {
        private const val KEY_MESSAGE = "KEY_MESSAGE"
        fun getInstance(message: String?): ProgressDialog {
            return ProgressDialog().also {
                it.arguments = Bundle().apply {
                    putString(KEY_MESSAGE, message)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }

}