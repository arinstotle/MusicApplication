package com.example.musicapplication.chatioandroid.data.repositories.chat


import com.example.musicapplication.chatioandroid.data.model.request.MessageModelRequest
import com.example.musicapplication.chatioandroid.data.model.response.ApiResponse
import com.example.musicapplication.chatioandroid.data.model.response.ChatListResponse
import com.example.musicapplication.chatioandroid.data.model.response.MessageResponseSocket
import com.kakyiretechnologies.chatioandroid.data.model.response.*
import com.example.musicapplication.chatioandroid.utils.DataState
import kotlinx.coroutines.flow.Flow


/**
 * @author Kakyire
 * Created by Daniel Frimpong on October 28, 2022.
 * https://github.com/kakyire
 */
interface ChatRepository {

    fun sendMessage(messageModelRequest: MessageModelRequest):
            Flow<DataState<ApiResponse<MessageResponseSocket>>>
    fun getMessages(chatId: String): Flow<DataState<ApiResponse<MessagesListResponse>>>
    fun getChats(): Flow<DataState<ApiResponse<ChatListResponse>>>

}