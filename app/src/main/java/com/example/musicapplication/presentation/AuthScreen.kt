package com.example.musicapplication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.decode.ImageSource
import com.example.musicapplication.R
import com.example.musicapplication.ui.theme.AuthDarkBlue
import com.example.musicapplication.ui.theme.AuthLightBlue
import com.example.musicapplication.ui.theme.DarkBackground

@Composable
fun AuthScreen() {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun AuthScreenFragment(){
    Column(modifier = Modifier
        .background(DarkBackground)
        .fillMaxWidth()){
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(DarkBackground),
            shape = RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp)
        ){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(AuthLightBlue, AuthDarkBlue)),
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
            .height(310.dp)
            .fillMaxWidth(),
            shape = RectangleShape){
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xBF292929), shape = RoundedCornerShape(size = 0.dp))
                .padding(start = 16.dp, top = 25.dp, end = 16.dp)){
                Text(text = "Login",
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF))
                )
                TextField(value = "Your e-mail",
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp)
                    .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    onValueChange = {},
                    textStyle = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(R.font.spartan_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000)
                    )
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
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
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier
                        .width(176.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start) {
                        Text(text = "No account?",
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.spartan_bold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFFFFFFFF),))
                        Text(text = "Registration",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.spartan_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF0984E3),

                                ))
                    }
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .background(color = Color(0xFFD1D1D1), shape = RoundedCornerShape(size = 38.dp))
                    ) {
                        Text(text = "Login",
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontFamily = FontFamily(Font(R.font.spartan_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF979797),

                                )
                        )
                    }
                }
            }
        }

    }
}
