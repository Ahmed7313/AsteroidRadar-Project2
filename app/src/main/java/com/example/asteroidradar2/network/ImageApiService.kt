package com.example.asteroidradar2.network

import com.example.asteroidradar2.Constants
import com.example.asteroidradar2.Image
import com.example.asteroidradar2.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL="https://api.nasa.gov/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofitImage= Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ImageApiService {

    @GET("planetary/apod")
    suspend fun getImageOfTheDay(@Query("api_key")key: String): Image

}

//exposing to other class
//exposing to other class
object ImageApi{
    val retrofitService : ImageApiService by lazy {
        retrofitImage.create(ImageApiService::class.java)
    }
}