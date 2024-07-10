package com.example.myshoppinglistapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)

    val location : State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeoCodingResult>())
    val address : State<List<GeoCodingResult>> = _address

    fun updateLocation(newLocation : LocationData){
        _location.value = newLocation
    }

    fun fetchAddress(latlng : String){
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyC3R_n5tdNrWDEzkskC1OYLINybs5zF5GQ"
                )
                _address.value = result.results
            }
        }catch ( e :Exception ){
            Log.d("res1","${e.cause} ${e.message}")
        }
    }
}