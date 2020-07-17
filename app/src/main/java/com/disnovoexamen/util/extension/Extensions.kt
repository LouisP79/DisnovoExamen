package com.disnovoexamen.util.extension

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.validateEmpty(msg: Int): Boolean{
    if(this.text!!.isEmpty()){
        this.error = this.context.getString(msg)
        return false
    }
    return true
}