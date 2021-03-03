package com.example.androiddevchallenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.base.BaseActivity
import com.example.androiddevchallenge.ui.theme.MyTheme

class KittyDetailActivity : BaseActivity() {
    private val kitty: Kitty?
        get() {
            val kitty = intent.getBundleExtra("bundle")?.getParcelable<Kitty?>("kitty")
            return kitty
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (kitty == null) {
            Toast.makeText(this, "数据异常", Toast.LENGTH_LONG)
            finish()
        }
        setContent {
            val title = kitty!!.name
            MyTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = if (title.isEmpty()) getString(R.string.app_name) else title,
                                    color = MaterialTheme.colors.onPrimary,
                                    fontSize = 14.sp
                                )
                            },
                            backgroundColor = MaterialTheme.colors.primaryVariant, elevation = 0.dp,
                            navigationIcon = {
                                IconButton(onClick = { navigateBack() }) {
                                    val backIcon: Painter = painterResource(R.drawable.ic_back)
                                    Icon(painter = backIcon, contentDescription = "ic_back")
                                }
                            }
                        )
                    }
                ) {
                    detailKitty(kitty!!)

                }
            }


        }
    }

    var showConfirmDialog by mutableStateOf(false)

    @Composable
    fun detailKitty(kitty: Kitty) {
        val kitty by remember(calculation = { mutableStateOf(kitty) })
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            detailImage(kitty.img)
            Spacer(modifier = Modifier.requiredHeight(50.dp))
            detailIntroduction(kitty.introduction)
            Spacer(modifier = Modifier.requiredHeight(50.dp))

            AdoptButton(kitty.adopted)
        }
        if (showConfirmDialog) {
            AdoptConfirmDialog(kitty = kitty)
        }
    }

    @Composable
    fun AdoptButton(adopted: Boolean) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { showConfirmDialog = true },
                enabled = !adopted
            ) {
                Text(text = if (adopted) "已领养" else "领养它!")
            }
        }
    }

    @Composable
    fun detailIntroduction(introduction: String) {
        Box(Modifier.background(Color.Gray, shape = RectangleShape)) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = introduction,
                fontSize = 18.sp,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }

    @Composable
    fun detailImage(imgId: String) {
        val imageIdentity = MyApplication.context.resources.getIdentifier(
            imgId, "drawable",
            MyApplication.context.packageName
        )
        val image: Painter = painterResource(imageIdentity)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = image,
                contentDescription = kitty!!.img,
                modifier = Modifier
                    .requiredSize(150.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    fun AdoptConfirmDialog(kitty: Kitty) {
        val stringBuilder = StringBuilder()
        kitty.adoptionRequirements.forEachIndexed { index, s ->
            stringBuilder.append(index + 1)
            stringBuilder.append(". ")
            stringBuilder.append(s)
            stringBuilder.append("\n")
        }
        AlertDialog(
            onDismissRequest = {
                showConfirmDialog = false
            },
            text = {
                Text(
                    text = "领养这只小猫吗? 请确认你是否具备以下条件\n\n$stringBuilder",
                    style = MaterialTheme.typography.body2
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        kitty.adopted = true
                    }
                ) {
                    Text(
                        text = "确认"
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                    }
                ) {
                    Text(
                        text = "取消"
                    )
                }
            }
        )
    }

    fun navigateBack() {
        finish()
    }
}