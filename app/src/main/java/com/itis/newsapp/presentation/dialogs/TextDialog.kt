package com.itis.newsapp.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.newsapp.R
import kotlinx.android.synthetic.main.dialog_text.*
import java.io.Serializable

class TextDialog : BaseDialog() {

    data class TextDialogParams(
        val code: Int,
        val positiveButton: String?,
        val negativeButton: String?,
        val title: String,
        val message: String?
    ) : Serializable

    companion object {
        private const val KEY_PARAMS = "KEY_PARAMS"
        fun getInstance(textDialogParams: TextDialogParams) = TextDialog().also {
            it.arguments = Bundle().apply {
                putSerializable(KEY_PARAMS, textDialogParams)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_text, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val params = arguments?.getSerializable(KEY_PARAMS) as? TextDialogParams
        if (params != null) {
            title.text = params.title
            params.message.let {
                if(it==null){
                    message.visibility = View.GONE
                }else{
                    message.visibility = View.VISIBLE
                    message.text = it
                }
            }
            params.positiveButton?.let { close.text = it }
            ok.visibility = if (params.positiveButton == null) View.GONE else View.VISIBLE
            params.negativeButton?.let { ok.text = it }
            close.visibility = if (params.negativeButton == null) View.GONE else View.VISIBLE
            close.setOnClickListener {
                positiveListener?.invoke(params.code)
                dismissAllowingStateLoss()
            }
            ok.setOnClickListener {
                negativeListener?.invoke(params.code)
                dismissAllowingStateLoss()
            }
        }
    }

}