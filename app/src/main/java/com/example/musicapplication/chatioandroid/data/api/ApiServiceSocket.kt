package com.example.musicapplication.chatioandroid.data.api.socketio

import com.example.musicapplication.chatioandroid.data.model.request.MessageModelRequest
import com.example.musicapplication.chatioandroid.data.model.response.ApiResponse
import com.example.musicapplication.chatioandroid.data.model.response.ChatListResponse
import com.example.musicapplication.chatioandroid.data.model.response.MessageResponseSocket
import com.kakyiretechnologies.chatioandroid.data.model.response.MessagesListResponse
import com.example.musicapplication.chatioandroid.utils.CHATS_ROUTE
import com.example.musicapplication.chatioandroid.utils.MESSAGES_ROUTE
import com.example.musicapplication.chatioandroid.utils.SEND_MESSAGE_ROUTE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiServiceSocket {

    @POST(SEND_MESSAGE_ROUTE)
    suspend fun sendMessage(@Body messageModelRequest: MessageModelRequest): Response<ApiResponse<MessageResponseSocket>>

    @GET(MESSAGES_ROUTE)
    suspend fun getMessages(@Path("chatId") chatId: String): Response<ApiResponse<MessagesListResponse>>

    @GET(CHATS_ROUTE)
    suspend fun getChats(): Response<ApiResponse<ChatListResponse>>


}