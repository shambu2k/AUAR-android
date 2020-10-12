package com.example.auar.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.auar.data.SocketsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(
    private val socketsRepository: SocketsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val socketScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private lateinit var playerNum: MutableLiveData<Int>

    fun createRoom(playerName: String, roomId: String) {
        socketScope.launch {
            Log.d("VM", "Creating room..")
            socketsRepository.createRoom(playerName, roomId)
        }
    }

    fun joinRoom(playerName: String, roomId: String) {
        socketScope.launch {
            socketsRepository.joinRoom(playerName, roomId)
        }
    }

    fun leaveRoom(playerName: String, roomId: String) {
        socketScope.launch {
            socketsRepository.leaveRoom(playerName, roomId)
        }
    }

    fun getRate(): LiveData<Int> {
        playerNum = socketsRepository.getNumPlayers()
        return playerNum
    }


    override fun onCleared() {
        super.onCleared()
        socketsRepository.clearSockets()
        viewModelJob.cancel()
    }
}
