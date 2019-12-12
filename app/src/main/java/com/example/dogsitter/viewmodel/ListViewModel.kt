package com.example.dogsitter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsitter.model.DogBreed

import kotlin.collections.ArrayList

class ListViewModel: ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        val dog1 = DogBreed("1", "Black Lab", "17 years",
            "breedGroup", "bredFor", "Lick", "")
        val dog2 = DogBreed("2", "Boxer", "9 years",
            "breedGroup", "bredFor", "Ayyy", "")
        val dog3 = DogBreed("3", "Mixed", "12 years",
            "breedGroup", "bredFor", "Grrr", "")

        val dogList = arrayListOf(dog1, dog2, dog3)

        //when we call .value it must be of type in generic of mutable live data <>

        dogs.value = dogList
        dogsLoadError.value = false
        isLoading.value = false
    }

}