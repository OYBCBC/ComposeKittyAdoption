package com.example.androiddevchallenge.repository.local

import com.example.androiddevchallenge.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepository(private val dbHelper: DbHelper):Repository() {

    override suspend fun getCat() = withContext(Dispatchers.IO) {
        dbHelper.getCats()
    }
}