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

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.base.BaseActivity
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.util.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    var info = Adoptable("中华田园猫", Date(), "生性好动，右眼眼球缺失", listOf("经济条件允许", "住房处有窗、阳台需愿意封窗，网阳台"), listOf())
    Scaffold {

        Surface(color = MaterialTheme.colors.background) {
            Column {
//                Text(text = "Ready... Set... GO!")
            }
            AdoptionBox(info = info)
        }
    }
}

/**
 * 领养item布局
 */
@Composable
fun AdoptionBox(info: Adoptable) {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.primary)
        .padding(15.dp)) {
        Column() {
            Text(color = MaterialTheme.colors.onPrimary, text = info.category)
            Text(color = MaterialTheme.colors.onPrimary, text = info.birthDate.format())
            Text(color = MaterialTheme.colors.onPrimary, text = info.description)

            Column(modifier = Modifier.background(MaterialTheme.colors.primary.alpha(0.5f))) {
                info.adoptionRequirement.forEach {
                    Text(color = MaterialTheme.colors.onPrimary, text = it)
                }
            }

            Row {
                info.imgList.forEach {

                }
            }

        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
