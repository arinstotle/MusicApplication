package com.example.musicapplication.presentation.searchRoomScreen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.musicapplication.R
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.navigation.NavigationRouter
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.mainScreen.ShimmerListItem
import com.example.musicapplication.presentation.mainScreen.standardQuadFromTo
import com.example.musicapplication.presentation.streamScreen.roommates
import com.example.musicapplication.presentation.theme.DarkRed
import com.example.musicapplication.presentation.theme.MtsBackgroundBlack
import com.example.musicapplication.presentation.theme.MtsRed
import com.example.musicapplication.presentation.theme.MtsTextWhite
import com.example.musicapplication.presentation.theme.VeryDarkRed
import com.example.musicapplication.presentation.viewModels.SearchScreenViewModel
import com.example.musicapplication.utils.ConnectivityObserver
import kotlinx.coroutines.launch
import javax.annotation.Untainted

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val currentRoom by viewModel.currentRoom.collectAsState()
    val enteringPassword by viewModel.enteringPassword.collectAsState()
    val rooms by viewModel.allRooms.collectAsState()
    val loadingState by viewModel.isLoading.collectAsState(initial = false)
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val connectionState = viewModel.connectionState.collectAsState()
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val colors: TextFieldColors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
            disabledTextColor = MaterialTheme.colorScheme.onTertiary,
            cursorColor = MaterialTheme.colorScheme.onTertiary,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
        val options = listOf("None", "A-Z", "Z-A")
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        val context = LocalContext.current
        if(viewModel.isEnterDialogShown && currentRoom != null) {
            CustomEnterDialog(
                isPrivateRoomOrNo = currentRoom!!.isPrivate,
                onDismiss = {
                    viewModel.onDismissEnterDialog()
                    viewModel.unsetEnteringRoom()
                    viewModel.unsetEnteringRoomPassword()
                },
                onConfirm = {
                    val lambda: (RoomItem) -> Unit = { roomItem ->
                        coroutineScope.launch {
                            viewModel.enterToTheRoom(roomItem).collect { enterUiState ->
                                when (enterUiState) {
                                    is UiState.Success -> {
                                        Toast.makeText(
                                            context,
                                            "Entering...",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        viewModel.onDismissEnterDialog()
                                        viewModel.unsetEnteringRoomPassword()
                                        NavigationRouter.prevScreen.value = Screen.SearchScreen
                                        navController.navigate(
                                            Screen.StreamScreen.withArgs(
                                                ((enterUiState.data
                                                        as RoomItem).id).toString()
                                            )
                                        )
                                    }
                                    is UiState.Error -> {
                                        Toast.makeText(
                                            context,
                                            "Not entering, try again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                    }
                    if (currentRoom!!.isPrivate) {
                        if (currentRoom!!.password != enteringPassword) {
                            Toast.makeText(context,
                                "Wrong password!",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            lambda.invoke(currentRoom!!)
                        }
                    } else {
                        lambda.invoke(currentRoom!!)
                    }
                },
                changePassword = { password ->
                    viewModel.changeEnteringRoomPassword(password)
                }
            )
        }
        if(viewModel.isDialogShown) {
            CustomDialog(
                onDismiss = {
                    viewModel.onDismissDialog()
                    viewModel.unsetRoomPassword()
                    viewModel.unsetRoomName()
                    viewModel.unsetRoomType()
                },
                onConfirm = {
                    coroutineScope.launch {
                        viewModel.addRoom().collect { uiState ->
                            when (uiState) {
                                is UiState.Success -> {
                                    Toast.makeText(context,
                                        "A new room has been successfully created!",
                                        Toast.LENGTH_SHORT).show()
                                    viewModel.onDismissDialog()
                                    viewModel.enterToTheRoom(uiState.data
                                            as RoomItem).collect { enterUiState ->
                                        when(enterUiState) {
                                            is UiState.Success -> {
                                                Toast.makeText(context,
                                                    "Entering...",
                                                    Toast.LENGTH_SHORT).show()
                                                viewModel.onDismissDialog()
                                                viewModel.unsetRoomPassword()
                                                viewModel.unsetRoomName()
                                                viewModel.unsetRoomType()
                                                navController.navigate(Screen.StreamScreen.withArgs(((enterUiState.data
                                                        as RoomItem).id).toString()))
                                            }
                                            is UiState.Error -> {
                                                Toast.makeText(context,
                                                    "Not entering, try again",
                                                    Toast.LENGTH_SHORT).show()
                                            }
                                            else -> {
                                            }
                                        }
                                    }
                                }
                                is UiState.Error -> {
                                    Toast.makeText(context,
                                        "The room was not created...",
                                        Toast.LENGTH_SHORT).show()
                                }
                                else -> {}
                            }
                        }
                    }
                },
                changeName = { roomName ->
                    viewModel.renameRoom(roomName)
                },
                changeType = { isPrivate ->
                    viewModel.changeRoomType(isPrivate)
                },
                changePassword = { password ->
                    viewModel.changeRoomPassword(password)
                }
            )
        }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            androidx.compose.material3.TextField(
                readOnly = true,
                value = selectedOptionText,
                label = {
                    Text(
                        "Order",
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = VeryDarkRed
                    )
                },
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                    ),
                singleLine = true,
                colors = colors,
                shape = RoundedCornerShape(20.dp),
            )
            ExposedDropdownMenu(
                modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary),
                        onClick = {
                            if (selectedOptionText != selectionOption) {
                                when (selectionOption) {
                                    "None" -> viewModel.changeSortType(OrdersTypes.NATURAL)
                                    "A-Z" -> viewModel.changeSortType(OrdersTypes.ALPHABETICAL)
                                    "Z-A" -> viewModel.changeSortType(OrdersTypes.REVERSE_ALPHABETICAL)
                                }
                                coroutineScope.launch {
                                    viewModel.updateRooms().collect { uiState ->
                                        when (uiState) {
                                            is UiState.Success -> {
                                            }
                                            is UiState.Error -> {
                                            }
                                            else -> {
                                            }
                                        }
                                    }
                                }
                            }
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(
                            text = selectionOption,
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            color = MtsBackgroundBlack
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newText -> viewModel.onSearchTextChange(newText) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = VeryDarkRed
                    )
                },
                placeholder = {
                    Text(
                        text = "Search",
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium))
                        )
                },
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium))
                    ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Search
                ),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                colors = colors
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        viewModel.onAddNewRoomClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        if(isSearching) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    trackColor = MtsRed
                )
            }
        } else if(connectionState.value == ConnectivityObserver.Status.Unavailable) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No internet connection!",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        else {
            val lambda: (RoomItem) -> Unit = { roomItem ->
                viewModel.onEnterToTheRoomClick()
                viewModel.setEnteringRoom(roomItem)
            }
            if (rooms is List<*>)
                RoomSectionSearchList(rooms = rooms as List<RoomItem>, isLoading = loadingState,
                    lambda)
            else
                RoomSectionSearch(rooms = rooms as UiState<List<RoomItem>>, isLoading = loadingState,
                    lambda)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RoomSectionSearch(rooms: UiState<List<RoomItem>>, isLoading : Boolean,
                      roomEnterAction: (RoomItem) -> Unit
                      ) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            color = MaterialTheme.colorScheme.onTertiary,
            text = "Rooms",
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                color = MaterialTheme.colorScheme.onTertiary
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(16.dp)
        )
            val data = rooms.data
            when {
                (data is List<RoomItem>) -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            start = 7.5.dp,
                            end = 7.5.dp,
                            bottom = 100.dp
                        )
                    ) {
                        items(rooms.data.size) {
                            ShimmerListItem(isLoading = isLoading, contentAfterLoading = {
                                RoomCardSearch(
                                    room = rooms.data[it],
                                    roomEnterAction
                                )
                            })
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            trackColor = MtsRed
                        )
                    }
                }
            }
        }
}

@ExperimentalFoundationApi
@Composable
fun RoomSectionSearchList(rooms: List<RoomItem>, isLoading : Boolean,
                          roomEnterAction: (RoomItem) -> Unit
                          ) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            color = MaterialTheme.colorScheme.onTertiary,
            text = "My Rooms",
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                color = MaterialTheme.colorScheme.onTertiary
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 7.5.dp,
                end = 7.5.dp,
                bottom = 100.dp
            )
        ) {
            items(rooms.size) {
                ShimmerListItem(isLoading = isLoading, contentAfterLoading = {
                    RoomCardSearch(
                        room = rooms[it],
                        roomEnterAction
                    )
                })
            }
        }
    }
}

@Composable
fun RoomCardSearch(
    room: RoomItem,
    enterAction: (RoomItem) -> Unit
) {
    val mediumColor = MaterialTheme.colorScheme.onPrimary
    val lightColor = MaterialTheme.colorScheme.surface
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary)
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
                color = mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = lightColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = room.roomName,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 26.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildString {
                     append("Number of participants: ")
                        if (room.roommates != null)
                            append(room.roommates.size.toString())
                        else
                            append("0")
                    },
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 26.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildString {
                        append("Tracks in the queue: ")
                        if (room.queue != null)
                            append(room.queue.size.toString())
                        else
                            append("0")
                    },
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 26.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (room.isPrivate) "Private" else "",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 26.sp,
                )
            }
                Text(
                    text = "Enter",
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            enterAction.invoke(room)
                        }
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.outlineVariant)
                        .padding(vertical = 6.dp, horizontal = 15.dp)
                )
            }
        }
    }

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    changeName: (String) -> Unit,
    changeType: (Boolean) -> Unit,
    changePassword: (String) -> Unit
) {
    var isPrivateRoom by remember {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(15.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ){
                Text(
                    text = "Let's create your room!",
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    TextFieldComponent(
                        labelValue = "Name",
                        onChangeTextAction = { name ->
                            changeName.invoke(name)
                        },
                        keyboardOptions = KeyboardOptions.Default,
                        iconResource = {
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Make private:",
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            fontSize = 16.sp
                            )
                        Switch(
                            checked = isPrivateRoom,
                            onCheckedChange = {
                                isPrivateRoom = !isPrivateRoom
                                changeType.invoke(isPrivateRoom)
                            },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = MaterialTheme.colorScheme.secondary,
                                checkedThumbColor = MaterialTheme.colorScheme.onTertiary
                            )
                        )
                    }
                    if (isPrivateRoom) {
                        PasswordTextFieldComponent(labelValue = "Password", onChangeTextAction = changePassword)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                        ,
                        shape = CircleShape
                    ) {
                        Text(
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            text = "Cancel",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            onConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(

                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Confirm",
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CustomEnterDialog(
    isPrivateRoomOrNo: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    changePassword: (String) -> Unit
) {
    val isPrivateRoom by remember {
        mutableStateOf(isPrivateRoomOrNo)
    }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(15.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ){
                Text(
                    text = if (!isPrivateRoom) "Are you sure you want to enter the room?"
                    else
                        "Enter the password:"
                    ,
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                if (isPrivateRoom) {
                    PasswordTextFieldComponent(labelValue = "", onChangeTextAction = {

                    })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                        ,
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            onConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(

                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Confirm",
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(textValueStart : String = "",
                       labelValue : String,
                       onChangeTextAction: (String) -> Unit,
                       keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                       iconResource : @Composable () -> Unit
) {
    val textValue = remember {
        mutableStateOf(textValueStart)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
        ,
        value = textValue.value,
        onValueChange = { textValue.value = it
            onChangeTextAction.invoke(it) },
        shape = RoundedCornerShape(20.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onSecondary,
            fontFamily = FontFamily(Font(R.font.spartan_bold))
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
            focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            cursorColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = MaterialTheme.colorScheme.onSurface
        ),
        label = {
            Text(
                text = labelValue,
                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(passwordStart : String = "",
                               labelValue : String,
                               onChangeTextAction: (String) -> Unit,
                               keyboardOptions: KeyboardOptions = KeyboardOptions.Default, ) {
    val password = remember {
        mutableStateOf(passwordStart)
    }
    val isVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
        ,
        value = password.value,
        onValueChange = {
            password.value = it
            onChangeTextAction.invoke(it) },
        shape = RoundedCornerShape(20.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onSecondary,
            fontFamily = FontFamily(Font(R.font.spartan_bold))
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
            focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            cursorColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = MaterialTheme.colorScheme.onSurface
        ),
        trailingIcon = {
            when(isVisible.value) {
                true -> IconButton(onClick = {
                    isVisible.value = !isVisible.value
                }) {
                    Icon(
                        tint = Color.Gray,
                        painter = painterResource(id = R.drawable.eye_open_icon),
                        contentDescription = "Show password"
                    )
                }
                false -> IconButton(onClick = {
                    isVisible.value = !isVisible.value
                }) {
                    Icon(
                        tint = Color.Gray,
                        painter = painterResource(id = R.drawable.eye_closed_icon),
                        contentDescription = "Hide password"
                    )
                }
            }
        },
        label = {
            Text(
                text = labelValue,
                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                )
        },
        visualTransformation = if (isVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}