package com.example.tae.starwars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StarwarsServices {
    @GET("user")
    fun getUserDetails(@Path("user") userName: String): Call<StarwarsUser>

}