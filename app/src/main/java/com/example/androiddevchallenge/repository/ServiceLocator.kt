package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.repository.local.DbHelper
import com.example.androiddevchallenge.repository.local.LocalRepository

object ServiceLocator {

    fun provideRepository() = LocalRepository(provideDataHelper())

    private fun provideDataHelper() = DbHelper()
}
