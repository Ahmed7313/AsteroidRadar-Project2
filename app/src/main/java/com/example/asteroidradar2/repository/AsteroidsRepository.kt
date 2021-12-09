package com.example.asteroidradar2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar2.Constants
import com.example.asteroidradar2.domain.Asteroid
import com.example.asteroidradar2.network.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.ArrayList

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids : LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()){
        it.asDomainModel()
    }

    suspend fun refreshAsteroids(startDate: String = getToday(), endDate: String = getSeventhDay()){
        withContext(Dispatchers.IO){
            var asteroidList: ArrayList<Asteroid>
            val response = Network.service.getAsteroids(startDate, endDate, Constants.API_KEY)
            val jsonObj = JSONObject(response)
            asteroidList= parseAsteroidsJsonResult(jsonObj)

            database.asteroidDao.insertAllAsteroids(*asteroidList.asDomainModel())
        }
    }

}