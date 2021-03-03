/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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