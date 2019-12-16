package com.example.dogsitter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsitter.model.DogBreed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import kotlin.collections.ArrayList

class ListViewModel: ViewModel() {
    private val dogService = DogsApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote(){
        isLoading.value = true

        disposable.add(
            dogService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogsList: List<DogBreed>) {
                        dogs.value = dogsList
                        isLoading.value = false
                        dogsLoadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        isLoading.value = false
                        e.printStackTrace()
                    }


                })


        )//end add function

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}