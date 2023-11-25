/*
package com.example.musicapplication.chatioandroid.data.api.socketio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.chatioandroid.data.model.response.ApiResponse
import com.example.musicapplication.chatioandroid.data.model.response.ChatListResponse
import com.example.musicapplication.chatioandroid.data.model.response.MessageResponseSocket
import com.example.musicapplication.chatioandroid.data.repositories.chat.ChatRepository
import com.example.musicapplication.chatioandroid.extensions.emitEventFlowResults
import com.example.musicapplication.chatioandroid.extensions.emitFlowResults
import com.example.musicapplication.chatioandroid.utils.DataState
import com.example.musicapplication.chatioandroid.utils.livedata.Event




import com.kakyiretechnologies.chatioandroid.data.model.response.MessagesListResponse





import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


*/
/**
 * @author Kakyire
 * Created by Daniel Frimpong on October 28, 2022.
 * https://github.com/kakyire
 *//*

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {


    private val _sentMessage = MutableLiveData<Event<DataState<ApiResponse<MessageResponseSocket>>>>()
    val sentMessage: LiveData<Event<DataState<ApiResponse<MessageResponseSocket>>>> = _sentMessage


    private val _messages = MutableLiveData<Event<DataState<ApiResponse<MessagesListResponse>>>>()
    val messages: LiveData<Event<DataState<ApiResponse<MessagesListResponse>>>> = _messages


    private val _chats = MutableLiveData<DataState<ApiResponse<ChatListResponse>>>()
    val chats: LiveData<DataState<ApiResponse<ChatListResponse>>> = _chats

    fun sendMessage (messageModelRequest: MessageModelRequest)=emitEventFlowResults(_sentMessage){
        repository.sendMessage(messageModelRequest)
    }

    fun getMessages(chatId:String)=emitEventFlowResults(_messages){repository.getMessages(chatId)}

    fun getChats()=emitFlowResults(_chats){repository.getChats()}

}*/
