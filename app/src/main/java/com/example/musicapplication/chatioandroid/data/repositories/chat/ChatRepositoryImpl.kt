package com.example.musicapplication.chatioandroid.data.repositories.chat

import com.example.musicapplication.chatioandroid.data.api.socketio.ApiServiceSocket
import com.example.musicapplication.chatioandroid.data.model.request.MessageModelRequest
import com.example.musicapplication.chatioandroid.data.model.response.ApiResponse
import com.example.musicapplication.chatioandroid.data.model.response.ChatListResponse
import com.example.musicapplication.chatioandroid.data.model.response.MessageResponseSocket
import com.kakyiretechnologies.chatioandroid.data.model.response.*
import com.example.musicapplication.chatioandroid.utils.DataState
import com.example.musicapplication.chatioandroid.utils.makeNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * @author Kakyire
 * Created by Daniel Frimpong on October 28, 2022.
 * https://github.com/kakyire
 */
class ChatRepositoryImpl @Inject constructor(private val apiService: ApiServiceSocket) :
    ChatRepository {
    override fun sendMessage(messageModelRequest: MessageModelRequest): Flow<DataState<ApiResponse<MessageResponseSocket>>> {
        return makeNetworkCall { apiService.sendMessage(messageModelRequest) }
    }

    override fun getMessages(chatId: String): Flow<DataState<ApiResponse<MessagesListResponse>>> {
        return makeNetworkCall { apiService.getMessages(chatId) }
    }

    override fun getChats(): Flow<DataState<ApiResponse<ChatListResponse>>> {
        return makeNetworkCall { apiService.getChats() }
    }
}