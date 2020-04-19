package com.project.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Priyanka on 16/04/2020
 */
abstract class CustomTextWatcher : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    abstract override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)

    override fun afterTextChanged(editable: Editable){
    }
}