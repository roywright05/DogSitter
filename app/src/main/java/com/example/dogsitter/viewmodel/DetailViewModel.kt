package com.example.dogsitter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsitter.model.DogBreed

class DetailViewModel: ViewModel() {

    //one variable contains all the information
    val dogDetails = MutableLiveData<DogBreed>()

    fun fetch(){

        val dogProfile = DogBreed("1", "German Shepard", "18",
            "1", "Home Defense", "Alert", "")

        dogDetails.value = dogProfile
    }
}