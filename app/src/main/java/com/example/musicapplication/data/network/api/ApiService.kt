package com.example.musicapplication.data.network.api

import com.example.musicapplication.data.network.dto.request.UserRequest
import com.example.musicapplication.data.network.dto.response.UserResponse
import com.example.musicapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
    suspend fun me(
        @Header(Constants.AUTH_TOKEN) token:String
    ): Response<UserResponse>

}