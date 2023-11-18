package com.example.managefootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.managefootball.model.Team
import kotlinx.coroutines.flow.Flow


@Dao
interface TeamDao {

    @Query("select * from team_tbl")
    fun getAllTeams(): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTeam(team: Team)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTeam(team: Team)

    @Query("select * from team_tbl where idTeam = :id")
    suspend fun getTeamById(id: String): Team

    @Query("delete from team_tbl")
    suspend fun deleteAllTeams()

}