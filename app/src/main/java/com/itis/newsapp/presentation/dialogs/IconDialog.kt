package com.itis.newsapp.presentation.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.newsapp.R
import kotlinx.android.synthetic.main.dialog_icon.*
import kotlinx.android.synthetic.main.dialog_icon.view.*
import java.io.Serializable

class IconDialog : BaseDialog() {

    data class IconDialogParams(
        val code: Int,
        val drawable: Int?,
        val positiveButton: String?,
        val negativeButton: String?,
        val title: String,
        val highlightTitle: String?,
        val message: String?,
        val highlightColor: Int?,
        val highlightTextSizeSp: Float?
    ) : Serializable

    companion object {

        private const val KEY_PARAMS = "KEY_PARAMS"

        fun getInstance(iconDialogParams: IconDialogParams): IconDialog {
            val args = Bundle().apply {
                putSerializable(KEY_PARAMS, iconDialogParams)
            }
            return IconDialog().apply { arguments = args }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_icon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val params = arguments?.getSerializable(KEY_PARAMS) as? IconDialogParams
        if (params != null) {
            view.title.text = params.title
            params.message.let {
                if(it==null){
                    view.message.visibility = View.GONE
                }else{
                    view.message.text = it
                    view.message.visibility = View.VISIBLE
                }
            }

            highlight_title.visibility = if (params.highlightTitle != null) {
                highlight_title.text = params.highlightTitle
                View.VISIBLE
            } else View.GONE
            params.highlightColor?.let { highlight_title.setTextColor(it) }
            params.highlightTextSizeSp?.let { highlight_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, it) }

            params.drawable?.let { view.icon.setImageResource(it) }
            params.positiveButton?.let { view.close.text = it }
            view.ok.visibility = if (params.positiveButton == null) View.GONE else View.VISIBLE
            params.negativeButton?.let { view.ok.text = it }
            view.close.visibility = if (params.negativeButton == null) View.GONE else View.VISIBLE
            view.close.setOnClickListener {
                positiveListener?.invoke(params.code)
                dismissAllowingStateLoss()
            }
            view.ok.setOnClickListener {
                negativeListener?.invoke(params.code)
                dismissAllowingStateLoss()
            }
        }
    }
}