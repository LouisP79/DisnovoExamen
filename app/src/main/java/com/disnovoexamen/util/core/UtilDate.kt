package com.disnovoexamen.util.core

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val dateFormatServiceNew = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


fun getDateService(date: String): Date? {
    var dateVar = date
    dateVar = dateVar.substring(0, 19)
    dateVar = dateVar.replace("T", " ")
    return try {
        dateFormatServiceNew.parse(dateVar)!!
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }

}