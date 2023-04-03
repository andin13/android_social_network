package com.example.androidsocialnetwork.data.services

import com.example.androidsocialnetwork.data.entities.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitNetworkService {
    @GET("auth/me")
    fun authMe(): Call<AuthMeResponseType>

    @POST("auth/login")
    fun login(@Body userData: LoginData): Call<LoginResponseType>

    @DELETE("auth/login")
    fun logout(): Call<LogoutResponseType>

    @GET("users")
    fun getUsers(@Query("page") page: Int, @Query("count") count: Int): Call<GetUsersResponseType>

    @GET("profile/{userId}")
    fun getProfile(@Path("userId") userId: String): Call<Profile>
}
