package com.example.managefootball.di

import android.content.Context
import androidx.room.Room
import com.example.managefootball.data.FootballDatabase
import com.example.managefootball.data.MatchDao
import com.example.managefootball.data.PlayerDao
import com.example.managefootball.data.ScoreDao
import com.example.managefootball.data.TeamDao
import com.example.managefootball.repository.RuleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePlayerDao(footballDatabase: FootballDatabase): PlayerDao = footballDatabase.playerDao()

    @Singleton
    @Provides
    fun provideTeamDao(footballDatabase: FootballDatabase): TeamDao = footballDatabase.teamDao()

    @Singleton
    @Provides
    fun provideMatchDao(footballDatabase: FootballDatabase): MatchDao = footballDatabase.matchDao()

    @Singleton
    @Provides
    fun provideScoreDao(footballDatabase: FootballDatabase): ScoreDao = footballDatabase.scoreDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): FootballDatabase
    = Room.databaseBuilder(
        context,
        FootballDatabase::class.java,
        "football_db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideRuleRepository(@ApplicationContext context: Context) = RuleRepository(context)
}