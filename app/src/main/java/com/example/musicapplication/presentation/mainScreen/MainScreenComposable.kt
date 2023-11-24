package com.example.musicapplication.presentation.mainScreen

import androidx.compose.animation.core.*
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicapplication.R
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.theme.DarkBackground
import com.example.musicapplication.presentation.viewModels.MainScreenViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    var isLoading by remember {
        mutableStateOf(viewModel.isLoading.value)
    }

    val rooms by viewModel.allRooms.collectAsState()

    LaunchedEffect(key1 = true) {
        delay(4000)
        isLoading = false
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    GreetingSection(navController, iconClick = {
                        navController.navigate(Screen.SearchScreen.route)
                    },
                        name = viewModel.userState.value.data?.name ?: "User"
                    )
                }
                if(rooms is UiState.Success<*>){
                    RoomSection(
                        isLoading = isLoading,
                        rooms = (rooms.data as List<RoomItem>).map {
                            Room(
                                it.roomName,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.onPrimary,
                                MaterialTheme.colorScheme.surface
                            )
                        }.toList()
                    )
                }
                else{
                    Box(modifier = Modifier.fillMaxSize()
                        .background(DarkBackground)) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )
    background(
        shape = RoundedCornerShape(20.dp),
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.tertiaryContainer,
                MaterialTheme.colorScheme.onTertiaryContainer,
                MaterialTheme.colorScheme.tertiary,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun GreetingSection(
    navController: NavController,
    name: String = "John",
    imageResource: Int = R.drawable.sample_avatar,
    iconClick: () -> Unit = {

    }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                    )
                    .clickable(
                        enabled = true,
                        onClick = {
                            navController.navigate(Screen.ProfileScreen.route)
                        }
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.sample_avatar),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    "Hello, $name!",
                    Modifier.padding(top = 8.dp),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Listen and Enjoy!",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = iconClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search),
                    contentDescription = "SearchButton",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RoomCard(
    room: Room
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(room.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())
        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = room.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = room.lightColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = room.title,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = "Enter",
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RoomSection(rooms: List<Room>, isLoading : Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            color = MaterialTheme.colorScheme.onTertiary,
            text = "My Rooms",
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                color = MaterialTheme.colorScheme.onTertiary
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp)
        ) {
            items(rooms.size) {
                ShimmerListItem(isLoading = isLoading, contentAfterLoading = {
                    RoomCard(room = rooms[it])
                })
            }
        }
    }
}

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Column(modifier =
        modifier
            .fillMaxWidth()
            .padding(7.5.dp)
            .aspectRatio(1f)
            .background(Color.Gray, shape = RoundedCornerShape(10.dp))
            .shimmerEffect()
        ) {

        }
    } else {
        contentAfterLoading()
    }
}

data class Room(
    val title: String,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color
)
fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        Math.abs(from.x + to.x) / 2f,
        Math.abs(from.y + to.y) / 2f
    )
}

//val mockedRooms = listOf(
//    Room(
//        title = "Room1",
//        MaterialTheme.colorScheme.secondary,
//        MaterialTheme.colorScheme.onPrimary,
//        MaterialTheme.colorScheme.surface
//    ),
//    Room(
//        title = "Room2",
//        MaterialTheme.colorScheme.secondary,
//        MaterialTheme.colorScheme.onPrimary,
//        MaterialTheme.colorScheme.surface
//    ),
//    Room(
//        title = "Room3",
//        MaterialTheme.colorScheme.secondary,
//        MaterialTheme.colorScheme.onPrimary,
//        MaterialTheme.colorScheme.surface
//    ),
//    Room(
//        title = "Room4",
//        MaterialTheme.colorScheme.secondary,
//        MaterialTheme.colorScheme.onPrimary,
//        MaterialTheme.colorScheme.surface
//    )
//)