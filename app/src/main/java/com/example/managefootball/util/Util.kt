package com.example.managefootball.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun convertStringToDate(day: String): Date{
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    return dateFormat.parse(day)!!
}

fun extractNumbers(input: String): String {
    val regex = Regex("[^0-9]+") // Định nghĩa biểu thức chính quy để lấy các con số
    return regex.replace(input, "") // Loại bỏ tất cả kí tự không phải số
}

fun createAlphabetList( number: Int): List<String> {
    val result = mutableListOf<String>()
    val startChar = 'A'.code

    for (i in 0 until number) {
        val charToAdd = (startChar + i).toChar().toString()
        result.add(charToAdd)
    }

    return result
}