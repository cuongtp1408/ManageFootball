package com.example.managefootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.managefootball.model.Score
import com.example.managefootball.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("select * from score_tbl")
    fun getAllScores(): Flow<List<Score>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScore(score: Score)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScores(scores: List<Score>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateScore(score: Score)
}