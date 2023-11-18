package com.example.managefootball.repository

import com.example.managefootball.data.MatchDao
import com.example.managefootball.data.PlayerDao
import com.example.managefootball.data.ScoreDao
import com.example.managefootball.data.TeamDao
import com.example.managefootball.model.Match
import com.example.managefootball.model.Player
import com.example.managefootball.model.Score
import com.example.managefootball.model.Team
import javax.inject.Inject

class FootballRepository @Inject constructor(
    private val playerDao: PlayerDao,
    private val teamDao: TeamDao,
    private val matchDao: MatchDao,
    private val scoreDao: ScoreDao,
) {

    fun getAllPlayers() = playerDao.getAllPlayers()
    suspend fun getPlayerById(id: String) = playerDao.getPlayerById(id)
    suspend fun addPlayer(player: Player) = playerDao.addPlayer(player)
    suspend fun updatePlayer(player: Player) = playerDao.updatePlayer(player)
    suspend fun deleteAllPlayers() = playerDao.deleteAllPlayers()
    suspend fun getPlayerWithTeamId(id: String) = playerDao.getPlayersWithTeamId(id)
    fun searchPlayer(query: String) = playerDao.searchPlayer(query)

    fun getAllTeams() = teamDao.getAllTeams()
    suspend fun getTeamById(id: String) = teamDao.getTeamById(id)
    suspend fun addTeam(team:Team) = teamDao.addTeam(team)
    suspend fun updateTeam(team: Team) = teamDao.updateTeam(team)
    suspend fun deleteAllTeams() = teamDao.deleteAllTeams()

    fun getAllMatches() = matchDao.getAllMatches()
    suspend fun getMatchById(id: String) = matchDao.getMatchById(id)
    suspend fun addMatch(match: Match) = matchDao.addMatch(match)
    suspend fun updateMatch(match: Match) = matchDao.updateMatch(match)
    suspend fun deleteAllMatches() = matchDao.deleteAllMatches()
    fun getMatchInRound(round: String) = matchDao.getMatchInRound(round)

    fun getAllScores() = scoreDao.getAllScores()
    suspend fun addScore(score: Score) = scoreDao.addScore(score)
    suspend fun updateScore(score: Score) = scoreDao.updateScore(score)
    suspend fun addScores(scores: List<Score>) = scoreDao.addScores(scores)


}