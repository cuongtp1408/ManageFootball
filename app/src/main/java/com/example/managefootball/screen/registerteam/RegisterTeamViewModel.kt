package com.example.managefootball.screen.registerteam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.model.Player
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.repository.RuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterTeamViewModel @Inject constructor(
    private val footballRepository: FootballRepository,
    private val ruleRepository: RuleRepository
): ViewModel() {

    val idTeam = UUID.randomUUID()

    private val _minYears = MutableStateFlow(16)
    val minYears = _minYears.asStateFlow()

    private val _maxYears = MutableStateFlow(40)
    val maxYears = _maxYears.asStateFlow()

    private val _minPlayers = MutableStateFlow(15)
    val minPlayers = _minPlayers.asStateFlow()

    private val _maxPlayers = MutableStateFlow(22)
    val maxPlayers = _maxPlayers.asStateFlow()

    private val _maxForeignPlayers = MutableStateFlow(3)
    val maxForeignPlayers = _maxForeignPlayers.asStateFlow()

    init {
        getMinYears()
        getMaxYears()
        getMinPlayers()
        getMaxPlayers()
        getMaxForeignPlayers()
    }

    fun addPlayer(player: Player){
        viewModelScope.launch(Dispatchers.IO) {
            footballRepository.addPlayer(player)
        }
    }

    fun addTeam(team: Team){
        viewModelScope.launch {
            footballRepository.addTeam(team)
        }
    }

    private fun getMinYears(){
        viewModelScope.launch {
            ruleRepository.getMinYears.collect {
                _minYears.value = it
            }
        }
    }

    private fun getMaxYears(){
        viewModelScope.launch {
            ruleRepository.getMaxYears.collect{
                _maxYears.value = it
            }
        }
    }

    private fun getMinPlayers(){
        viewModelScope.launch {
            ruleRepository.getMinPlayers.collect{
                _minPlayers.value = it
            }
        }
    }

    private fun getMaxPlayers(){
        viewModelScope.launch {
            ruleRepository.getMaxPlayers.collect{
                _maxPlayers.value = it
            }
        }
    }

    private fun getMaxForeignPlayers(){
        viewModelScope.launch {
            ruleRepository.getMaxForeignPlayers.collect{
                _maxForeignPlayers.value = it
            }
        }
    }
}