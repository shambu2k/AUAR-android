package com.example.auar.data

class Constants {

    companion object {
        const val SERVER_IP = "SERVER_ADDRESS_HERE"
        const val PORT = "2001"

        const val EMIT_CREATE_ROOM = "create_room"
        const val EMIT_JOIN_ROOM = "join_room"
        const val EMIT_LEAVE_ROOM = "leave_room"

        const val ON_PLAYER_JOINED = "player_joined"
        const val ON_PLAYER_LEFT = "player_left"
        const val ON_ROOM_ERROR = "room_error"
        const val ON_NUM_PLAYERS = "num_players"
    }
}