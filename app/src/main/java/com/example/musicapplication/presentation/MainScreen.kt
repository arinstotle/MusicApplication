package com.example.musicapplication.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicapplication.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = Constants.PROFILE_HEADER) })
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                }
            )
        }
    ){values ->
        Text(modifier = Modifier.padding(values), text = "aaa")
    }
}