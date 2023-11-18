package com.example.managefootball.screen.matchdetail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.ui.theme.Black
import com.example.managefootball.ui.theme.BlueCard
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.GraySecondTextColor
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.Yellow
import com.example.managefootball.util.Constant
import com.example.managefootball.util.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(modifier: Modifier = Modifier,navController: NavController,matchId: String,
                      matchDetailViewModel: MatchDetailViewModel = hiltViewModel()){

    val listScores by matchDetailViewModel.listScores.collectAsState()
    val listPlayers by matchDetailViewModel.listPlayers.collectAsState()
    val match by matchDetailViewModel.match.collectAsState()
    val team1 by matchDetailViewModel.team1.collectAsState()
    val team2 by matchDetailViewModel.team2.collectAsState()
    val isLoading by matchDetailViewModel.isLoading


    LaunchedEffect(key1 = true){
        matchDetailViewModel.getMatchDetail(matchId)
        matchDetailViewModel.getListScores(matchId)

    }


    LaunchedEffect(key1 = match, key2 = listScores){
        match?.let {
            matchDetailViewModel.getTeam1ById(it.idTeam1.toString())
            matchDetailViewModel.getTeam2ById(it.idTeam2.toString())
        }
        matchDetailViewModel.getListPlayersScore()
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = { TopBar(title = "Detail Result Match"){
            navController.popBackStack()
        }}
    ) { paddingValues ->
        if (isLoading){
            CircularProgressIndicator()
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(modifier = modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .weight(0.3f),
                    colors = CardDefaults.cardColors(containerColor = GrayBackground)) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()

                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxSize()
                                .weight(0.5f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = team1?.nameTeam ?: "",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Green,
                                modifier = modifier.padding(8.dp), textAlign = TextAlign.Left
                            )
                            Text(
                                text = match?.resultTeam1.toString(),
                                color = ErrorColor,
                                modifier = modifier.padding(8.dp),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "-",
                                color = Black,
                                modifier = modifier.padding(8.dp),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = match?.resultTeam2.toString(),
                                color = ErrorColor,
                                modifier = modifier.padding(8.dp),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Right
                            )
                            Text(
                                text = team2?.nameTeam ?: "",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Green,
                                modifier = modifier.padding(8.dp), textAlign = TextAlign.Right
                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxSize()
                                .weight(0.25f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sân: ${team1?.yardTeam ?: ""}",
                                color = Black,
                                modifier = modifier.padding(8.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxSize()
                                .weight(0.25f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ngày: ${match?.day}",
                                color = Black,
                                modifier = modifier.padding(8.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "Giờ: ${match?.time}",
                                color = Black,
                                modifier = modifier.padding(8.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Right
                            )
                        }
                    }

                }
                Card(modifier = modifier
                    .fillMaxWidth().padding(5.dp)
                    .height(50.dp)
                    , shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GrayBackground
                    )) {
                    Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "STT",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Cầu thủ",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.3f),
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "Đội",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.3f),
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "Loại bàn thắng",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.2f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Thời điểm",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.2f),
                            textAlign = TextAlign.Center
                        )
                    }

                }
                LazyColumn(modifier = modifier
                    .fillMaxSize()
                    .weight(0.75f)
                    .padding(5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    items(listPlayers.size){ index ->
                        val nameTeam = if (listPlayers[index].idTeam==team1!!.idTeam) team1!!.nameTeam else team2!!.nameTeam
                        ScoreCard(num = index+1, name = listPlayers[index].namePlayer,
                            nameTeam = nameTeam, typeScore = listScores[index].typeScore, minute = listScores[index].time)
                    }
                }
            }
        }
    }

}

@Composable
fun ScoreCard(modifier: Modifier = Modifier, num: Int, name: String, nameTeam: String, typeScore: String, minute: Int){
    Card(modifier = modifier
        .fillMaxWidth()
        .height(50.dp)
        , shape = RoundedCornerShape(10.dp),

        colors = CardDefaults.cardColors(
            containerColor = if (num%2==0) BlueCard.copy(0.7f) else Yellow.copy(0.4f)
        )) {
        Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$num",
                fontSize = 17.sp,
                modifier = modifier.weight(0.1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = name,
                fontSize = 17.sp,
                modifier = modifier.weight(0.3f),
                textAlign = TextAlign.Left
            )
            Text(
                text = nameTeam,
                fontSize = 17.sp,
                modifier = modifier.weight(0.3f),
                textAlign = TextAlign.Left
            )
            Text(
                text = typeScore,
                fontSize = 17.sp,
                modifier = modifier.weight(0.2f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$minute",
                fontSize = 17.sp,
                modifier = modifier.weight(0.2f),
                textAlign = TextAlign.Center
            )
        }

    }
}