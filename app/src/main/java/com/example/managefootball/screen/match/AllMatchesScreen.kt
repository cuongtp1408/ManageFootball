package com.example.managefootball.screen.match

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.model.Match
import com.example.managefootball.model.MatchTeams
import com.example.managefootball.model.Team
import com.example.managefootball.nav.NavScreen
import com.example.managefootball.ui.theme.Black
import com.example.managefootball.ui.theme.BlackBackground
import com.example.managefootball.ui.theme.BlueCard
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.GreenBackground
import com.example.managefootball.ui.theme.WhiteBackground
import com.example.managefootball.ui.theme.WhiteGrayBackground
import com.example.managefootball.ui.theme.Yellow
import com.example.managefootball.util.BottomBar
import com.example.managefootball.util.Constant.STATUS_DONE
import com.example.managefootball.util.Constant.STATUS_PROGRESS
import com.example.managefootball.util.convertStringToDate
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllMatchesScreen(modifier: Modifier = Modifier,navController: NavController,
                     allMatchesViewModel: AllMatchesViewModel = hiltViewModel() ) {

    val context = LocalContext.current
    val listMatches by allMatchesViewModel.listMatches.collectAsState()
    val listMatchTeams by allMatchesViewModel.listMatchTeams.collectAsState()
    val isLoading = allMatchesViewModel.isLoading
    val currentDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now())

    LaunchedEffect(key1 = listMatches){
        allMatchesViewModel.getAllListMatchTeams()
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(NavScreen.AddMatchScreen.route) },
                containerColor = GreenBackground,
                elevation = FloatingActionButtonDefaults.elevation(5.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add match", tint = Color.White)
            }
        }
    ) { paddingValues ->
        if (isLoading.value) {
            Column(
                modifier = modifier
                    .background(BlackBackground)
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                CircularProgressIndicator()
            }
        } else {
        Column(modifier = modifier
            .background(BlackBackground)
            .padding(paddingValues)
            .fillMaxSize()) {
            Column(
                modifier = modifier
                    .background(GreenBackground)
                    .fillMaxSize()
                    .weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {
                Text(text = "Lịch thi đấu", fontWeight = FontWeight.SemiBold, fontSize = 30.sp, color = Color.White,
                    modifier = modifier.padding(3.dp))
            }
            if (listMatches.isEmpty()){
                Column(
                    modifier = modifier.fillMaxSize().weight(0.85f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = "Ooops! No Data",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize().weight(0.85f)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(listMatchTeams.size) { index ->
                        val isDone =
                            convertStringToDate(listMatches[index].day) <= convertStringToDate(
                                currentDate
                            )
                        MatchCard(
                            match = listMatches[index],
                            team1 = listMatchTeams[index].team1,
                            team2 = listMatchTeams[index].team2,
                            isDone = isDone
                        ) { done ->
                            if (listMatches[index].status == STATUS_DONE) {
                                navController.navigate(NavScreen.MatchDetailScreen.route + "/${listMatches[index].idMatch}")
                            } else if (listMatches[index].status == STATUS_PROGRESS && !done) {
                                Toast.makeText(
                                    context,
                                    "Chưa đến ngày diễn ra trận đấu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                navController.navigate(NavScreen.AddResultMatchScreen.route + "/${listMatches[index].idMatch}")
                            }
                        }
                    }

                }
            }
            }
        }
    }
}

@Composable
fun MatchCard(modifier: Modifier = Modifier, match: Match, team1: Team, team2: Team, isDone: Boolean, onClick:(Boolean) -> Unit = {}){
    Card(modifier = modifier
        .fillMaxWidth()
        .height(65.dp)
        .clickable {
            onClick(isDone)
        }, shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = WhiteBackground
//            containerColor = if (match.status == STATUS_PROGRESS && isDone) BlueCard.copy(0.6f) else if (match.status == STATUS_PROGRESS && !isDone)  Yellow else Green
        )) {
        Row(modifier = modifier
            .fillMaxSize().padding(horizontal = 25.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = team1.nameTeam,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = modifier.padding(8.dp).fillMaxWidth().weight(0.3f), textAlign = TextAlign.Left)

            if (match.status == STATUS_PROGRESS && !isDone){
                Column( horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth().weight(0.5f)
                    ) {
                    Row(
                        modifier = modifier.fillMaxWidth().background(WhiteGrayBackground),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = match.day,
                            color = Black,modifier = modifier.padding(3.dp),
                            fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = modifier.height(3.dp))
                    Text(text = STATUS_PROGRESS,
                        color = ErrorColor,modifier = modifier.padding(3.dp),
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            } else if (match.status == STATUS_PROGRESS && isDone) {
                Column( horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth().weight(0.5f)
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth().background(WhiteGrayBackground),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = match.day,
                            color = Black,modifier = modifier.padding(3.dp),
                            fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Text(text = "Chưa ghi kết quả",
                        color = ErrorColor,modifier = modifier.padding(3.dp),
                        fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            } else {
                Row(modifier = modifier.background(WhiteGrayBackground).fillMaxWidth(0.5f), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    Text(
                        text = match.resultTeam1.toString(),
                        color = Black,
                        modifier = modifier.padding(3.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "-",
                        color = Black,
                        modifier = modifier.padding(3.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = match.resultTeam2.toString(),
                        color =Black,
                        modifier = modifier.padding(3.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Right
                    )
                }
            }

            Text(text = team2.nameTeam,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = modifier.padding(8.dp).fillMaxWidth().weight(0.3f), textAlign = TextAlign.Right)
        }
    }
}