package com.project.utils.widgets

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import com.project.R
import com.project.utils.CustomTextWatcher

class SearchWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var listener: OnSearchClickListener? = null
    private var currentTime: Long = 0
    var edtSearch: EditText? = null

    fun setSearchClickListener(listener: OnSearchClickListener) {
        this.listener = listener
    }

    init {
        attrs?.let { init(it) }
    }

    private fun init(attrs: AttributeSet) {
        val rootView = View.inflate(context, R.layout.layout_search_view, this)
        edtSearch = rootView.findViewById(R.id.edt_search)
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.SearchWidget, 0, 0)
        val hintText: String?

        try {
            hintText = array.getString(R.styleable.SearchWidget_searchHint)
        } finally {
            array.recycle()
        }
        edtSearch?.hint = hintText

        edtSearch?.setOnClickListener {
            edtSearch?.isCursorVisible = true
        }
        edtSearch?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                currentTime = System.currentTimeMillis()

                if (edtSearch?.text.toString().trim().isNotEmpty()) {
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            if (System.currentTimeMillis() - currentTime > 500) {
                                listener?.onSearchTextChange(edtSearch?.text.toString())
                            }
                            handler.removeCallbacks(this)
                        }
                    }, 510)
                }
            }
        })
    }
    fun getTextFromEditText():String{
        return edtSearch?.text.toString().trim()
    }

    interface OnSearchClickListener {
        fun onSearchTextChange(searchKeyword: String)

    }

}