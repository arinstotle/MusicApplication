package com.example.musicapplication.forDeletion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapplication.R
import com.example.musicapplication.presentation.theme.DarkBackground
import com.example.musicapplication.presentation.theme.TextGrey
import com.example.musicapplication.presentation.theme.TextWhite

@Composable
@Preview(showBackground = true)
fun StreamPreview(){
    Column(modifier = Modifier.background(DarkBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)){
        Text(text = "Start your stream!",
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.spartan_bold)),
                fontWeight = FontWeight(700),
                color = TextWhite,
                )
        )
        ClickableText(text = AnnotatedString("Choose a song for streaming"),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.spartan_regular)),
                fontWeight = FontWeight(500),
                color = TextGrey,

                textAlign = TextAlign.Right,
                textDecoration = TextDecoration.Underline,
            ),
            onClick = {}
        )
    }
}