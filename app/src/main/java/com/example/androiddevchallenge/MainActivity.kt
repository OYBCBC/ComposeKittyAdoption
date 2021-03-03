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

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.base.BaseActivity
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = getString(R.string.app_name),
                                    color = MaterialTheme.colors.onPrimary,
                                    fontSize = 14.sp
                                )
                            },
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                            elevation = 0.dp
                        )
                    }
                ) {
                    Surface(color = MaterialTheme.colors.background) {
                        showList(kittiesLiveData = viewModel.kittiesLiveData) { _, kitty ->
                            val intent = Intent(this@MainActivity, KittyDetailActivity::class.java)
                            val bundle = Bundle()
                            bundle.putParcelable("kitty", kitty)
                            intent.putExtra("bundle", bundle)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        viewModel.parseData()
    }
}

@Composable
fun showList(
    kittiesLiveData: LiveData<Result<List<Kitty>>>,
    onClick: (pos: Int, kitty: Kitty) -> Unit
) {
    val result by kittiesLiveData.observeAsState()
    result?.let {
        when (it.status) {
            Result.SUCCESS -> {
                it.data?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        LazyColumn(Modifier.fillMaxWidth()) {
                            itemsIndexed(it) { index, kitty ->
                                Box(Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                                    AdoptionBox(position = index, info = kitty, onClick = onClick)
                                }
                            }
                        }
                    }
                }
            }
            Result.LOADING -> {
                loadingProgress()
            }
            else -> {
                Toast.makeText(MyApplication.context, it.msg, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun loadingProgress() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                15.dp
            )
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

/**
 * 领养item布局
 */
@Composable
fun AdoptionBox(position: Int, info: Kitty, onClick: (pos: Int, kitty: Kitty) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(position, info) },
        elevation = 4.dp,
    ) {
        val imgId = MyApplication.context.resources.getIdentifier(
            info.img, "drawable",
            MyApplication.context.packageName
        )
        val image: Painter = painterResource(imgId)
        Column(modifier = Modifier.fillMaxHeight()) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = image,
                    contentDescription = info.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(220.dp)
                )
                NameText(info.name)
            }
            DescriptionText(info.introduction)
//            Column(modifier = Modifier
//                .background(MaterialTheme.colors.primary.alpha(0.5f))
//                .fillMaxWidth()) {
//                requirementsNote(list = info.adoptionRequirements)
//            }
        }
    }
}

@Composable
fun NameText(name: String) {
    Box(
        modifier =
        Modifier
            .background(color = MaterialTheme.colors.onPrimary.alpha(0.5f))
            .fillMaxWidth()
            .padding(0.dp, 5.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = name,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DescriptionText(introduction: String) {
    Text(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp, start = 15.dp), text = introduction, color = MaterialTheme.colors.onBackground, fontSize = 14.sp)
}

@Composable
fun requirementsNote(list: List<String>) {
    list.forEachIndexed { index, s ->
        Text(
            color = MaterialTheme.colors.onPrimary,
            text = "${index + 1}. $s",
            fontWeight = FontWeight.Thin
        )
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApplication()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApplication()
    }
}
