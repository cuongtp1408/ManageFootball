package com.example.managefootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.managefootball.model.Match
import com.example.managefootball.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("select * from match_tbl order by day desc")
    fun getAllMatches(): Flow<List<Match>>

    @Query("select * from match_tbl where idMatch = :id")
    suspend fun getMatchById(id: String): Match

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMatch(match: Match)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMatch(match: Match)

    @Query("delete from match_tbl")
    suspend fun deleteAllMatches()

    @Query("select * from match_tbl where round = :round")
    fun getMatchInRound(round: String): Flow<List<Match>>
}