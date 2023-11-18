package com.example.managefootball.screen.matchdetail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.model.Match
import com.example.managefootball.model.Player
import com.example.managefootball.model.Score
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val footballRepository: FootballRepository
): ViewModel() {
    private val _match = MutableStateFlow<Match?>(null)
    val match = _match.asStateFlow()

    private val _team1 = MutableStateFlow<Team?>(null)
    val team1 = _team1.asStateFlow()

    private val _team2 = MutableStateFlow<Team?>(null)
    val team2 = _team2.asStateFlow()

    private val _listScores = MutableStateFlow<List<Score>>(emptyList())
    val listScores = _listScores.asStateFlow()

    private val _listPlayers = MutableStateFlow<List<Player>>(emptyList())
    val listPlayers = _listPlayers.asStateFlow()

    var isLoading = mutableStateOf(true)

    fun getMatchDetail(matchId: String) {
        viewModelScope.launch {
            _match.value = footballRepository.getMatchById(matchId)
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

    fun getListScores(matchId: String){
        viewModelScope.launch {
            footballRepository.getAllScores().map { list ->
                list.filter {
                    it.idMatch.toString() == matchId }
            }.collect{
                _listScores.value = it

            }

        }
    }

    fun getListPlayersScore(){
        viewModelScope.launch {
            Log.e("Tpoo", "list player: start")
            isLoading.value = true
            _listPlayers.value = emptyList()
            val list = mutableListOf<Player>()
            _listScores.value.forEach { score ->
                val player = footballRepository.getPlayerById(score.idPlayer.toString())
                list.add(player)
            }
            _listPlayers.value = list
            isLoading.value = false
        }
    }

}