package com.example.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composearticle.ui.theme.ComposeArticleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeArticleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Article()
                }
            }
        }
    }
}

@Composable
fun ArticleName(name: String) {
    Text(
        text = name,
        fontSize = 24.sp,
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            )
    )
}

@Composable
fun ArticleBody1(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),
        textAlign = TextAlign.Justify
    )
}

@Composable
fun ArticleBody2(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
        textAlign = TextAlign.Justify
    )
}

@Composable
fun ArticleImage(image: Painter) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
//            .wrapContentWidth()
    )
}

@Composable
fun Article() {
    val image = painterResource(id = R.drawable.bg_compose_background)
    Column {
        ArticleImage(image = image)
        ArticleName(name = stringResource(id = R.string.article_name))
        ArticleBody1(text = stringResource(id = R.string.body_1))
        ArticleBody2(text = stringResource(id = R.string.body_2))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeArticleTheme {
        Article()
    }
}