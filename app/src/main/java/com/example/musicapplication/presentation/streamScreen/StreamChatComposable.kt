package com.example.musicapplication.presentation.streamScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapplication.R
import com.example.musicapplication.chatioandroid.data.api.socketio.ChatViewModel
import com.example.musicapplication.model.MessageItem
import com.example.musicapplication.model.MessageState
import com.example.musicapplication.model.MessageType
import com.example.musicapplication.presentation.theme.MtsBackgroundGreyDark2


private const val TAG = "ChatDetails"

//@AndroidEntryPoint
//class StreamChatComposable : Fragment(R.layout.fragment_chat_details) {
//
//    private lateinit var binding: FragmentChatDetailsBinding
//
//    private val args by navArgs<ChatDetailsFragmentArgs>()
//
//    private val chatViewModel by viewModels<ChatViewModel>()
//
//    private var chatId: String? = null
//
//    @Inject
//    lateinit var preferenceManager: PreferenceManager
//
//    @Inject
//    lateinit var socketIOUtils: SocketIOUtils
//
//    @Inject
//    lateinit var messagesListAdapter: MessagesListAdapter
//
//    private val currentUserId by lazy {
//        preferenceManager.getString(Keys.USER_ID)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentChatDetailsBinding.bind(view)
//        observeViewModelCallbacks()
//        chatId = args.chatId
//
//
//        setupRecyclerView()
//        clickListeners()
//        onTextChangeListener()
//        observeViewModelCallbacks()
//
//
//        observeSocketEvents()
//
//
//    }
//
//    private fun observeSocketEvents() {
//        socketIOUtils.apply {
//            onEventReceive(
//                CHAT_MESSAGES_EVENT, this@ChatDetailsFragment,
//                MessagesListResponse::class.java
//            ) {
//                loadMessages(it.messages)
//                Timber.tag(TAG).d("messages: ${Gson().toJson(it.messages)}}")
//
//            }
//
//
//        }
//    }
//
//    private fun loadMessages(messages: List<Message>) {
//        messagesListAdapter.apply {
//            userId = currentUserId
//            submitList(messages)
//        }
//
//
//    }
//
//    private fun onTextChangeListener() = with(binding) {
//        edtMessage.doAfterTextChanged { btnSend.isEnabled = !it.isNullOrEmpty() }
//    }
//
//    private fun clickListeners() = with(binding) {
//        btnSend.setOnClickListener {
//            sendMessage()
//        }
//    }
//
//
//    private fun setupRecyclerView() {
//        binding.rvMessages.apply {
//            adapter = messagesListAdapter
////            scrollToPosition(0)
//        }
//        messagesListAdapter.userId = currentUserId
//        messagesListAdapter.registerAdapterDataObserver(object :
//            RecyclerView.AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                super.onItemRangeInserted(positionStart, itemCount)
//                binding.rvMessages.scrollToPosition(positionStart)
//
//                Timber.tag(TAG).d("Position $positionStart")
//
//            }
//        })
//
//    }
//
//    private fun sendMessage() = with(binding) {
//        val message = edtMessage.text.toString().trim()
//
//        if (message.isEmpty()) {
//            toast("You can't send empty message")
//            return@with
//        }
//
//        val messageModelRequest = MessageModelRequest(
//            text = message,
//            receiverId = args.receiverId
//        )
//
//        chatViewModel.sendMessage(messageModelRequest)
//        edtMessage.setText("")
//    }
//
//    private fun observeViewModelCallbacks() = with(chatViewModel) {
//        getMessages()
//        observeLiveDataEvent(messages) {
//            loadMessages(it.messages)
//        }
//
//        observeLiveDataEvent(sentMessage) {
//            socketIOUtils.sendEvent(CHAT_MESSAGES_EVENT, it)
//            Timber.tag(TAG).d("sent message $it")
//
//        }
//    }
//
//    private fun getMessages() = with(chatViewModel) {
//        chatId?.let { getMessages(it) }
//    }
//}
val messages = listOf(
    MessageItem("Комната комната1 создана", "System", -1, "12:30", false, MessageType.ROOM_CREATED),
    MessageItem("Шашлык вошел в чат", "System", -1, "12:32", false, MessageType.USER_ENTERED),
    MessageItem("Помидорка вошел в чат", "System", -1, "12:32", false, MessageType.USER_ENTERED),
    MessageItem("Привет, как дела?", "Шашлык", 1, "12:33", true, MessageType.TEXT_MESSAGE),
    MessageItem("Привет, у меня все отлично!", "Помидорка", 2, "12:35", false, MessageType.TEXT_MESSAGE),
    MessageItem("Отправляю тебе фото с вечеринки", "Помидорка", 1, "13:00", true, MessageType.TEXT_MESSAGE),
    MessageItem("Привет, как дела?", "Шашлык", 1, "12:33", true, MessageType.TEXT_MESSAGE),
    MessageItem("Привет, у меня все отлично!", "Помидорка", 2, "12:35", false, MessageType.TEXT_MESSAGE),
    MessageItem("Отправляю тебе фото с вечеринки", "Помидорка", 1, "13:00", true, MessageType.TEXT_MESSAGE)
)


@Composable
fun StreamChatComposable(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(bottom = 25.dp),
            state = lazyListState,
            contentPadding = PaddingValues(bottom = 60.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            items(messages.size) { message ->
                when (messages[message].type) {
                    MessageType.ROOM_CREATED, MessageType.KICK_USER, MessageType.USER_LEFT,
                    MessageType.USER_ENTERED -> {
                        SystemMessage(message = messages[message])
                    }
                    MessageType.TEXT_MESSAGE -> {
                        val messageContent = @Composable {
                            when (messages[message].type) {
                                MessageType.TEXT_MESSAGE -> {
                                    TextMessage(
                                        message = messages[message],
                                        onClickMessage = { },
                                        onLongClickMessage = { }
                                    )
                                }

                                else -> {
                                    throw IllegalArgumentException()
                                }
                            }
                        }
                        if (messages[message].isOwnMessage) {
                            OwnMessageContainer(
                                message = messages[message],
                                messageContent = messageContent
                            )
                        } else {
                            FriendMessageContainer(
                                message = messages[message],
                                showPartDetail = true,
                                messageContent = messageContent
                            )
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.primary)
        ) {
            ChatRow {}
        }
    }
}

@Composable
fun ChatRow(sendMessageClick: () -> Unit) {
    var message by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.mts_wide_medium))
            ),
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text("Type a message",
                fontFamily = FontFamily(Font(R.font.mts_wide_medium))
                ) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                unfocusedContainerColor = MtsBackgroundGreyDark2
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {

                }
            )
        )
        Button(
            onClick = sendMessageClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Send",
                fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}


@Composable
private fun OwnMessageContainer(
    message: MessageItem,
    messageContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 14.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        StateMessage(messageState = message.messageState)
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Top)
                .weight(weight = 1f, fill = false),
            contentAlignment = Alignment.TopEnd
        ) {
            messageContent()
        }
    }
}

@Composable
private fun FriendMessageContainer(
    message: MessageItem,
    showPartDetail: Boolean,
    messageContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 0.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if (showPartDetail) {
            AsyncImage(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(size = 38.dp)
                    .clip(shape = CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
                   ,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.mts_avatar)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mts_avatar),
                contentDescription = "chat_photo",
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            modifier = Modifier
        ) {
            if (showPartDetail) {
                Text(
                    modifier = Modifier.padding(end = 30.dp),
                    text = message.senderName,
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 3.dp)
                    .fillMaxWidth(fraction = 0.90f)
                    .wrapContentWidth(align = Alignment.Start),
                contentAlignment = Alignment.TopStart
            ) {
                messageContent()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TextMessage(
    message: MessageItem,
    onClickMessage: (MessageItem) -> Unit,
    onLongClickMessage: (MessageItem) -> Unit
) {
    Text(
        modifier = Modifier
            .clip(
                shape = if (message.isOwnMessage) {
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 6.dp,
                        bottomEnd = 20.dp,
                        bottomStart = 20.dp
                    )
                } else {
                    RoundedCornerShape(
                        topStart = 6.dp,
                        topEnd = 20.dp,
                        bottomEnd = 20.dp,
                        bottomStart = 20.dp
                    )
                }
            )
            .background(
                color = if (message.isOwnMessage) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.inverseSurface
                }
            )
            .combinedClickable(
                onClick = {
                    onClickMessage(message)
                },
                onLongClick = {
                    onLongClickMessage(message)
                }
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        text = message.contents,
        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
        fontSize = 16.sp,
        color = if (message.isOwnMessage) {
            Color.White
        } else {
            MaterialTheme.colorScheme.inverseOnSurface
        },
        textAlign = TextAlign.Start
    )
}

@Composable
private fun SystemMessage(message: MessageItem) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .background(
                color = MtsBackgroundGreyDark2,
                shape = RoundedCornerShape(size = 20.dp)
            )
            .padding(all = 4.dp),
        text = message.contents,
        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
        fontSize = 12.sp
    )
}

@Composable
private fun RowScope.StateMessage(messageState: MessageState) {
    Box(
        modifier = Modifier
            .align(alignment = Alignment.CenterVertically)
            .padding(end = 8.dp)
            .size(size = 20.dp)
    ) {
        when (messageState) {
            MessageState.Sending -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            }
            is MessageState.SendFailed -> {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Outlined.Warning,
                    colorFilter = ColorFilter.tint(color = Color.Red),
                    contentDescription = null
                )
            }
            MessageState.Completed -> {
            }
            else -> {}
        }
    }
}
