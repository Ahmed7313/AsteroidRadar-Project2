package com.example.asteroidradar2.main

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidradar2.Constants
import com.example.asteroidradar2.Image
import com.example.asteroidradar2.PictureOfDay
import com.example.asteroidradar2.domain.Asteroid
import com.example.asteroidradar2.network.ImageApi
import com.example.asteroidradar2.repository.AsteroidsRepository
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch

enum class ImageApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val repository = AsteroidsRepository(database)

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image>
        get() = _image

    fun displayPropertyDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    private val _status = MutableLiveData<ImageApiStatus>()
    val status: LiveData<ImageApiStatus>
        get() = _status

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
        getImageOfTheDay()
    }

    val asteroids = repository.asteroids

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _status.value = ImageApiStatus.LOADING
            try {
                var result = ImageApi.retrofitService.getImageOfTheDay(Constants.API_KEY)
                _image.value=result
                _status.value = ImageApiStatus.DONE
            } catch (e: Exception) {
                _status.value=ImageApiStatus.ERROR
                _image.value= Image("","","","","","","","")
            }
        }
    }

}