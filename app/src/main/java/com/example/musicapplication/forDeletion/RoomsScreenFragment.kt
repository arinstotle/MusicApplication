package com.example.musicapplication.forDeletion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapplication.R
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.theme.BlueCard
import com.example.musicapplication.theme.DarkBackground
import com.example.musicapplication.theme.GreenCard
import com.example.musicapplication.theme.OrangeCard
import com.example.musicapplication.theme.PinkCard
import com.example.musicapplication.theme.PurpleCard
import com.example.musicapplication.theme.TextWhite
import com.example.musicapplication.theme.YellowCard
import com.example.musicapplication.utils.Constants

@Composable
@Preview(showBackground = true)
fun RoomsScreenFragment(list: List<RoomItem> = Constants.mockedRoomList) {
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(DarkBackground)
        .padding(start = 20.dp, end = 20.dp, top = 28.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp)) {
        itemsIndexed(list){_, item  ->
            RoomItemView(item = item)
        }
    }
}

var k = 0

@Composable
fun RoomItemView(item:RoomItem = Constants.mockedRoom){

    val colors = listOf(PinkCard, PurpleCard, BlueCard, GreenCard,
        YellowCard, OrangeCard)


    Card(modifier = Modifier
        .fillMaxWidth()
        .height(112.dp),
        shape = RoundedCornerShape(17.dp)
    ){
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(colors[k %colors.size], colors[(k + 1) % colors.size])),
                    alpha = 0.8f
                ))
        {
            Text(modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 19.dp),
                text = item.roomName,
                color = TextWhite,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily(Font(R.font.spartan_extrabold, FontWeight.Normal))
                )
            )
        }
    }
    SideEffect {
        k++
    }
}