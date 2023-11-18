package com.example.managefootball.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "player_tbl")
data class Player(
    @PrimaryKey
    val idPlayer: UUID = UUID.randomUUID(),
    var namePlayer: String="",
    val dateOfBirth: String="",
    val typePlayer: String="",
    val notePlayer: String="",
    val totalGoal: Int=0,
    val idTeam: UUID
)
