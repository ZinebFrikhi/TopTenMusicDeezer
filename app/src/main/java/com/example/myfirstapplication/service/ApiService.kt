package com.example.myfirstapplication.service

import com.example.myfirstapplication.model.ResponseApiDeezer
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("chart/0/tracks")
    suspend fun getTenSongsList(): Response<ResponseApiDeezer>
}
