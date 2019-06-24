package com.itis.newsapp.presentation.dialogs.builder

import android.content.Context
import androidx.annotation.DrawableRes
import com.itis.newsapp.R
import com.itis.newsapp.presentation.dialogs.BaseDialog
import com.itis.newsapp.presentation.dialogs.IconDialog
import com.itis.newsapp.presentation.dialogs.TextDialog

class DialogBuilder(private val context: Context) {

    enum class Style {
        INFO, WARNING, ERROR, MESSAGE
    }

    private var icon: Int? = null
        get() {
            return if (field == null) {
                when (style) {
                    Style.INFO -> R.drawable.ic_info
                    Style.WARNING -> R.drawable.ic_warning
                    Style.ERROR -> R.drawable.ic_warning
                    else -> null
                }
            } else field
        }
    private var title: String = ""
        get() {
            if (field.isBlank()) {
                return when (style) {
                    Style.ERROR -> context.getString(R.string.error)
                    else -> field
                }
            }
            return field
        }
    private var message: String ?=null
    private var style = Style.INFO
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null
        get() {
            return if (field == null)
                when (style) {
                    Style.INFO -> context.getString(R.string.close)
                    Style.WARNING -> context.getString(R.string.close)
                    Style.ERROR -> context.getString(R.string.close)
                    Style.MESSAGE -> context.getString(R.string.close)
                }
            else field
        }
    private var highlightTitle: String? = null
    private var highlightColor: Int? = null
    private var highlightTextSizeSp: Float? = null

    fun setHighlightTitle(text: String?): DialogBuilder {
        this.highlightTitle = text
        return this
    }

    fun setHighlightTextColor(textColor: Int?): DialogBuilder {
        this.highlightColor = textColor
        return this
    }

    fun setHighlightTextSizeSp(size: Float?): DialogBuilder {
        this.highlightTextSizeSp = size
        return this
    }

    fun setStyle(style: Style): DialogBuilder {
        this.style = style
        return this
    }

    fun setTitle(title: String): DialogBuilder {
        this.title = title
        return this
    }

    fun setMessage(message: String?): DialogBuilder {
        this.message = message
        return this
    }

    fun setIcon(@DrawableRes icon: Int?): DialogBuilder {
        this.icon = icon
        return this
    }

    fun setPositiveButtonText(text: String?): DialogBuilder {
        this.positiveButtonText = text
        return this
    }

    fun setNegativeButtonText(text: String?): DialogBuilder {
        this.negativeButtonText = text
        return this
    }


    fun build(requestCode: Int): BaseDialog {
        return when (style) {
            Style.INFO, Style.WARNING, Style.ERROR -> {
                val params = IconDialog.IconDialogParams(requestCode, icon, positiveButtonText, negativeButtonText, title, highlightTitle, message, highlightColor, highlightTextSizeSp)
                IconDialog.getInstance(params)
            }
            else -> {
                val params = TextDialog.TextDialogParams(requestCode, positiveButtonText, negativeButtonText, title, message)
                TextDialog.getInstance(params)
            }
        }
    }

}