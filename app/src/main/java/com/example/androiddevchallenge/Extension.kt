package com.example.androiddevchallenge

import android.widget.Toast

public fun String.toast() {
    Toast.makeText(MyApplication.context, this, Toast.LENGTH_SHORT)
}