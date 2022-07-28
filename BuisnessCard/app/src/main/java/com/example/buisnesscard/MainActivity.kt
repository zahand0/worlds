package com.example.buisnesscard

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buisnesscard.ui.theme.BuisnessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuisnessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PersonInfo()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun PersonInfo() {
    Image(
        painter = painterResource(id = R.drawable.background_2),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.2f
    )
    Column() {
        Spacer(Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.person_photo_v2),
            contentDescription = stringResource(id = R.string.photo_description),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .width(200.dp)
                .clip(CircleShape)
                .border(width = 5.dp, color = colorResource(R.color.purple), shape = CircleShape)
        )
        Text(
            text = stringResource(id = R.string.full_name),
            textAlign = TextAlign.Center,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)

        )
        Text(
            text = stringResource(id = R.string.job_title),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            color = colorResource(R.color.purple),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.height(100.dp))

        Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_phone),
                contentDescription = stringResource(id = R.string.phone_icon_description),
                tint = colorResource(R.color.purple),
                modifier = Modifier
                    .size(32.dp)
            )
            Text(
                text = stringResource(id = R.string.phone_number),
                textAlign = TextAlign.Right,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
        }
        Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_social_media),
                contentDescription = stringResource(id = R.string.social_media_icon_description),
                tint = colorResource(R.color.purple),
                modifier = Modifier
                    .size(32.dp)
            )
            Text(
                text = stringResource(id = R.string.social_media),
                textAlign = TextAlign.Right,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
        }

        Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_email),
                contentDescription = stringResource(id = R.string.email_icon_description),
                tint = colorResource(R.color.purple),
                modifier = Modifier
                    .size(32.dp)
            )
            Text(
                text = stringResource(id = R.string.email),
                textAlign = TextAlign.Right,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BuisnessCardTheme {
        PersonInfo()
    }
}