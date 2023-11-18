package com.example.managefootball.screen.players

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.model.Player
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val footballRepository: FootballRepository
): ViewModel() {
    private val _listPlayers = MutableStateFlow<List<Player>>(emptyList())
    val listPlayers = _listPlayers.asStateFlow()

    private val _listPlayersWithTeams = MutableStateFlow<List<Pair<Player,Team>>>(emptyList())
    val listPlayersWithTeams = _listPlayersWithTeams.asStateFlow()

    var isLoading by mutableStateOf(true)

    init {
        searchPlayers("")
    }

    fun searchPlayers(query: String){
        viewModelScope.launch {
            footballRepository.searchPlayer(query).collect {
                _listPlayers.value = it
            }
        }
    }

    fun getPlayersWithTeam(){
        viewModelScope.launch {
            isLoading = true
            val list = mutableListOf<Pair<Player,Team>>()
            _listPlayers.value.forEach{ player->
                val team = footballRepository.getTeamById(player.idTeam.toString())
                list.add(Pair(player,team))
            }
            _listPlayersWithTeams.value = list
            isLoading = false
        }
    }
}