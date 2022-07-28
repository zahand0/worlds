package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FourQuadrants()
                }
            }
        }
    }
}

@Composable
fun FirstQuadrant() {
    Box(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxSize()
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.quadrant_1_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(text = stringResource(id = R.string.text_1_quadrant))
        }
    }
}

@Composable
fun SecondQuadrant() {
    Box(
        modifier = Modifier
            .background(Color.Yellow)
            .fillMaxSize()
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.quadrant_2_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(text = stringResource(id = R.string.text_2_quadrant))
        }
    }
}

@Composable
fun ThirdQuadrant() {
    Box(
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize()
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.quadrant_3_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(text = stringResource(id = R.string.text_3_quadrant))
        }
    }
}

@Composable
fun FourthQuadrant() {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.quadrant_4_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(text = stringResource(id = R.string.text_4_quadrant))
        }
    }
}

@Composable
fun FourQuadrants() {
    Column {
        Row(Modifier.weight(1f)) {
            Box(Modifier.weight(1f)) { FirstQuadrant() }
            Box(Modifier.weight(1f)) { SecondQuadrant() }
        }
        Row(Modifier.weight(1f)) {
            Box(Modifier.weight(1f)) { ThirdQuadrant() }
            Box(Modifier.weight(1f)) { FourthQuadrant() }
        }
    }
    
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeQuadrantTheme {
        FourQuadrants()
    }
}