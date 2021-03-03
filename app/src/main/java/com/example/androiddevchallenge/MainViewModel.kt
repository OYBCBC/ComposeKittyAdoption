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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.repository.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val kittiesLiveData: LiveData<Result<List<Kitty>>>
        get() = _kittiesLiveData

    private val _kittiesLiveData = MutableLiveData<Result<List<Kitty>>>()

    private val kitties = mutableListOf<Kitty>()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _kittiesLiveData.value = Result.error(throwable.message ?: "未知异常")
    }

    fun parseData() = viewModelScope.launch(handler) {
        _kittiesLiveData.value = Result.loading()
        kitties.addAll(repository.getCat())
        _kittiesLiveData.value = Result.success(kitties)
    }

    fun setKittyAdopted(position: Int) {
        kitties[position].adopted = true
        _kittiesLiveData.value = _kittiesLiveData.value
    }
}
