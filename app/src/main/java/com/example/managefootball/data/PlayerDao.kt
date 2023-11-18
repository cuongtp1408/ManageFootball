package com.example.managefootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.managefootball.model.Player
import com.example.managefootball.model.Team
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface PlayerDao {

    @Query("select * from player_tbl")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("select * from player_tbl where idPlayer = :id")
    suspend fun getPlayerById(id: String): Player

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayer(player: Player)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlayer(player: Player)

    @Query("delete from player_tbl")
    suspend fun deleteAllPlayers()

    @Query("select * from player_tbl where idTeam = :id")
    suspend fun getPlayersWithTeamId(id: String): List<Player>

    @Query("SELECT * FROM player_tbl WHERE namePlayer LIKE '%' || :query || '%'")
    fun searchPlayer(query: String): Flow<List<Player>>
    
}