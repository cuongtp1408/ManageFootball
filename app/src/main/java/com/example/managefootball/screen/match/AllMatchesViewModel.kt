package com.example.managefootball.screen.match

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.model.Match
import com.example.managefootball.model.MatchTeams
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.util.convertStringToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMatchesViewModel @Inject constructor(
    private val footballRepository: FootballRepository
): ViewModel() {
    private val _listMatches = MutableStateFlow<List<Match>>(emptyList())
    val listMatches = _listMatches.asStateFlow()

    private val _listMatchTeams = MutableStateFlow<List<MatchTeams>>(emptyList())
    val listMatchTeams = _listMatchTeams.asStateFlow()

    var isLoading = mutableStateOf(true)

    init {

        getAllMatches()
    }

    private fun getAllMatches(){
        viewModelScope.launch {
            footballRepository.getAllMatches().map { matches ->
                matches.sortedByDescending { match ->
                    convertStringToDate(match.day)
                }
            }.collect{
                _listMatches.value = it
            }

            getAllListMatchTeams()
        }
    }

    fun getAllListMatchTeams(){

        viewModelScope.launch {
            isLoading.value = true

            _listMatchTeams.value = emptyList()
            val listMatchTeams = mutableListOf<MatchTeams>()

            _listMatches.value.forEach { match ->
                val team1 = footballRepository.getTeamById(match.idTeam1.toString())
                val team2 = footballRepository.getTeamById(match.idTeam2.toString())
                val matchTeams = MatchTeams(idMatch = match.idMatch, team1 = team1, team2 = team2)
                listMatchTeams.add(matchTeams)
            }
            _listMatchTeams.value = listMatchTeams.distinct()
            isLoading.value = false
        }

    }


}