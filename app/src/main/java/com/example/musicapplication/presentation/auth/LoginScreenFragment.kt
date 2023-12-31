package com.example.musicapplication.presentation.auth

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapplication.R
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.presentation.theme.AuthDarkBlue
import com.example.musicapplication.presentation.theme.AuthLightBlue
import com.example.musicapplication.presentation.theme.DarkBackground
import com.example.musicapplication.presentation.theme.TextWhite
import com.example.musicapplication.utils.ConnectivityObserver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenFragment(
    authState: AuthState,
    connectionState:ConnectivityObserver.Status,
    onLogin: (authState:AuthState) -> Unit,
    goToRegister:() -> Unit
    ){
    val context = LocalContext.current
    val email = remember {
        mutableStateOf(authState.user.email)
    }
    val password = remember {
        mutableStateOf(authState.user.password)
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val isEmailEmpty = remember {
        mutableStateOf(false)
    }
    val isPasswordEmpty = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.primary)
        .fillMaxWidth()
        .padding(start = 14.dp, end = 14.dp, bottom = 40.dp)){
        Card(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(110.dp),
            shape = RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp)
        ){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.outlineVariant)),
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
            .wrapContentHeight()
            .fillMaxWidth(),
            shape = RectangleShape
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(size = 0.dp))
                .padding(start = 16.dp, top = 25.dp, end = 16.dp)){
                Text(text = "Login",
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )
                TextField(value = email.value,
                    label = {Text(text = "Your email",
                        fontFamily = FontFamily(Font(R.font.mts_wide_regular)),
                        )},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 17.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(5.dp),
                    onValueChange = {
                        email.value=it
                        if(it.isNotBlank()) isEmailEmpty.value = false
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.mts_wide_regular)),
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = if (!authState.isWrongData && !isEmailEmpty.value) {
                        TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            errorLabelColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.onTertiary,
                            errorCursorColor = MaterialTheme.colorScheme.onTertiary
                        )
                    } else {
                        TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedTextColor = Color.Red,
                            focusedTextColor = Color.Red,
                            disabledTextColor = Color.Red,
                            unfocusedLabelColor = Color.Red,
                            focusedLabelColor = Color.Red,
                            disabledLabelColor = Color.Red,
                            errorLabelColor = Color.Red,
                            cursorColor = MaterialTheme.colorScheme.onTertiary,
                            errorCursorColor = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .wrapContentHeight(),
                    shape = RoundedCornerShape(5.dp),
                    value = password.value,
                    label = {Text(text = "Password",
                        fontFamily = FontFamily(Font(R.font.mts_wide_regular)),
                        )},
                    onValueChange = {
                        password.value=it
                        if(it.isNotBlank()) isPasswordEmpty.value = false
                                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.mts_wide_regular)),
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if(passwordVisibility.value){
                            IconButton(onClick = {
                                passwordVisibility.value=!passwordVisibility.value
                            }) {
                                Image(painter = painterResource(id = R.drawable.ic_visibility_on),
                                    contentDescription = "visible")
                            }
                        }
                        else{
                            IconButton(onClick = {
                                passwordVisibility.value=!passwordVisibility.value
                            }
                            ) {
                                Image(painter = painterResource(id = R.drawable.ic_visibility_off),
                                    contentDescription = "invisible")
                            }
                        }
                    },
                    visualTransformation =
                    if(passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    colors = if (!authState.isWrongData && !isPasswordEmpty.value) {
                        TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            errorLabelColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.onTertiary,
                            errorCursorColor = MaterialTheme.colorScheme.onTertiary
                            )
                    } else {
                        TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedTextColor = Color.Red,
                            focusedTextColor = Color.Red,
                            disabledTextColor = Color.Red,
                            unfocusedLabelColor = Color.Red,
                            focusedLabelColor = Color.Red,
                            disabledLabelColor = Color.Red,
                            errorLabelColor = Color.Red,
                            cursorColor = MaterialTheme.colorScheme.onTertiary,
                            errorCursorColor = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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
                                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                                fontWeight = FontWeight(700),
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        )
                        ClickableText(text = AnnotatedString("Registration"),
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.mts_wide_regular)),
                                fontWeight = FontWeight(400),
                                color = MaterialTheme.colorScheme.onPrimary),
                            onClick = {
                                goToRegister()
                            }
                        )
                    }
                    TextButton(onClick = {
                        isEmailEmpty.value = email.value.isBlank()
                        isPasswordEmpty.value = password.value.isBlank()
                        if(!isEmailEmpty.value && !isPasswordEmpty.value){
                            if(connectionState== ConnectivityObserver.Status.Available) {
                                onLogin(
                                    AuthState(
                                        user = UserItem(
                                            id = 0,
                                            photoUrl = null,
                                            name = "",
                                            email = email.value,
                                            password = password.value
                                        ),
                                        isAuthorized = false,
                                        isWrongData = false,
                                        isCreated = true
                                    )
                                )
                            }
                            else{
                                Toast.makeText(context,
                                    "Oops! Bad internet connection, try later", Toast.LENGTH_SHORT).show()
                            }
                        } },
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(size = 38.dp)
                            )
                    ) {
                        Text(text = "Login",
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                                fontWeight = FontWeight(400),
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        )
                    }
                }
            }
        }

    }
}
