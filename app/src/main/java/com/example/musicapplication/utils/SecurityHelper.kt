package com.example.musicapplication.utils

import androidx.compose.ui.text.toLowerCase
import java.lang.StringBuilder
import java.security.MessageDigest
import java.util.Locale

object SecurityHelper {

    //типа хэширование
    fun String.getHash(type:String = Constants.HASH_FUNC):String = this

    //хэширование
//    fun String.getHash(type:String = Constants.HASH_FUNC):String{
//        val digestBytes = MessageDigest.getInstance(type).digest(this.toByteArray())
//        val hash:StringBuilder = StringBuilder()
//        digestBytes.forEach { b -> hash.append(String.format("%02X", b)) }
//        return hash.toString()
//    }
}