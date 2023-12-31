package com.example.musicapplication.di

import android.content.Context
import com.example.musicapplication.chatioandroid.data.api.socketio.ApiServiceSocket
import com.example.musicapplication.chatioandroid.data.api.socketio.SocketIOUtils
import com.example.musicapplication.chatioandroid.utils.BASE_URL
import com.example.musicapplication.data.network.interceptors.GetCookieInterceptor
import com.example.musicapplication.data.network.interceptors.SetCookieJar
import com.example.musicapplication.data.network.api.ApiService
import com.example.musicapplication.data.network.repo.NetworkSource
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.utils.Constants
import com.example.musicapplication.utils.Constants.TIMEOUT
import com.example.musicapplication.utils.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Provides
    fun provideApiServiceSocket(okhttpClient: OkHttpClient): ApiServiceSocket = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpClient)
        .build()
        .create()

    @Provides
    @Singleton
    fun provideSocketIO(io: Socket): SocketIOUtils {
        return SocketIOUtils(io=io)
    }

    @Provides
    fun provideIo(): Socket {
        return IO.socket(BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        client: OkHttpClient
    ): Retrofit {
        System.setProperty("http.keepAlive", "false")
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: Interceptor,
        getCookieInterceptor: GetCookieInterceptor,
        setCookieJar: SetCookieJar
    ): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(getCookieInterceptor)
            .cookieJar(setCookieJar).build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideCookieInterceptor(sharedPreferencesHelper: SharedPreferencesHelper): GetCookieInterceptor {
        return GetCookieInterceptor(sharedPreferencesHelper)
    }

    @Provides
    @Singleton
    fun provideCookieJar(sharedPreferencesHelper: SharedPreferencesHelper) = SetCookieJar(sharedPreferencesHelper)


    @Provides
    @Singleton
    fun provideNetworkSource(
        apiService: ApiService
    ): NetworkSource = NetworkSource(apiService)

    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver =
        NetworkConnectivityObserver(context)
}


