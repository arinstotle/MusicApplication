package com.example.musicapplication.data.network.api

import android.util.Log
import com.example.cinopoisk.utils.exceptions.ResponseEmptyException
import com.example.cinopoisk.utils.exceptions.ResponseUnsuccessfulException
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.model.toRequest
import com.example.musicapplication.data.network.state.DataState
import com.example.musicapplication.data.network.dto.response.toItem
import com.example.musicapplication.data.network.exceptions.WrongDataException
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.utils.SecurityHelper
import com.example.musicapplication.utils.SecurityHelper.getHash
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkSource @Inject constructor(
    private val api: ApiService,
    private val sharedPreferencesHelper: SharedPreferencesHelper
){
    suspend fun login(user:UserItem): DataState<UserItem> {
        try {
            val response = api.login(user.copy(password = user.password.getHash()).toRequest())
            if(response.isSuccessful){
                if(response.body()!=null){
                    return DataState.Result(response.body()!!.toItem())
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
            return DataState.Exception(exception)
        }
        catch (exception: HttpException){
            Log.println(Log.DEBUG, "login response", exception.message?: "HttpException")
            return DataState.Exception(exception)
        }
        catch(unsuccessful: ResponseUnsuccessfulException){
            Log.println(Log.DEBUG, "login response", unsuccessful.info)
            return DataState.Exception(unsuccessful)
        }
        catch(empty: ResponseEmptyException){
            Log.println(Log.DEBUG, "login response", empty.info)
            return DataState.Exception(empty)
        }
        catch(wrong: WrongDataException){
            Log.println(Log.DEBUG, "login response", wrong.message?:"")
            return DataState.Exception(wrong)
        }
        catch (other:Exception) {
            Log.println(Log.DEBUG, "login response", "Exception")
            other.printStackTrace()
            return DataState.Exception(other)
        }

    }

    suspend fun signup(user:UserItem): DataState<UserItem> {
        try {
            val response = api.signup(user.copy(password = user.password.getHash()).toRequest())

            if(response.isSuccessful){
                if(response.body()!=null){
                    return DataState.Result(response.body()!!.toItem())
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
            return DataState.Exception(exception)
        }
        catch (exception: HttpException){
            Log.println(Log.DEBUG, "login response", exception.message?: "HttpException")
            return DataState.Exception(exception)
        }
        catch(unsuccessful: ResponseUnsuccessfulException){
            Log.println(Log.DEBUG, "login response", unsuccessful.info)
            return DataState.Exception(unsuccessful)
        }
        catch(empty: ResponseEmptyException){
            Log.println(Log.DEBUG, "login response", empty.info)
            return DataState.Exception(empty)
        }
        catch (other:Exception) {
            Log.println(Log.DEBUG, "login response", "Exception")
            other.printStackTrace()
            return DataState.Exception(other)
        }

    }

    suspend fun me(): DataState<UserItem> {
        try {
            val response = api.me(sharedPreferencesHelper.getToken())
            if(response.isSuccessful){
                if(response.body()!=null){
                    return DataState.Result(response.body()!!.toItem())
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
            return DataState.Exception(exception)
        }
        catch (exception: HttpException){
            Log.println(Log.DEBUG, "login response", exception.message?: "HttpException")
            return DataState.Exception(exception)
        }
        catch(unsuccessful: ResponseUnsuccessfulException){
            Log.println(Log.DEBUG, "login response", unsuccessful.info)
            return DataState.Exception(unsuccessful)
        }
        catch(empty: ResponseEmptyException){
            Log.println(Log.DEBUG, "login response", empty.info)
            return DataState.Exception(empty)
        }
        catch (other:Exception) {
            Log.println(Log.DEBUG, "login response", "Exception")
            other.printStackTrace()
            return DataState.Exception(other)
        }

    }
}