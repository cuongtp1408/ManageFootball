package com.example.managefootball.screen.resultmatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.model.Match
import com.example.managefootball.model.Player
import com.example.managefootball.model.Score
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.repository.RuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddResultMatchViewModel @Inject constructor(
    private val footballRepository: FootballRepository,
    private val ruleRepository: RuleRepository
): ViewModel() {
    private val _match = MutableStateFlow<Match?>(null)
    val match = _match.asStateFlow()

    private val _team1 = MutableStateFlow<Team?>(null)
    val team1 = _team1.asStateFlow()

    private val _team2 = MutableStateFlow<Team?>(null)
    val team2 = _team2.asStateFlow()

    private val _listPlayersTeam1 = MutableStateFlow<List<Player>>(emptyList())
    val listPlayersTeam1 = _listPlayersTeam1.asStateFlow()

    private val _listPlayersTeam2 = MutableStateFlow<List<Player>>(emptyList())
    val listPlayersTeam2 = _listPlayersTeam2.asStateFlow()

    private val _maxScores = MutableStateFlow(3)
    val maxScores = _maxScores.asStateFlow()

    private val _maxMinutes = MutableStateFlow(90)
    val maxMinutes = _maxMinutes.asStateFlow()

    var isLoading by mutableStateOf(true)

    init {
        getMaxScores()
        getMaxMinutes()
    }

    fun getMatchById(idMatch: String){
        viewModelScope.launch {
            _match.value = footballRepository.getMatchById(idMatch)
        }
    }

    fun addScores(scores: List<Score>){
        viewModelScope.launch {
            footballRepository.addScores(scores)
        }
    }

    fun addScore(score: Score){
        viewModelScope.launch {
            footballRepository.addScore(score)
        }
    }


    suspend fun updatePlayer(player: Player) {
        withContext(Dispatchers.IO){
            footballRepository.updatePlayer(player)
        }
    }

    fun updateTeam(team: Team){
        viewModelScope.launch {
            footballRepository.updateTeam(team)
        }
    }

    fun updateMatch(match: Match){
        viewModelScope.launch {
            footballRepository.updateMatch(match)
        }
    }

    fun getTeam1ById(teamId: String){
        viewModelScope.launch {
            _team1.value = footballRepository.getTeamById(teamId)
        }
    }

    fun getTeam2ById(teamId: String){
        viewModelScope.launch {
            _team2.value = footballRepository.getTeamById(teamId)
        }
    }

    fun getListPlayersTeam1(idTeam: String){
        viewModelScope.launch {
            isLoading = true
            _listPlayersTeam1.value = footballRepository.getPlayerWithTeamId(idTeam)
            isLoading = false
        }
    }

    fun getListPlayersTeam2(idTeam: String){
        viewModelScope.launch {
            isLoading = true
            _listPlayersTeam2.value = footballRepository.getPlayerWithTeamId(idTeam)
            isLoading = false
        }
    }

    private fun getMaxScores(){
        viewModelScope.launch {
            ruleRepository.getMaxScores.collect{
                _maxScores.value = it
            }
        }
    }

    private fun getMaxMinutes(){
        viewModelScope.launch {
            ruleRepository.getMaxMinutes.collect{
                _maxMinutes.value = it
            }
        }
    }

    suspend fun getPlayerById(idPlayer: String): Player = footballRepository.getPlayerById(idPlayer)
}