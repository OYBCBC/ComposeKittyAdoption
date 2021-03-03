package com.example.androiddevchallenge.repository.local

import com.example.androiddevchallenge.Kitty
import com.example.androiddevchallenge.MyApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DbHelper {

    suspend fun getCats(): List<Kitty> = coroutineScope {
        var dogs: List<Kitty> = ArrayList()
        val job = launch(Dispatchers.IO) {
            try {
                val assetsManager = MyApplication.context.assets
                val inputReader = InputStreamReader(assetsManager.open("kitty.json"))
                val jsonString = BufferedReader(inputReader).readText()
                val typeOf = object : TypeToken<List<Kitty>>() {}.type
                dogs = Gson().fromJson(jsonString, typeOf)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        job.join()
        dogs
    }
}