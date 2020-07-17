package com.disnovoexamen.util.extension

import android.widget.EditText

fun EditText.validateEmpty(msg: Int): Boolean{
    if(this.text!!.isEmpty()){
        this.error = this.context.getString(msg)
        return false
    }
    return true
}