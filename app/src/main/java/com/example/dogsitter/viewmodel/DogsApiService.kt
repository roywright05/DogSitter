package com.example.dogsitter.viewmodel

import com.example.dogsitter.model.DogBreed
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {
    //This is the class where we implement the GET, POST etc methods from our API

    private val BASE_URL =
        "https://raw.githubusercontent.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DogsApi::class.java)

    fun getDogs(): Single <List<DogBreed>>{
        return retrofit.getDogs()
    }
}