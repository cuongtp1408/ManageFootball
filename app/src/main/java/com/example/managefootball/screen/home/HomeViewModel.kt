package com.example.managefootball.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.model.Team
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.repository.RuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val footballRepository: FootballRepository,
    private val ruleRepository: RuleRepository
): ViewModel() {
    private val _listTeams = MutableStateFlow<List<Team>>(emptyList())
    val listTeams = _listTeams.asStateFlow()

    private val _priorityTotalScore = MutableStateFlow(1)
    val priorityTotalScore = _priorityTotalScore.asStateFlow()

    private val _priorityNumberDifferent = MutableStateFlow(2)
    val priorityNumberDifferent = _priorityNumberDifferent.asStateFlow()

    private val _priorityTotalGoal = MutableStateFlow(3)
    val priorityTotalGoal = _priorityTotalGoal.asStateFlow()

    private val _priorityTotalMatch = MutableStateFlow(4)
    val priorityTotalMatch = _priorityTotalMatch.asStateFlow()

    private val _winScore = MutableStateFlow(3)
    val winScore = _winScore.asStateFlow()

    private val _tieScore = MutableStateFlow(1)
    val tieScore = _tieScore.asStateFlow()

    private val _loseScore = MutableStateFlow(0)
    val loseScore = _loseScore.asStateFlow()

    var isLoading = mutableStateOf(true)

    init {
        isLoading.value = true
        getListTeams()
        getPriorityNumberDifferent()
        getPriorityTotalGoal()
        getPriorityTotalScore()
        getPriorityTotalMatch()
        getWinScore()
        getTieScore()
        getLoseScore()
        isLoading.value = false
    }

    private fun getListTeams(){
        viewModelScope.launch {
            footballRepository.getAllTeams().collect{
                _listTeams.value = it
            }
        }
    }

    private fun getPriorityTotalScore(){
        viewModelScope.launch {
            ruleRepository.getPriorityTotalScore.collect{
                _priorityTotalScore.value = it
            }
        }
    }

    private fun getPriorityNumberDifferent(){
        viewModelScope.launch {
            ruleRepository.getPriorityNumberDifferent.collect{
                _priorityNumberDifferent.value = it
            }
        }
    }

    private fun getPriorityTotalGoal(){
        viewModelScope.launch {
            ruleRepository.getPriorityTotalGoal.collect{
                _priorityTotalGoal.value = it
            }
        }
    }

    private fun getPriorityTotalMatch(){
        viewModelScope.launch {
            ruleRepository.getPriorityTotalMatch.collect{
                _priorityTotalMatch.value = it
            }
        }
    }

    private fun getWinScore(){
        viewModelScope.launch {
            ruleRepository.getWinScore.collect{
                _winScore.value = it
            }
        }
    }

    private fun getTieScore(){
        viewModelScope.launch {
            ruleRepository.getTieScore.collect{
                _tieScore.value = it
            }
        }
    }

    private fun getLoseScore(){
        viewModelScope.launch {
            ruleRepository.getLoseScore.collect{
                _loseScore.value = it
            }
        }
    }
}