package com.example.androiddevchallenge

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * 领养子类
 */
@Parcelize
class Kitty(
    var name: String,
    var introduction: String,
    var img: String,
    var adopted: Boolean,
    var adoptionRequirements: List<String>
) : Parcelable