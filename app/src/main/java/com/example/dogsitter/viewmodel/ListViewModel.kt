package com.example.dogsitter.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsitter.model.DogBreed
import com.example.dogsitter.model.DogDatabase
import com.example.dogsitter.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

import kotlin.collections.ArrayList

class ListViewModel(application: Application): BaseViewModel(application) {
    private val dogService = DogsApiService()
    private val disposable = CompositeDisposable()
    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh(){

        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromDatabase()
        }else{

            fetchFromRemote()
        }
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        isLoading.value = true
        launch {
             val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
                dogsRetrieved(dogs)

            Toast.makeText(getApplication(), "Dogs received from database",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote(){
        isLoading.value = true

        disposable.add(
            dogService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>(){

                    override fun onSuccess(dogsList: List<DogBreed>) {

                        storeDogsLocally(dogsList)
                        Toast.makeText(getApplication(), "Dogs received from endpoint",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        isLoading.value = false
                        e.printStackTrace()
                    }

                })


        )//end add function

    }//End fetch from remote

    private fun dogsRetrieved(dogsList: List<DogBreed>){
        dogs.value = dogsList
        dogsLoadError.value = false
        isLoading.value = false

    }

    private fun storeDogsLocally(list:List<DogBreed>){

        launch {
            val dao = DogDatabase(getApplication()).dogDao()
                dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray())

            var i = 0
            while(i < list.size){

                list[i].uuid = result[i].toInt()
                ++i
            }

            dogsRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}