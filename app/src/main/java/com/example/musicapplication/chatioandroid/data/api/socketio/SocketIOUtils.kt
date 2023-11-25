package com.example.musicapplication.chatioandroid.data.api.socketio

import android.content.ContentValues.TAG
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.musicapplication.chatioandroid.data.model.payload.OnlineUserPayload
import com.example.musicapplication.chatioandroid.utils.ONLINE_USER_EVENT
import com.google.gson.Gson
import io.socket.client.Socket
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Kakyire
 * Created by Daniel Frimpong on October 29, 2022.
 * https://github.com/kakyire
 */
@Suppress("UNCHECKED_CAST")
class SocketIOUtils(private val io: Socket, private val gson: Gson = Gson()) {


    fun connect(): Socket? {

        Timber.tag(TAG).d("Just connected to server")

        return io.connect()
    }

    fun disconnect(): Socket? {
        return io.disconnect()
    }


    fun <T> sendEvent(event: String, message: T) {
        if (!io.connected()) throw IllegalAccessException("Socket IO is not connected")


        val payload = if (message is String) message else gson.toJson(message)
        Timber.tag(TAG).d(payload)
        io.emit(event, payload)
    }


    fun isConnected(): Boolean {
        return io.isActive

    }

    fun joinOnlineUsers(onlineUserPayload: OnlineUserPayload) {
        io.on("connect") {

            Timber.tag("TAG").d("user connected with ${io.id()}")
            sendEvent(ONLINE_USER_EVENT, onlineUserPayload)
        }
    }

    fun <T : Any> onEventReceive(
        event: String,
        fragment: Fragment,
        clazz: Class<T>,
        onReceived: (T) -> Unit
    ) {
        io.on(event) {
            if (event.equals(
                    "connect",
                    true
                )
            ) {
                throw IllegalArgumentException("Use onConnect function to check connection")
            }
            if (it.isNotEmpty()) {
                val value = gson.fromJson(it[0].toString(), clazz)
                fragment.lifecycleScope.launch { onReceived(value) }
            }
        }
    }


}