package com.example.kotlinlogin.api

import com.example.kotlinlogin.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
            @Field("email") email: String,
            @Field("name") name :String,
            @Field("password") password:String,
            @Field("school") school:String
    ) : Call<DefaultResponse>
}