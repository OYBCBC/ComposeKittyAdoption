package com.example.androiddevchallenge

import java.text.SimpleDateFormat
import java.util.Date

fun Date.format(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // 这个是你要转成后的时间的格式
    return sdf.format(this) // 时间戳转换成时间
}

fun Date.format(format: String): String {
    val sdf = SimpleDateFormat(format) // 这个是你要转成后的时间的格式
    return sdf.format(this) // 时间戳转换成时间
}