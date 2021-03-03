package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.Kitty

abstract class Repository {

    abstract suspend fun getCat(): List<Kitty>

}