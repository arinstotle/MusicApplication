package com.example.musicapplication.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapplication.R
import com.example.musicapplication.ui.theme.DarkBackground
import com.example.musicapplication.ui.theme.RegDarkPurple
import com.example.musicapplication.ui.theme.RegLightPurple
import com.example.musicapplication.ui.theme.TextWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun RegistrationScreenFragment(){
    Column(modifier = Modifier
        .background(DarkBackground)
        .fillMaxWidth()
        .padding(start = 14.dp, end = 14.dp, bottom = 40.dp)){
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(DarkBackground),
            shape = RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp)
        ){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(RegLightPurple, RegDarkPurple)),
                    alpha = 0.8f
                )){
                Image(modifier = Modifier
                    .padding(start = 16.dp, top = 19.dp, bottom = 19.dp)
                    .height(70.dp)
                    .width(70.dp),
                    painter = painterResource(id = R.drawable.ic_default_profile_photo),
                    contentDescription = "photo",)
            }
        }
        Card(modifier = Modifier
            .height(560.dp)
            .fillMaxWidth(),
            shape = RectangleShape
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = DarkBackground, shape = RoundedCornerShape(size = 0.dp))
                .padding(start = 16.dp, top = 25.dp, end = 16.dp)){
                Text(text = "Create account",
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF)
                    )
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp)
                    .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    value = "Your name",
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000)
                    ))
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    value = "Your e-mail",
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000)
                    ))
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    value = "Password",
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000)
                    ))
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp)
                    .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    value = "Repeat your password",
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000)
                    ))
                Text(
                    text = "The password must contain a  8 characters at least including 1 uppercase letter, 1 number and 1 special character.",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_regular)),
                        fontWeight = FontWeight(400),
                        color = TextWhite
                    ),
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier
                        .width(176.dp)
                        .height(95.dp),
                        text = "Then you can log in to your account",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.spartan_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),)
                    )
                    TextButton(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(105.dp)
                            .height(46.dp)
                            .background(color = Color(0xFFD1D1D1), shape = RoundedCornerShape(size = 38.dp))
                    ) {
                        Text(text = "Create",
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontFamily = FontFamily(Font(R.font.spartan_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF979797)
                            )
                        )
                    }
                }
            }
        }

    }
}