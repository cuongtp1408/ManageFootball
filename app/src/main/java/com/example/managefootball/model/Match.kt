package com.example.managefootball.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.managefootball.util.Constant.STATUS_PROGRESS
import java.util.UUID

@Entity(tableName = "match_tbl")
data class Match(
    @PrimaryKey
    val idMatch: UUID = UUID.randomUUID(),
    val round: String = "",
    val idTeam1: UUID,
    val idTeam2: UUID,
    val resultTeam1: Int = 0,
    val resultTeam2: Int = 0,
    val day: String,
    val time: String,
    val status: String = STATUS_PROGRESS
)
