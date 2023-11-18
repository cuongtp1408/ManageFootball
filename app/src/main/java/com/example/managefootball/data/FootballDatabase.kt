package com.example.managefootball.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.managefootball.model.Match
import com.example.managefootball.model.Player
import com.example.managefootball.model.Score
import com.example.managefootball.model.Team
import com.example.managefootball.util.UUIDConverter

@Database(entities = [Player::class,Team::class, Match::class,Score::class], version = 12, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class FootballDatabase : RoomDatabase(){
    abstract fun playerDao():PlayerDao
    abstract fun teamDao(): TeamDao
    abstract fun matchDao(): MatchDao
    abstract fun scoreDao(): ScoreDao
}