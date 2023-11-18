package com.example.managefootball.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "team_tbl")
data class Team(
    @PrimaryKey
    val idTeam: UUID =UUID.randomUUID(),
    val nameTeam: String="",
    val yardTeam: String="",
    val win: Int=0,
    val tie: Int=0,
    val lose: Int=0,
    val numberDiff: Int = 0,
    val totalGoal: Int = 0
)
