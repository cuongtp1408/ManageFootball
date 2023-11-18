package com.example.managefootball.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managefootball.repository.RuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val ruleRepository: RuleRepository
): ViewModel() {
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

    private val _maxScores = MutableStateFlow(3)
    val maxScores = _maxScores.asStateFlow()

    private val _maxMinutes = MutableStateFlow(90)
    val maxMinutes = _maxMinutes.asStateFlow()

    private val _winScore = MutableStateFlow(3)
    val winScore = _winScore.asStateFlow()

    private val _tieScore = MutableStateFlow(1)
    val tieScore = _tieScore.asStateFlow()

    private val _loseScore = MutableStateFlow(0)
    val loseScore = _loseScore.asStateFlow()

    private val _priorityTotalScore = MutableStateFlow(1)
    val priorityTotalScore = _priorityTotalScore.asStateFlow()

    private val _priorityNumberDifferent = MutableStateFlow(2)
    val priorityNumberDifferent = _priorityNumberDifferent.asStateFlow()

    private val _priorityTotalGoal = MutableStateFlow(3)
    val priorityTotalGoal = _priorityTotalGoal.asStateFlow()

    private val _priorityTotalMatch = MutableStateFlow(4)
    val priorityTotalMatch = _priorityTotalMatch.asStateFlow()

    init {
        getMinYears()
        getMaxYears()
        getMinPlayers()
        getMaxPlayers()
        getMaxForeignPlayers()
        getMaxScores()
        getMaxMinutes()
        getWinScore()
        getTieScore()
        getLoseScore()
        getPriorityNumberDifferent()
        getPriorityTotalGoal()
        getPriorityTotalScore()
        getPriorityTotalMatch()
    }

    private fun getMinYears(){
        viewModelScope.launch {
            ruleRepository.getMinYears.collect {
                _minYears.value = it
            }
        }
    }

    private fun getMaxYears(){
        viewModelScope.launch{
            ruleRepository.getMaxYears.collect{
                _maxYears.value = it
            }
        }
    }

    private fun getMinPlayers(){
        viewModelScope.launch{
            ruleRepository.getMinPlayers.collect{
                _minPlayers.value = it
            }
        }
    }

    private fun getMaxPlayers(){
        viewModelScope.launch{
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

    fun setMinYears(num: Int){
        viewModelScope.launch{
            ruleRepository.setMinYears(num)
        }
    }
    fun setMaxYears(num: Int){
        viewModelScope.launch{
            ruleRepository.setMaxYears(num)
        }
    }
    fun setMinPlayers(num: Int){
        viewModelScope.launch{
            ruleRepository.setMinPlayers(num)
        }
    }
    fun setMaxPlayers(num: Int){
        viewModelScope.launch {
            ruleRepository.setMaxPlayers(num)
        }
    }
    fun setMaxForeignPlayers(num: Int){
        viewModelScope.launch {
            ruleRepository.setMaxForeignPlayers(num)
        }
    }
    fun setMaxScores(num: Int){
        viewModelScope.launch {
            ruleRepository.setMaxScores(num)
        }
    }
    fun setMaxMinutes(num: Int){
        viewModelScope.launch {
            ruleRepository.setMaxMinutes(num)
        }
    }
    fun setWinScore(num: Int){
        viewModelScope.launch {
            ruleRepository.setWinScore(num)
        }
    }
    fun setTieScore(num: Int){
        viewModelScope.launch {
            ruleRepository.setTieScore(num)
        }
    }
    fun setLoseScore(num: Int){
        viewModelScope.launch {
            ruleRepository.setLoseScore(num)
        }
    }
    fun setPriorityTotalScore(num: Int){
        viewModelScope.launch {
            ruleRepository.setPriorityTotalScore(num)
        }
    }
    fun setPriorityNumberDifferent(num: Int){
        viewModelScope.launch {
            ruleRepository.setPriorityNumberDifferent(num)
        }
    }
    fun setPriorityTotalGoal(num: Int){
        viewModelScope.launch {
            ruleRepository.setPriorityTotalGoal(num)
        }
    }
    fun setPriorityTotalMatch(num: Int){
        viewModelScope.launch {
            ruleRepository.setPriorityTotalMatch(num)
        }
    }


}