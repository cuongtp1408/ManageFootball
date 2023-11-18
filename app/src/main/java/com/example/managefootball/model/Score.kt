package com.example.managefootball.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "score_tbl")
data class Score (
    @PrimaryKey
    val idScore: UUID = UUID.randomUUID(),
    val idMatch: UUID,
    val idPlayer: UUID,
    val time: Int,
    val typeScore: String
)