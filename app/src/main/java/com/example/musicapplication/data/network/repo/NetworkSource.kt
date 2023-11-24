package com.example.musicapplication.data.network.repo

import android.util.Log
import com.example.cinopoisk.utils.exceptions.ResponseEmptyException
import com.example.cinopoisk.utils.exceptions.ResponseUnsuccessfulException
import com.example.musicapplication.data.network.api.ApiService
import com.example.musicapplication.data.network.dto.request.toRoomRequest
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.model.toRequest
import com.example.musicapplication.data.network.state.NetworkState
import com.example.musicapplication.data.network.dto.response.toItem
import com.example.musicapplication.data.network.dto.response.toRoomItem
import com.example.musicapplication.data.network.dto.response.toRoomItemList
import com.example.musicapplication.data.network.exceptions.WrongDataException
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.utils.SecurityHelper.getHash
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkSource @Inject constructor(
    private val api: ApiService
){
    suspend fun login(user:UserItem): NetworkState<UserItem> {
        try {
            val response = api.login(user.copy(password = user.password.getHash()).toRequest())
            if(response.isSuccessful){
                if(response.body()!=null){
                    return NetworkState.Result(response.body()!!.toItem())
                }
                else{
                    throw ResponseEmptyException("Empty body")
                }
            }
            else{
                if(response.code() == 400){
                    throw WrongDataException("Bad request")
                }
                throw ResponseUnsuccessfulException("Unsuccessful response")
            }

        }
        catch (exception: UnknownHostException){
            Log.println(Log.DEBUG, "login response", exception.message?: "UnknownHostException")
            return NetworkState.Exception(exception)
        }
        catch (exception: HttpException){
            Log.println(Log.DEBUG, "login response", exception.message?: "HttpException")
            return NetworkState.Exception(exception)
        }
        catch(unsuccessful: ResponseUnsuccessfulException){
            Log.println(Log.DEBUG, "login response", unsuccessful.info)
            return NetworkState.Exception(unsuccessful)
        }
        catch(empty: ResponseEmptyException){
            Log.println(Log.DEBUG, "login response", empty.info)
            return NetworkState.Exception(empty)
        }
        catch(wrong: WrongDataException){
            Log.println(Log.DEBUG, "login response", wrong.message?:"")
            return NetworkState.Exception(wrong)
        }
        catch (other:Exception) {
            Log.println(Log.DEBUG, "login response", "Exception")
            other.printStackTrace()
            return NetworkState.Exception(other)
        }

    }

    suspend fun signup(user:UserItem): NetworkState<UserItem> {
        try {
            val response = api.signup(user.copy(password = user.password.getHash()).toRequest())

            if(response.isSuccessful){
                if(response.body()!=null){
                    return NetworkState.Result(response.body()!!.toItem())
                }
                else{
                    throw ResponseEmptyException("Empty body")
                }
            }
            else{
                throw ResponseUnsuccessfulException("Unsuccessful response")
            }

        }
        catch (exception: UnknownHostException){
            Log.println(Log.DEBUG, "login response", exception.message?: "UnknownHostException")
            return NetworkState.Exception(exception)
        }
        catch (exception: HttpException){
            Log.println(Log.DEBUG, "login response", exception.message?: "HttpException")
            return NetworkState.Exception(exception)
        }
        catch(unsuccessful: ResponseUnsuccessfulException){
            Log.println(Log.DEBUG, "login response", unsuccessful.info)
            return NetworkState.Exception(unsuccessful)
        }
        catch(empty: ResponseEmptyException){
            Log.println(Log.DEBUG, "login response", empty.info)
            return NetworkState.Exception(empty)
        }
        catch (other:Exception) {
            Log.println(Log.DEBUG, "login response", "Exception")
            other.printStackTrace()
            return NetworkState.Exception(other)
        }

    }

    suspend fun me(): NetworkState<UserItem> {
        try {
            val response = api.me()
            if(response.isSuccessful){
                if(response.body()!=null){
                    return NetworkState.Result(response.body()!!.toItem())
                }
                else{
                    throw ResponseEmptyException("Empty body")
                }
            }
            else{
                throw ResponseUnsuccessfulException("Unsuccessful response")
            }

        }
        catch (exception: UnknownHostException){
            Log.println(Log.DEBUG, "login response", exception.message?: "UnknownHostException")
            return NetworkState.Exception(exception)
        }
        catch (exception: HttpException){
            Log.println(Log.DEBUG, "login response", exception.message?: "HttpException")
            return NetworkState.Exception(exception)
        }
        catch(unsuccessful: ResponseUnsuccessfulException){
            Log.println(Log.DEBUG, "login response", unsuccessful.info)
            return NetworkState.Exception(unsuccessful)
        }
        catch(empty: ResponseEmptyException){
            Log.println(Log.DEBUG, "login response", empty.info)
            return NetworkState.Exception(empty)
        }
        catch (other:Exception) {
            Log.println(Log.DEBUG, "login response", "Exception")
            other.printStackTrace()
            return NetworkState.Exception(other)
        }

    }

    fun getRooms(): Flow<NetworkState<List<RoomItem>>> = flow {
        emit(NetworkState.Initial)
        val response = api.allRoomsFromServer()
        if (response.isSuccessful) {
            if (response.body() != null)
                emit(NetworkState.Result(response.body()!!.toRoomItemList()))
            else {
                throw ResponseEmptyException("Empty body")
            }
        }
        else {
            throw ResponseUnsuccessfulException("Unsuccessful response")
        }
    }.catch {
        emit(NetworkState.Exception(it))
    }

    fun getUserRooms(userId: Int): Flow<NetworkState<List<RoomItem>>?> = flow {
        emit(NetworkState.Initial)
        val response = api.getUserRoomsFromServer(userId)
        if (response.isSuccessful) {
            if (response.body() == null)
                emit(null)
            if (response.body() != null)
                emit(NetworkState.Result(response.body()!!.toRoomItemList()))
            else {
                throw ResponseEmptyException("Empty body")
            }
        }
        else {
            throw ResponseUnsuccessfulException("Unsuccessful response")
        }
    }.catch {
        emit(NetworkState.Exception(it))
    }

    fun createRoom(
        room: RoomItem
    ): Flow<NetworkState<RoomItem>> = flow {
        emit(NetworkState.Initial)
        val response = api.createRoomInServer(room.toRoomRequest())
        if (response.isSuccessful) {
            if (response.body() != null)
                emit(NetworkState.Result(response.body()!!.toRoomItem()))
            else
                throw ResponseEmptyException("Empty body")
        }
        else
            throw ResponseUnsuccessfulException("Unsuccessful response")
    }.catch {
        emit(NetworkState.Exception(it))
    }

    fun joining(
        id: Int
    ): Flow<NetworkState<RoomItem>> = flow {
        emit(NetworkState.Initial)
        val response = api.joiningARoomInServer(
           id = id
        )
        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(NetworkState.Result(response.body()!!.toRoomItem()))
            }
            else
                throw ResponseEmptyException("Empty body")
        }
        else
            throw ResponseUnsuccessfulException("Unsuccessful response")
    }.catch {
        emit(NetworkState.Exception(it))
    }

    fun getRoomInfoById(
        id: Int
    ): Flow<NetworkState<RoomItem>> = flow {
        emit(NetworkState.Initial)
        val response = api.getRoomInfoFromServerById(
            id = id
        )
        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(NetworkState.Result(response.body()!!.toRoomItem()))
            }
            else
                throw ResponseEmptyException("Empty body")
        }
        else
            throw ResponseUnsuccessfulException("Unsuccessful response")
    }.catch {
        emit(NetworkState.Exception(it))
    }

}