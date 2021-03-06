package com.example.dogsitter.viewmodel

import com.example.dogsitter.model.DogBreed
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}