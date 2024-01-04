package com.example.managefootball.screen.addmatch

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.managefootball.model.Match
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.util.AddMatchWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class AddMatchViewModel @Inject constructor(
    private val footballRepository: FootballRepository
): ViewModel() {
    private val _listTeams = MutableStateFlow<List<Team>>(emptyList())
    val listTeams = _listTeams.asStateFlow()

    private val _listTeamInRound = MutableStateFlow<List<String>>(emptyList())
    val listTeamInRound = _listTeamInRound.asStateFlow()

    private val _listMatchInRound = MutableStateFlow<List<Match>>(emptyList())
    val listMatchInRound = _listMatchInRound.asStateFlow()

    private val _listAllMatches = MutableStateFlow<List<Match>>(emptyList())
    val listAllMatches = _listAllMatches.asStateFlow()


    init {
        getAllTeams()
        getAllMatches()
    }

    private fun getAllTeams(){
        viewModelScope.launch {
            footballRepository.getAllTeams().collect{
                _listTeams.value = it
            }
        }
    }

    fun addMatch(match: Match){
        viewModelScope.launch {
            footballRepository.addMatch(match)
        }
    }

    fun getMatchInRound(round: String){
        viewModelScope.launch {
            footballRepository.getMatchInRound(round).collect{
                _listMatchInRound.value = it
            }
        }
    }

    private fun getAllMatches(){
        viewModelScope.launch {
            footballRepository.getAllMatches().collect{
                _listAllMatches.value = it
            }
        }
    }

    fun getTeamInRound(){
        _listTeamInRound.value = emptyList()
        _listMatchInRound.value.forEach{ match ->
            _listTeamInRound.value =  _listTeamInRound.value.plus(match.idTeam1.toString()).plus(match.idTeam2.toString())
        }
    }
}