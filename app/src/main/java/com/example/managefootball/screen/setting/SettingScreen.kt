package com.example.managefootball.screen.setting

import android.graphics.Paint.Align
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.nav.MainScreen
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.GraySecondTextColor
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.GreenBackground
import com.example.managefootball.util.BottomBar
import com.example.managefootball.util.InputField
import com.example.managefootball.util.extractNumbers

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SettingScreen(modifier: Modifier = Modifier, navController: NavController, settingViewModel: SettingViewModel = hiltViewModel()){
    val minYears by settingViewModel.minYears.collectAsState()
    val maxYears by settingViewModel.maxYears.collectAsState()
    val minPlayers by settingViewModel.minPlayers.collectAsState()
    val maxPlayers by settingViewModel.maxPlayers.collectAsState()
    val maxForeignPlayers by settingViewModel.maxForeignPlayers.collectAsState()
    val maxScores by settingViewModel.maxScores.collectAsState()
    val maxMinutes by settingViewModel.maxMinutes.collectAsState()
    val winScore by settingViewModel.winScore.collectAsState()
    val tieScore by settingViewModel.tieScore.collectAsState()
    val loseScore by settingViewModel.loseScore.collectAsState()
    val priorityTotalScore by settingViewModel.priorityTotalScore.collectAsState()
    val priorityNumberDifferent by settingViewModel.priorityNumberDifferent.collectAsState()
    val priorityTotalGoal by settingViewModel.priorityTotalGoal.collectAsState()
    val priorityTotalMatch by settingViewModel.priorityTotalMatch.collectAsState()

    var minYearState by remember { mutableStateOf(minYears) }
    var maxYearsState by remember { mutableStateOf(maxYears) }
    var minPlayersState by remember { mutableStateOf(minPlayers) }
    var maxPlayersState by remember { mutableStateOf(maxPlayers) }
    var maxForeignPlayersState by remember { mutableStateOf(maxForeignPlayers) }
    var maxScoresState by remember { mutableStateOf(maxScores) }
    var maxMinutesState by remember { mutableStateOf(maxMinutes) }
    var winScoreState by remember { mutableStateOf(winScore) }
    var tieScoreState by remember { mutableStateOf(tieScore) }
    var loseScoreState by remember { mutableStateOf(loseScore) }
    var priorityTotalScoreState by remember { mutableStateOf(priorityTotalScore) }
    var priorityNumberDifferentState by remember { mutableStateOf(priorityNumberDifferent) }
    var priorityTotalGoalState by remember { mutableStateOf(priorityTotalGoal) }
    var priorityTotalMatchState by remember { mutableStateOf(priorityTotalMatch) }
    val listPriority = listOf(1,2,3,4)
    val listSuitable = listOf(priorityTotalScoreState,priorityNumberDifferentState, priorityTotalGoalState, priorityTotalMatchState)
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    // minPlayer >= 1, maxScore <=7, maxMinute >= 20, priority : 1->4
    isError = minYearState<1 || minYearState>maxYearsState || minPlayersState<1 || minPlayersState>maxPlayersState || maxScoresState>7 ||
            maxMinutesState<20 || winScoreState<=tieScoreState || tieScoreState<=loseScoreState
            || !listPriority.containsAll(listSuitable) || listSuitable.size != listSuitable.distinct().size

    LaunchedEffect(key1 = true ){
        minYearState = minYears
        maxYearsState = maxYears
        minPlayersState = minPlayers
        maxPlayersState = maxPlayers
        maxForeignPlayersState = maxForeignPlayers
        maxScoresState = maxScores
        maxMinutesState = maxMinutes
        winScoreState = winScore
        tieScoreState = tieScore
        loseScoreState = loseScore
        priorityTotalScoreState = priorityTotalScore
        priorityNumberDifferentState = priorityNumberDifferent
        priorityTotalGoalState = priorityTotalGoal
        priorityTotalMatchState = priorityTotalMatch
    }

    LaunchedEffect(key1 = minYears){
        minYearState = minYears
    }
    LaunchedEffect(key1 = maxYears){
        maxYearsState = maxYears
    }
    LaunchedEffect(key1 = minPlayers){
        minPlayersState = minPlayers
    }
    LaunchedEffect(key1 = maxPlayers){
        maxPlayersState = maxPlayers
    }
    LaunchedEffect(key1 = maxForeignPlayers){
        maxForeignPlayersState = maxForeignPlayers
    }
    LaunchedEffect(key1 = maxScores){
        maxScoresState = maxScores
    }
    LaunchedEffect(key1 = maxMinutes){
        maxMinutesState = maxMinutes
    }
    LaunchedEffect(key1 = winScore){
        winScoreState = winScore
    }
    LaunchedEffect(key1 = tieScore){
        tieScoreState = tieScore
    }
    LaunchedEffect(key1 = loseScore){
        loseScoreState = loseScore
    }
    LaunchedEffect(key1 = priorityTotalScore){
        priorityTotalScoreState = priorityTotalScore
    }
    LaunchedEffect(key1 = priorityNumberDifferent){
        priorityNumberDifferentState = priorityNumberDifferent
    }
    LaunchedEffect(key1 = priorityTotalGoal){
        priorityTotalGoalState = priorityTotalGoal
    }
    LaunchedEffect(key1 = priorityTotalMatch){
        priorityTotalMatchState = priorityTotalMatch
    }

    Log.e("Tpoo", "value: $minYears - $maxYears - $minPlayers - $maxPlayers")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController)}
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = modifier
                    .background(GreenBackground)
                    .fillMaxSize()
                    .weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                ) {
                Text(text = "Cài đặt quy định", fontWeight = FontWeight.SemiBold, fontSize = 30.sp, color = Color.White,
                    modifier = modifier.padding(3.dp))

            }
//            Row(modifier = modifier
//                .fillMaxWidth()
//                .height(50.dp), verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Cài đặt",
//                    fontSize = 25.sp,
//                    modifier = modifier
//                        .weight(0.8f),
//                    textAlign = TextAlign.Center
//                )
//                Button(onClick = {
//
//                    if (!isError){
//                        settingViewModel.setMinYears(minYearState)
//                        settingViewModel.setMaxYears(maxYearsState)
//                        settingViewModel.setMinPlayers(minPlayersState)
//                        settingViewModel.setMaxPlayers(maxPlayersState)
//                        settingViewModel.setMaxForeignPlayers(maxForeignPlayersState)
//                        settingViewModel.setMaxScores(maxScoresState)
//                        settingViewModel.setMaxMinutes(maxMinutesState)
//                        settingViewModel.setWinScore(winScoreState)
//                        settingViewModel.setTieScore(tieScoreState)
//                        settingViewModel.setLoseScore(loseScoreState)
//                        settingViewModel.setPriorityTotalScore(priorityTotalScoreState)
//                        settingViewModel.setPriorityNumberDifferent(priorityNumberDifferentState)
//                        settingViewModel.setPriorityTotalGoal(priorityTotalGoalState)
//                        settingViewModel.setPriorityTotalMatch(priorityTotalMatchState)
//                        Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show()
//                        keyboard?.hide()
//                        navController.navigate(MainScreen.HomeScreen.route)
//                    }
//                }, modifier = modifier
//                    .weight(0.3f), colors = ButtonDefaults.buttonColors(
//                        if (!isError) Green else GraySecondTextColor.copy(0.3f)
//                    )) {
//                    Text(text = "Save", fontSize = 17.sp, textAlign = TextAlign.Center, modifier = modifier.fillMaxSize())
//                }
//            }
            LazyColumn(modifier = modifier
                .fillMaxSize()
                .weight(0.85f)
                .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start){
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Đội",
                            fontSize = 25.sp,
                            modifier = modifier.fillMaxSize(),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Medium
                        )

                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = modifier
                                .fillMaxWidth()
                                .weight(0.7f), verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = "Độ tuổi tối thiểu",
                                    fontSize = 20.sp,
                                    modifier = modifier
                                        .fillMaxSize(),
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = "Độ tuổi tối thiểu phải lớn hơn 0",
                                    modifier = Modifier.padding(bottom = 10.dp,  end = 10.dp),
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = ErrorColor
                                )
                            }

                            InputField(
                                value = minYearState.toString(),
                                onValueChange = {
                                    if (it == "") minYearState = 0
                                    else if (minYearState == 0 && it.endsWith('0')) minYearState = it.toInt()/10
                                    else minYearState = extractNumbers(it).toInt()
                                },
                                textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)
                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Độ tuổi tối đa",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = maxYearsState.toString(),
                                onValueChange = {
                                    if (it == "") maxYearsState = 0
                                    else if (maxYearsState == 0 && it.endsWith('0')) maxYearsState = it.toInt()/10
                                    else maxYearsState = extractNumbers(it).toInt()
                                },
                                labelId = "",textAlign = TextAlign.Center,
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = modifier
                                .fillMaxWidth()
                                .weight(0.7f), verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = "Số lượng cầu thủ tối thiểu",
                                    fontSize = 20.sp,
                                    modifier = modifier
                                        .fillMaxSize(),
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = "Số lượng cầu thủ tối thiểu phải lớn hơn 0",
                                    modifier = Modifier.padding(bottom = 10.dp,  end = 10.dp),
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = ErrorColor
                                )
                            }
                            InputField(
                                value = minPlayersState.toString(),
                                onValueChange = {
                                    if (it == "") minPlayersState = 0
                                    else if (minPlayersState == 0 && it.endsWith('0')) minPlayersState = it.toInt()/10
                                    else minPlayersState = extractNumbers(it).toInt()
                                },
                                labelId = "",textAlign = TextAlign.Center,
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Số lượng cầu thủ tối đa",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = maxPlayersState.toString(),
                                onValueChange = {
                                    if (it == "") maxPlayersState = 0
                                    else if (maxPlayersState == 0 && it.endsWith('0')) maxPlayersState = it.toInt()/10
                                    else maxPlayersState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Số lượng cầu thủ nước ngoài tối đa",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = maxForeignPlayersState.toString(),
                                onValueChange = {
                                    if (it == "") maxForeignPlayersState = 0
                                    else if (maxForeignPlayersState == 0 && it.endsWith('0')) maxForeignPlayersState = it.toInt()/10
                                    else maxForeignPlayersState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                    }
                }
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Trận đấu",
                            fontSize = 25.sp,
                            modifier = modifier.fillMaxSize(),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Medium
                        )

                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = modifier
                                .fillMaxWidth()
                                .weight(0.7f), verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = "Số lượng loại bàn thắng",
                                    fontSize = 20.sp,
                                    modifier = modifier
                                        .fillMaxSize(),
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = "Số lượng loại bàn thắng tối đa là 7",
                                    modifier = Modifier.padding(bottom = 10.dp,  end = 10.dp),
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = ErrorColor
                                )
                            }
                            InputField(
                                value = maxScoresState.toString(),
                                onValueChange = {
                                    if (it == "") maxScoresState = 0
                                    else if (maxScoresState == 0 && it.endsWith('0')) maxScoresState = it.toInt()/10
                                    else maxScoresState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = modifier
                                .fillMaxWidth()
                                .weight(0.7f), verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = "Thời điểm ghi bàn tối đa",
                                    fontSize = 20.sp,
                                    modifier = modifier
                                        .fillMaxSize(),
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = "Thời điểm ghi bàn tối đa >= 20",
                                    modifier = Modifier.padding(
                                        bottom = 10.dp,

                                        end = 10.dp
                                    ),
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = ErrorColor
                                )
                            }
                            InputField(
                                value = maxMinutesState.toString(),
                                onValueChange = {
                                    if (it == "") maxMinutesState = 0
                                    else if (maxMinutesState == 0 && it.endsWith('0')) maxMinutesState = it.toInt()/10
                                    else maxMinutesState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                    }
                }
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Điểm số",
                                fontSize = 25.sp,
                                modifier = modifier.fillMaxSize(),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Điểm thắng > Điểm hòa > Điểm thua",
                                modifier = Modifier.padding(
                                    bottom = 10.dp,

                                    end = 10.dp
                                ),
                                fontStyle = FontStyle.Italic,
                                fontSize = 15.sp,
                                color = ErrorColor
                            )
                        }


                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Điểm thắng",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = winScoreState.toString(),
                                onValueChange = {
                                    if (it == "") winScoreState = 0
                                    else if (winScoreState == 0 && it.endsWith('0')) winScoreState = it.toInt()/10
                                    else winScoreState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Điểm hòa",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = tieScoreState.toString(),
                                onValueChange = {
                                    if (it == "") tieScoreState = 0
                                    else if (tieScoreState == 0 && it.endsWith('0')) tieScoreState = it.toInt()/10
                                    else tieScoreState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Điểm thua",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = loseScoreState.toString(),
                                onValueChange = {
                                    if (it == "") loseScoreState = 0
                                    else if (loseScoreState == 0 && it.endsWith('0')) loseScoreState = it.toInt()/10
                                    else loseScoreState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                    }
                }
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Độ ưu tiên",
                                fontSize = 25.sp,
                                modifier = modifier.fillMaxSize(),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Độ ưu tiên theo thứ tự: 1 - 2 - 3 - 4",
                                modifier = Modifier.padding(
                                    bottom = 10.dp,

                                    end = 10.dp
                                ),
                                fontStyle = FontStyle.Italic,
                                fontSize = 15.sp,
                                color = ErrorColor
                            )
                        }


                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Điểm số",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = priorityTotalScoreState.toString(),
                                onValueChange = {
                                    if (it == "") priorityTotalScoreState = 0
                                    else if (priorityTotalScoreState == 0 && it.endsWith('0')) priorityTotalScoreState = it.toInt()/10
                                    else priorityTotalScoreState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Hiệu số",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = priorityNumberDifferentState.toString(),
                                onValueChange = {
                                    if (it == "") priorityNumberDifferentState = 0
                                    else if (priorityNumberDifferentState == 0 && it.endsWith('0')) priorityNumberDifferentState = it.toInt()/10
                                    else priorityNumberDifferentState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Tổng số bàn thắng",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = priorityTotalGoalState.toString(),
                                onValueChange = {
                                    if (it == "") priorityTotalGoalState = 0
                                    else if (priorityTotalGoalState == 0 && it.endsWith('0')) priorityTotalGoalState = it.toInt()/10
                                    else priorityTotalGoalState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Đối kháng",
                                fontSize = 20.sp,
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.7f),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal
                            )
                            InputField(
                                value = priorityTotalMatchState.toString(),
                                onValueChange = {
                                    if (it == "") priorityTotalMatchState = 0
                                    else if (priorityTotalMatchState == 0 && it.endsWith('0')) priorityTotalMatchState = it.toInt()/10
                                    else priorityTotalMatchState = extractNumbers(it).toInt()
                                },textAlign = TextAlign.Center,
                                labelId = "",
                                keyboardType = KeyboardType.Number
                                , modifier = modifier.weight(0.3f)

                            )
                        }
                    }
                }
                item{
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            if (!isError){
                                settingViewModel.setMinYears(minYearState)
                                settingViewModel.setMaxYears(maxYearsState)
                                settingViewModel.setMinPlayers(minPlayersState)
                                settingViewModel.setMaxPlayers(maxPlayersState)
                                settingViewModel.setMaxForeignPlayers(maxForeignPlayersState)
                                settingViewModel.setMaxScores(maxScoresState)
                                settingViewModel.setMaxMinutes(maxMinutesState)
                                settingViewModel.setWinScore(winScoreState)
                                settingViewModel.setTieScore(tieScoreState)
                                settingViewModel.setLoseScore(loseScoreState)
                                settingViewModel.setPriorityTotalScore(priorityTotalScoreState)
                                settingViewModel.setPriorityNumberDifferent(priorityNumberDifferentState)
                                settingViewModel.setPriorityTotalGoal(priorityTotalGoalState)
                                settingViewModel.setPriorityTotalMatch(priorityTotalMatchState)
                                Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show()
                                keyboard?.hide()
                                navController.navigate(MainScreen.HomeScreen.route)
                            } else {
                                Toast.makeText(context,"Vi phạm quy định! Vui lòng xem lại và điều chỉnh các quy định sao cho thích hợp!", Toast.LENGTH_LONG).show()
                            }
                        },
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(GreenBackground),
                            modifier = modifier
                                .fillMaxWidth(0.95f)
                                .padding(8.dp)) {
                            Text(text = "Lưu",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}