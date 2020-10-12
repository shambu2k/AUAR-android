package com.example.auar.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auar.data.Constants.Companion.EMIT_CREATE_ROOM
import com.example.auar.data.Constants.Companion.EMIT_JOIN_ROOM
import com.example.auar.data.Constants.Companion.EMIT_LEAVE_ROOM
import com.example.auar.data.Constants.Companion.ON_NUM_PLAYERS
import com.example.auar.data.Constants.Companion.ON_PLAYER_JOINED
import com.example.auar.data.Constants.Companion.ON_PLAYER_LEFT
import com.example.auar.data.Constants.Companion.ON_ROOM_ERROR
import com.example.auar.data.Constants.Companion.PORT
import com.example.auar.data.Constants.Companion.SERVER_IP
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject
import javax.inject.Inject


class SocketsRepository @Inject constructor() {

    private lateinit var mSocket: Socket
    private var numPlayers: MutableLiveData<Int> = MutableLiveData()

    private var onPlayerJoined: Emitter.Listener = Emitter.Listener {
        Log.d("REPO", "PLAYER JOINED LISTENRER ${it[0]}")
    }

    private var onPlayerLeft: Emitter.Listener = Emitter.Listener {
        Log.d("REPO", "player left: "+it[0] as String)
    }

    private var onNumPlayers: Emitter.Listener = Emitter.Listener {
        numPlayers.postValue(it[0] as Int)
        Log.d("REPO", "NumPlayers: ${numPlayers.value.toString()}")
    }

    private var onRoomError: Emitter.Listener = Emitter.Listener {
        Log.d("REPO", "room error: "+it[0] as String)
    }

    init {
        mSocket = IO.socket(SERVER_IP + PORT)
        mSocket.on(ON_PLAYER_JOINED, onPlayerJoined)
        mSocket.on(ON_PLAYER_LEFT, onPlayerLeft)
        mSocket.on(ON_NUM_PLAYERS, onNumPlayers)
        mSocket.on(ON_ROOM_ERROR, onRoomError)
        mSocket.connect()
        Log.d("REPO", "Initialized Socket at $SERVER_IP$PORT")
    }

    suspend fun createRoom(playerName: String, roomId: String){
        Log.d("REPO", "Creating Room")
        var jsonObject = JSONObject()
        jsonObject.put("roomId", roomId)
        jsonObject.put("playerName", playerName)
        mSocket.emit(EMIT_CREATE_ROOM, jsonObject)
    }

    suspend fun joinRoom(playerName: String, roomId: String){
        var jsonObject = JSONObject()
        jsonObject.put("roomId", roomId)
        jsonObject.put("playerName", playerName)
        mSocket.emit(EMIT_JOIN_ROOM, jsonObject)
    }

    suspend fun leaveRoom(playerName: String, roomId: String) {
        Log.d("REPO", "Leaving Room")
        var jsonObject = JSONObject()
        jsonObject.put("roomId", roomId)
        jsonObject.put("playerName", playerName)
        mSocket.emit(EMIT_LEAVE_ROOM, jsonObject)
    }

    fun getNumPlayers(): MutableLiveData<Int> {
        return numPlayers;
    }

    fun clearSockets(){
        mSocket.disconnect();
        mSocket.off(ON_PLAYER_JOINED, onPlayerJoined)
        mSocket.off(ON_PLAYER_LEFT, onPlayerLeft)
        mSocket.off(ON_NUM_PLAYERS, onNumPlayers)
        mSocket.off(ON_ROOM_ERROR, onRoomError)
    }
}
