package com.example.managefootball.model

import java.util.UUID

data class MatchTeams(
    val idMatch: UUID,
    val team1: Team,
    val team2 : Team
)