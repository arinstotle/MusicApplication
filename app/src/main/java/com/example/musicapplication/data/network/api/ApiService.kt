package com.example.musicapplication.data.network.api

import com.example.musicapplication.data.network.dto.request.RoomRequest
import com.example.musicapplication.data.network.dto.request.UserRequest
import com.example.musicapplication.data.network.dto.response.RoomResponse
import com.example.musicapplication.data.network.dto.response.UserResponse
import com.example.musicapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST(Constants.PATH_LOGIN)
    suspend fun login(
        @Body user: UserRequest
    ):Response<UserResponse>

    @POST(Constants.PATH_SIGNUP)
    suspend fun signup(
        @Body user: UserRequest
    ):Response<UserResponse>

    @GET(Constants.PATH_ME)
    suspend fun me(): Response<UserResponse>

    @GET(Constants.PATH_ROOMS)
    suspend fun allRoomsFromServer(): Response<List<RoomResponse>>

    @POST(Constants.PATH_ROOMS)
    suspend fun createRoomInServer(
        @Body body: RoomRequest
    ): Response<RoomResponse>

    @POST("/rooms/{id}")
    suspend fun joiningARoomInServer(@Path("id") id: Int): Response<RoomResponse>

    @GET("${Constants.PATH_ROOMS_WITH_ID}{id}")
    suspend fun getRoomInfoFromServerById( @Path("id") id: Int): Response<RoomResponse>

    @GET("${Constants.PATH_USERS}{id}${Constants.PATH_ROOMS}")
    suspend fun getUserRoomsFromServer(@Path("id") id: Int): Response<List<RoomResponse>>
}