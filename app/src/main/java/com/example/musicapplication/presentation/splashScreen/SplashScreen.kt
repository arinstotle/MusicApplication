package com.example.musicapplication.presentation.splashScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapplication.R
import com.example.musicapplication.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(brush: Brush, navController: NavController) {
    var animated by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(initialValue = 360f) }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        delay(350)
        visible = !visible
        delay(500)
        animated = !animated
        delay(1800)
        visible = !visible
        navController.navigate(Screen.AuthScreen.route)
    }
    LaunchedEffect(animated) {
        rotation.animateTo(
            targetValue = if (animated) 0f else 360f,
            animationSpec = tween(durationMillis = 1000),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.End)
                    + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
                    + shrinkHorizontally() + fadeOut(),
        ) {
            Card(
                modifier = Modifier
                    .size(170.dp)
                    .graphicsLayer {
                        rotationY = rotation.value
                    }
                    .drawWithContent {
                        drawContent()
                        drawRoundRect(brush = brush,
                            cornerRadius = CornerRadius(20f, 20f)
                        )
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.mts),
                        contentDescription = "",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
fun LogoWithShimmer(navController: NavController) {
    val gradient = listOf(
        Color.White.copy(alpha = 0.2f),
        Color.White.copy(alpha = 0.6f),
        Color.White.copy(alpha = 0.2f)
    )
    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        ), label = ""
    )
    val brush = linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(x = translateAnimation.value,
            y = translateAnimation.value)
    )
    SplashScreen(brush = brush, navController)
}