package com.example.managefootball.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RuleRepository(private val context: Context){

    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("setting")
    }

    private object Keys{
        val MIN_YEARS = intPreferencesKey("min_years")
        val MAX_YEARS = intPreferencesKey("max_years")
        val MIN_PLAYERS = intPreferencesKey("min_players")
        val MAX_PLAYERS = intPreferencesKey("max_players")
        val MAX_FOREIGN_PLAYERS = intPreferencesKey("max_foreign_players")
        val MAX_SCORES = intPreferencesKey("max_scores")
        val MAX_MINUTE = intPreferencesKey("max_minute")
        val WIN_SCORE = intPreferencesKey("win_score")
        val TIE_SCORE = intPreferencesKey("tie_score")
        val LOSE_SCORE = intPreferencesKey("lose_score")
        val PRIORITY_TOTAL_SCORE = intPreferencesKey("priority_total_score")
        val PRIORITY_NUMBER_DIFFERENT = intPreferencesKey("priority_number_different")
        val PRIORITY_TOTAL_GOAL = intPreferencesKey("priority_total_goal")
        val PRIORITY_TOTAL_MATCH = intPreferencesKey("priority_total_match")
        val IS_FIRST_RUN = booleanPreferencesKey("is_first_run")
    }

    val getMinYears: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MIN_YEARS] ?: 16
    }
    val getMaxYears: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MAX_YEARS] ?: 40
    }
    val getMinPlayers: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MIN_PLAYERS] ?: 2
    }
    val getMaxPlayers: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MAX_PLAYERS] ?: 22
    }
    val getMaxForeignPlayers: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MAX_FOREIGN_PLAYERS] ?: 3
    }
    val getMaxScores: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MAX_SCORES] ?: 3
    }
    val getMaxMinutes: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.MAX_MINUTE] ?: 90
    }
    val getWinScore: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.WIN_SCORE] ?: 3
    }
    val getTieScore: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.TIE_SCORE] ?: 1
    }
    val getLoseScore: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.LOSE_SCORE] ?: 0
    }
    val getPriorityTotalScore: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.PRIORITY_TOTAL_SCORE] ?: 1
    }
    val getPriorityNumberDifferent: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.PRIORITY_NUMBER_DIFFERENT] ?: 2
    }
    val getPriorityTotalGoal: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.PRIORITY_TOTAL_GOAL] ?: 3
    }
    val getPriorityTotalMatch: Flow<Int> =context.dataStore.data.map { pref ->
        pref[Keys.PRIORITY_TOTAL_MATCH] ?: 4
    }
    val getIsFirstRun: Flow<Boolean> =context.dataStore.data.map { pref ->
        pref[Keys.IS_FIRST_RUN] ?: true
    }


    suspend fun setMinYears(years: Int){
        context.dataStore.edit { pref->
            pref[Keys.MIN_YEARS] =years
        }
    }
    suspend fun setMaxYears(years: Int){
        context.dataStore.edit { pref->
            pref[Keys.MAX_YEARS] =years
        }
    }
    suspend fun setMinPlayers(players: Int){
        context.dataStore.edit { pref->
            pref[Keys.MIN_PLAYERS] =players
        }
    }
    suspend fun setMaxPlayers(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.MAX_PLAYERS] =num
        }
    }
    suspend fun setMaxForeignPlayers(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.MAX_FOREIGN_PLAYERS] =num
        }
    }
    suspend fun setMaxScores(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.MAX_SCORES] =num
        }
    }
    suspend fun setMaxMinutes(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.MAX_MINUTE] =num
        }
    }
    suspend fun setWinScore(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.WIN_SCORE] =num
        }
    }
    suspend fun setTieScore(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.TIE_SCORE] =num
        }
    }
    suspend fun setLoseScore(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.LOSE_SCORE] =num
        }
    }
    suspend fun setPriorityTotalScore(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.PRIORITY_TOTAL_SCORE] =num
        }
    }
    suspend fun setPriorityNumberDifferent(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.PRIORITY_NUMBER_DIFFERENT] =num
        }
    }
    suspend fun setPriorityTotalGoal(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.PRIORITY_TOTAL_GOAL] =num
        }
    }
    suspend fun setPriorityTotalMatch(num: Int){
        context.dataStore.edit { pref->
            pref[Keys.PRIORITY_TOTAL_MATCH] =num
        }
    }
    suspend fun setIsFirstRun(isFirst: Boolean){
        context.dataStore.edit { pref->
            pref[Keys.IS_FIRST_RUN] =isFirst
        }
    }

}