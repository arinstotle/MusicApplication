package com.example.musicapplication.presentation

import android.graphics.drawable.Icon
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapplication.R
import com.example.musicapplication.model.AudioItem
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.ui.theme.DarkBackground
import com.example.musicapplication.ui.theme.SearchFieldBackground
import com.example.musicapplication.ui.theme.TextGrey
import com.example.musicapplication.ui.theme.TextWhite
import com.example.musicapplication.utils.Constants
import com.google.android.material.search.SearchBar

@Composable
fun SearchScreenFragment() {
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showBackground = true)
fun SearchField() {

    var searchQuery = remember {
        mutableStateOf("")
    }


    Card(modifier = Modifier
        .height(50.dp)
        .fillMaxWidth()
        .padding(start = 22.dp, end = 22.dp),
        shape = RoundedCornerShape(6.dp)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)){
            Row(modifier = Modifier.background(DarkBackground)){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(modifier = Modifier
                        .height(18.dp)
                        .width(18.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "search")
                }
                TextField(modifier = Modifier.background(DarkBackground),
                    value = "",
                    onValueChange = {searchQuery.value = it},
                    colors = TextFieldDefaults.textFieldColors())
            }
        }
    }
}
@Composable
//@Preview(showBackground = true)
fun CreateRoomButton(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(DarkBackground)
        .padding(start = 22.dp),
        verticalAlignment = Alignment.CenterVertically){
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "add",
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(TextGrey, shape = RoundedCornerShape(5.dp)),
        )
        Text(
            text = "Create room",
            modifier = Modifier
                .padding(start = 11.dp),
            color = TextGrey,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.spartan_regular, FontWeight.Normal))
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SearchAudioFragment(list: List<AudioItem> = Constants.mockedAudioList){
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(DarkBackground)
        .padding(start = 20.dp, end = 20.dp, top = 28.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)) {
        itemsIndexed(list){_, item  ->
            AudioItemView(item = item)
        }
    }
}

@Composable
//@Preview(showBackground = true)
fun SearchUsersFragment(list: List<UserItem> = Constants.mockedUserList){
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(DarkBackground)
        .padding(start = 20.dp, end = 20.dp, top = 28.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)) {
        itemsIndexed(list){_, item  ->
            UserItemView(item = item)
        }
    }
}

@Composable
//@Preview(showBackground = true)
fun AudioItemView(item:AudioItem = Constants.mockedAudio){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(34.dp)
        .background(DarkBackground),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(0.dp)
    ){
        Row(
            Modifier
                .background(DarkBackground)
                .fillMaxSize()){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.coverUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_default_audio_cover),
                contentDescription = "poster",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Column(){
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(start = 9.dp),
                    color = TextWhite,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_semibold, FontWeight.Normal))
                    )
                )
                Text(
                    text = item.artist,
                    modifier = Modifier
                        .padding(start = 9.dp),
                    color = TextGrey,
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.spartan_semibold, FontWeight.Normal))
                    )
                )
            }
            Text(
                text = "${(item.duration/60000).toInt()}:${((item.duration%60000)/1000).toInt()}",
                modifier = Modifier
                    .padding(start = 9.dp),
                color = TextWhite,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.spartan_semibold, FontWeight.Normal))
                )
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "add",
            )

        }
    }
}

@Composable
//@Preview(showBackground = true)
fun UserItemView(item:UserItem = Constants.mockedUser){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(34.dp)
        .background(DarkBackground),
        shape = RectangleShape
    ){
        Row(
            Modifier
                .background(DarkBackground)
                .fillMaxSize()){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.photoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_default_profile_photo),
                contentDescription = "poster",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(34.dp)
                    .height(34.dp)
                    .clip(CircleShape)
            )
            Text(
                text = item.nickname,
                modifier = Modifier
                    .padding(start = 9.dp),
                color = TextWhite,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.spartan_bold, FontWeight.Normal))
                )
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "clear",
            )


        }
    }
}

