package com.example.managefootball.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.model.Team
import com.example.managefootball.nav.NavScreen
import com.example.managefootball.screen.matchdetail.ScoreCard
import com.example.managefootball.ui.theme.BlueCard
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.Yellow
import com.example.managefootball.util.BottomBar
import com.example.managefootball.util.Constant.LOSE
import com.example.managefootball.util.Constant.TIE
import com.example.managefootball.util.Constant.WIN
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController,homeViewModel: HomeViewModel = hiltViewModel() ){

    val listTeams by homeViewModel.listTeams.collectAsState()
    val priorityTotalScore by homeViewModel.priorityTotalScore.collectAsState()
    val priorityNumberDifferent by homeViewModel.priorityNumberDifferent.collectAsState()
    val priorityTotalGoal by homeViewModel.priorityTotalGoal.collectAsState()
    val priorityTotalMatch by homeViewModel.priorityTotalMatch.collectAsState()
    val winScore by homeViewModel.winScore.collectAsState()
    val tieScore by homeViewModel.tieScore.collectAsState()
    val loseScore by homeViewModel.loseScore.collectAsState()
    val listTotalScore = listTeams.map { it.lose*loseScore + it.tie*tieScore + it.win*winScore }

    val listTeam_TotalScore = listTeams.zip(listTotalScore).sortedWith(
        compareBy(
            {if (priorityTotalScore == 1) -it.second else if (priorityNumberDifferent==1) -it.first.numberDiff else if (priorityTotalGoal==1) -it.first.totalGoal else  -(it.first.win+it.first.lose+it.first.tie)},
            {if (priorityTotalScore == 2) -it.second else if (priorityNumberDifferent==2) -it.first.numberDiff else if (priorityTotalGoal==2) -it.first.totalGoal else  -(it.first.win+it.first.lose+it.first.tie)},
            {if (priorityTotalScore == 3) -it.second else if (priorityNumberDifferent==3) -it.first.numberDiff else if (priorityTotalGoal==3) -it.first.totalGoal else  -(it.first.win+it.first.lose+it.first.tie)},
            {if (priorityTotalScore == 4) -it.second else if (priorityNumberDifferent==4) -it.first.numberDiff else if (priorityTotalGoal==4) -it.first.totalGoal else  -(it.first.win+it.first.lose+it.first.tie)}
        )
    )

    val currentDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(currentDate)

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(NavScreen.RegisterTeamScreen.route) },
                containerColor = Green,
                elevation = FloatingActionButtonDefaults.elevation(5.dp)
                ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add team", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .weight(0.15f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Bảng xếp hạng", fontWeight = FontWeight.SemiBold, fontSize = 25.sp, color = Green,
                    modifier = modifier.padding(3.dp))
                Text(text = "Ngày: $formattedDate", fontWeight = FontWeight.SemiBold, fontSize = 25.sp, color = Green,
                    modifier = modifier.padding(3.dp))

            }
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .height(50.dp), shape = RoundedCornerShape(10.dp),

                colors = CardDefaults.cardColors(
                    containerColor = GrayBackground
                )
            ) {
                Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Hạng",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.15f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Đội",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.3f),
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "Thắng",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.3f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Hòa",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.2f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Thua",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.2f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Hiệu số",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.2f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Điểm số",
                        fontSize = 17.sp,
                        modifier = modifier.weight(0.2f),
                        textAlign = TextAlign.Center
                    )
                }

            }

            LazyColumn(modifier = modifier
                .fillMaxSize()
                .weight(0.85f)
                .padding(5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                items(listTeam_TotalScore.size){ index ->
                    TeamCard(team = listTeam_TotalScore[index].first, rank = index+1, score = listTeam_TotalScore[index].second)
                }
            }
        }
    }
}

@Composable
fun TeamCard(modifier: Modifier = Modifier, team: Team?, rank: Int, score: Int = 0){
    team?.let {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp), shape = RoundedCornerShape(10.dp),

            colors = CardDefaults.cardColors(
                containerColor = if (rank % 2 == 0) BlueCard.copy(0.7f) else Yellow.copy(0.4f)
            )
        ) {
            Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$rank",
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.15f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = team.nameTeam,
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.3f),
                    textAlign = TextAlign.Left
                )
                Text(
                    text = team.win.toString(),
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = team.tie.toString(),
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = team.lose.toString(),
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if (team.numberDiff>=0) "+${team.numberDiff}" else "${team.numberDiff}",
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.2f),
                    textAlign = TextAlign.Center,
                    color = if (team.numberDiff>=0) Green else ErrorColor
                )
                Text(
                    text = score.toString(),
                    fontSize = 17.sp,
                    modifier = modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}