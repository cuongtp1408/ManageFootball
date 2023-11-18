package com.example.managefootball.util

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.managefootball.model.Player
import com.example.managefootball.model.Score
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.util.Constant.STATUS_DONE
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.UUID
import kotlin.random.Random

// Chưa check type score, time dài
@HiltWorker
class AddMatchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: FootballRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        val idMatchString = inputData.getString("idMatch")!!
        val idTeam1String = inputData.getString("idTeam1")!!
        val idTeam2String = inputData.getString("idTeam2")!!
        val idMatch = UUID.fromString(idMatchString)
        val idTeam1 = UUID.fromString(idTeam1String)
        val idTeam2 = UUID.fromString(idTeam2String)

        val team1 = repository.getTeamById(idTeam1String)
        val team2 = repository.getTeamById(idTeam2String)

        val listPlayersTeam1 = repository.getPlayerWithTeamId(idTeam1String)
        val listPlayersTeam2 = repository.getPlayerWithTeamId(idTeam2String)

        val randomResult = Random.nextInt(3,8)
        var randomResultTeam1 = Random.nextInt(0,5)
        var randomResultTeam2 = 0

        if (randomResult>randomResultTeam1){
            randomResultTeam2 = randomResult - randomResultTeam1
        }

        Log.e("Tpoo", "rsT1: " + randomResultTeam1)
        Log.e("Tpoo", "rsT2: " + randomResultTeam2)

        val listScoresTeam1 = randomScorePlayers(listPlayersTeam1,randomResultTeam1, idMatch)
        val listScoresTeam2 = randomScorePlayers(listPlayersTeam2,randomResultTeam2,idMatch)

        Log.e("Tpoo", "team1: " + listScoresTeam1)
        Log.e("Tpoo", "team2: " + listScoresTeam2)


        randomResultTeam1 = listScoresTeam1.size
        randomResultTeam2 = listScoresTeam2.size

        if (randomResultTeam1 == randomResultTeam2){
            repository.updateTeam(team1.copy(tie = team1.tie+1))
            repository.updateTeam(team2.copy(tie = team2.tie+1))
        } else if (randomResultTeam1 > randomResultTeam2){
            repository.updateTeam(team1.copy(win = team1.win + 1))
            repository.updateTeam(team2.copy(lose = team2.lose + 1))
        } else {
            repository.updateTeam(team1.copy(lose = team1.lose + 1))
            repository.updateTeam(team2.copy(win = team2.win + 1))
        }

        val match = repository.getMatchById(idMatchString)
        repository.addScores(listScoresTeam1)
        repository.addScores(listScoresTeam2)

        listScoresTeam1.forEach{ score ->
           val player = repository.getPlayerById(score.idPlayer.toString())
           repository.updatePlayer(player = player.copy(totalGoal = player.totalGoal+1))
        }
        listScoresTeam2.forEach{ score ->
            val player = repository.getPlayerById(score.idPlayer.toString())
            repository.updatePlayer(player = player.copy(totalGoal = player.totalGoal+1))
        }

        repository.updateMatch(match.copy(resultTeam1 = randomResultTeam1, resultTeam2 = randomResultTeam2,status = STATUS_DONE))
        return Result.success()
    }

    private fun randomScorePlayers(players: List<Player>, num: Int, idMatch: UUID): List<Score>{
        val listScore = mutableListOf<Score>()
        for (i in 0 until num ){
            val selectedPlayer = Random.nextInt(0,players.size)
            val selectedMinute = Random.nextInt(0,91)
            val score = Score(idMatch = idMatch, idPlayer = players[selectedPlayer].idPlayer, time = selectedMinute, typeScore = "A")
            listScore.add(score)
        }
        return listScore.distinct()
    }


}