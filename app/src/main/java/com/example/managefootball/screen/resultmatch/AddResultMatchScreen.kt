package com.example.managefootball.screen.resultmatch

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.model.Player
import com.example.managefootball.model.Score
import com.example.managefootball.model.Team
import com.example.managefootball.nav.MainScreen
import com.example.managefootball.ui.theme.Black
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.GraySecondTextColor
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.util.Constant
import com.example.managefootball.util.InputField
import com.example.managefootball.util.InputFieldWithTrailingIcon
import com.example.managefootball.util.TopBar
import com.example.managefootball.util.createAlphabetList
import com.example.managefootball.util.extractNumbers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResultMatchScreen(navController: NavController, modifier: Modifier = Modifier,
                         addResultMatchViewModel: AddResultMatchViewModel = hiltViewModel(), matchId: String){
    val maxScores by addResultMatchViewModel.maxScores.collectAsState()
    val maxMinutes by addResultMatchViewModel.maxMinutes.collectAsState()
    val context = LocalContext.current
    val match by addResultMatchViewModel.match.collectAsState()
    val isLoading = addResultMatchViewModel.isLoading
    val team1 by addResultMatchViewModel.team1.collectAsState()
    val team2 by addResultMatchViewModel.team2.collectAsState()
    val listPlayersTeam1 by addResultMatchViewModel.listPlayersTeam1.collectAsState()
    val listPlayersTeam2 by addResultMatchViewModel.listPlayersTeam2.collectAsState()
    val listTypeScores = createAlphabetList(maxScores)
    var scoreTeam1 by remember { mutableStateOf(0) }
    var scoreTeam2 by remember { mutableStateOf(0) }
    val listTeam = remember { mutableStateListOf<Team?>() }

    val listPlayerScore = remember{ mutableStateListOf<Pair<Player,Score>>()}
    var typeScore by remember { mutableStateOf("A") }
    var minuteScore by remember { mutableStateOf(0) }
    var indexPlayer by remember { mutableStateOf<Int?>(null) }
    var indexTeam by remember { mutableStateOf(0) }
    var expandedDropdownTeam by remember { mutableStateOf(false) }
    var expandedDropdownPlayer by remember { mutableStateOf(false) }
    var expandedDropdownTypeScore by remember { mutableStateOf(false) }

    var namePlayerError by remember { mutableStateOf(false) }
    var numberPlayerScoreError by remember { mutableStateOf(false) }
    var minuteScoreError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(key1 = true ){
        addResultMatchViewModel.getMatchById(matchId)
    }

    LaunchedEffect(key1 = match){
        match?.let {
            addResultMatchViewModel.getTeam1ById(it.idTeam1.toString())
            addResultMatchViewModel.getTeam2ById(it.idTeam2.toString())
            addResultMatchViewModel.getListPlayersTeam1(it.idTeam1.toString())
            addResultMatchViewModel.getListPlayersTeam2(it.idTeam2.toString())
        }
    }

    LaunchedEffect(key1 = team1, key2 = team2){
        listTeam.clear()
        listTeam.add(team1)
        listTeam.add(team2)
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = { TopBar(title = "Add Result Match"){
            navController.popBackStack()
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .weight(0.85f)
                        .verticalScroll(rememberScrollState())

                ) {
                    Card(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .height(150.dp),
                        colors = CardDefaults.cardColors(containerColor = GrayBackground)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxSize()
                                    .weight(0.35f),
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
                                    text = "-",
                                    color = Black,
                                    modifier = modifier.padding(8.dp),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
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

                    Text(
                        text = "- Thời điểm ghi bàn phải <= $maxMinutes",
                        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                        fontStyle = FontStyle.Italic,
                        fontSize = 15.sp,
                        color = ErrorColor
                    )

                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .weight(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Tỉ số đội 1",
                                modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Black
                            )
                            InputField(value = scoreTeam1.toString(), onValueChange = {
                                if (it == "") scoreTeam1 = 0
                                else if (scoreTeam1 == 0 && it.endsWith('0')) scoreTeam1 = it.toInt() / 10
                                else scoreTeam1 = extractNumbers(it).toInt()
                            }, labelId = "", keyboardType = KeyboardType.Number)
                        }

                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .weight(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Tỉ số đội 2",
                                modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Black
                            )
                            InputField(value = scoreTeam2.toString(), onValueChange = {
                                if (it == "") scoreTeam2 = 0
                                else if (scoreTeam2 == 0 && it.endsWith('0')) scoreTeam2 = it.toInt() / 10
                                else scoreTeam2 = extractNumbers(it).toInt()
                            }, labelId = "", keyboardType = KeyboardType.Number)
                        }

                    }

                    Spacer(modifier = modifier.height(8.dp))

                    // Hiện danh sách cầu thủ
                    listPlayerScore.forEachIndexed { index, pair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(modifier = modifier
                                .fillMaxWidth()
                                .weight(0.9f)) {
                                Text(text = "Cầu thủ ${index+1}:", fontSize = 15.sp, fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Bold, color = GraySecondTextColor,
                                    modifier = modifier
                                        .align(Alignment.Start)
                                        .padding(8.dp))
                                Row(modifier = modifier.fillMaxWidth()) {
                                    InputField(value = pair.first.namePlayer, onValueChange = {
                                      //
                                    }, labelId = "Tên cầu thủ", modifier = modifier.weight(0.7f), readOnly = true)
                                    InputField(value = if (pair.first.idTeam==team1!!.idTeam) team1!!.nameTeam else team2!!.nameTeam,
                                        onValueChange = {
                                        //
                                    }, labelId = "Đội", modifier = modifier.weight(0.3f), readOnly = true)
                                }
                                Row(modifier = modifier.fillMaxWidth()) {
                                    InputField(value = pair.second.typeScore, onValueChange = {
                                        //
                                    }, labelId = "Loại bàn thắng", modifier = modifier.weight(0.5f), readOnly = true
                                    )
                                    InputField(value = pair.second.time.toString(), onValueChange = {
                                        //
                                    }, labelId = "Thời điểm", modifier = modifier.weight(0.5f), readOnly = true)
                                }
                            }

                            IconButton(
                                onClick = { listPlayerScore.removeAt(index) },
                                modifier = modifier
                                    .fillMaxWidth()
                                    .weight(0.1f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = ErrorColor)
                            }
                        }
                    }

                    // Thêm cầu thủ
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Cầu thủ ${listPlayerScore.size+1}:", fontSize = 15.sp, fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold, color = GraySecondTextColor,
                            modifier = modifier
                                .align(Alignment.Start)
                                .padding(8.dp)
                        )
                        Row(modifier = modifier.fillMaxWidth()) {
                            Column(modifier = modifier
                                .weight(0.6f)
                                .fillMaxWidth()) {
                                InputFieldWithTrailingIcon(value = indexPlayer?.let { if (indexTeam == 0) listPlayersTeam1[it].namePlayer
                                else listPlayersTeam2[it].namePlayer } ?: "", onValueChange = {
                                    //
                                }, labelId = "Tên", modifier = modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        IconButton(onClick = { expandedDropdownPlayer = true }) {
                                            Icon(imageVector = if (expandedDropdownPlayer) Icons.Default.KeyboardArrowUp
                                            else Icons.Default.KeyboardArrowDown,
                                                contentDescription = "expand")
                                        }
                                    }, isError = namePlayerError, errorValue = "Vui lòng chọn tên cầu thủ"
                                )
                                DropdownMenu(
                                    expanded = expandedDropdownPlayer,
                                    onDismissRequest = { expandedDropdownPlayer = false },
                                    modifier = modifier.fillMaxWidth(0.7f)
                                ) {
                                    if (indexTeam == 0) {
                                        listPlayersTeam1.forEachIndexed {index, player ->
                                            DropdownMenuItem(text = {
                                                Text(text = player.namePlayer)
                                            }, onClick = {
                                                indexPlayer = index
                                                expandedDropdownPlayer = false
                                                namePlayerError = false
                                                }
                                            )
                                        }
                                    } else {
                                        listPlayersTeam2.forEachIndexed {index, player ->
                                            DropdownMenuItem(text = {
                                                Text(text = player.namePlayer)
                                            }, onClick = {
                                                indexPlayer = index
                                                expandedDropdownPlayer = false
                                                namePlayerError = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Column(modifier = modifier
                                .weight(0.4f)
                                .fillMaxWidth()) {
                                InputFieldWithTrailingIcon(value = listTeam[indexTeam]?.nameTeam ?: "", onValueChange = {
                                    //
                                }, labelId = "Đội", modifier = modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        IconButton(onClick = { expandedDropdownTeam = true }) {
                                            Icon(imageVector = if (expandedDropdownTeam) Icons.Default.KeyboardArrowUp
                                            else Icons.Default.KeyboardArrowDown,
                                                contentDescription = "expand")
                                        }
                                    }
                                )
                                DropdownMenu(
                                    expanded = expandedDropdownTeam,
                                    onDismissRequest = { expandedDropdownTeam = false },
                                    modifier = modifier.fillMaxWidth(0.3f)
                                ) {
                                    listTeam.forEachIndexed {index, team ->
                                        DropdownMenuItem(text = {
                                            Text(text = team?.nameTeam ?: "a")
                                        }
                                            ,onClick = {
                                                indexTeam = index

                                                expandedDropdownTeam = false
                                            })
                                    }
                                }
                            }
                        }
                        Row(modifier = modifier.fillMaxWidth()) {
                            Column(modifier = modifier
                                .weight(0.5f)
                                .fillMaxWidth()) {
                                InputFieldWithTrailingIcon(value = typeScore, onValueChange = {
                                    //
                                }, labelId = "Type", modifier = modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        IconButton(onClick = { expandedDropdownTypeScore = true }) {
                                            Icon(imageVector = if (expandedDropdownTypeScore) Icons.Default.KeyboardArrowUp
                                            else Icons.Default.KeyboardArrowDown,
                                                contentDescription = "expand")
                                        }
                                    }
                                )
                                DropdownMenu(
                                    expanded = expandedDropdownTypeScore,
                                    onDismissRequest = { expandedDropdownTypeScore = false },
                                    modifier = modifier.fillMaxWidth(0.5f)
                                ) {
                                    listTypeScores.forEach { type ->
                                        DropdownMenuItem(text = {
                                            Text(text = type)
                                        }
                                            ,onClick = {
                                                typeScore = type
                                                expandedDropdownTypeScore = false
                                            })
                                    }
                                }
                            }

                            InputField(value = minuteScore.toString(), onValueChange = {
                                if (it == "") minuteScore = 0
                                else if (minuteScore == 0 && it.endsWith('0')) minuteScore = it.toInt()/10
                                else minuteScore = extractNumbers(it).toInt()
                                minuteScoreError = false
                            }, labelId = "Thời điểm", modifier = modifier.weight(0.5f), keyboardType = KeyboardType.Number,
                                isError = minuteScoreError, errorValue = "Thời điểm ghi bàn không phù hợp")
                        }

                        Button(
                            onClick = {
                                if (indexPlayer == null) namePlayerError = true
                                if (minuteScore > maxMinutes) minuteScoreError = true
                                if (!namePlayerError && !minuteScoreError) {
                                    val player =
                                        if (indexTeam == 0) listPlayersTeam1[indexPlayer!!] else listPlayersTeam2[indexPlayer!!]
                                    listPlayerScore.add(
                                        Pair(
                                            first = player,
                                            second = Score(
                                                idMatch = match!!.idMatch,
                                                idPlayer = player.idPlayer,
                                                time = minuteScore,
                                                typeScore = typeScore
                                            )
                                        )
                                    )
                                    typeScore = "A"
                                    minuteScore = 0
                                    indexPlayer = null
                                    indexTeam = 0
                                }
                            },
                            modifier = modifier
                                .fillMaxWidth(0.8f)
                                .padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(Color.White),
                            border = BorderStroke(2.dp, Green)
                        ) {
                            Text("Add Player", fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Green,
                                modifier = modifier.padding(8.dp))
                        }
                    }

                }

                Button(onClick = {
                    // Case 0 - 0
                    if (scoreTeam1 == 0 && scoreTeam2 == 0){
                        if (listPlayerScore.size>0){
                            numberPlayerScoreError = true
                            Toast.makeText(
                                context,
                                "Số lượng bàn thắng và số lượng cầu thủ ghi bàn mỗi đội không phù hợp",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else numberPlayerScoreError = false
                        if (!numberPlayerScoreError){
                            addResultMatchViewModel.updateTeam(
                                team1!!.copy(
                                    tie = team1!!.tie + 1,
                                    totalGoal = team1!!.totalGoal + scoreTeam1
                                )
                            )
                            addResultMatchViewModel.updateTeam(
                                team2!!.copy(
                                    tie = team2!!.tie + 1,
                                    totalGoal = team2!!.totalGoal + scoreTeam2
                                )
                            )
                            addResultMatchViewModel.updateMatch(
                                match!!.copy(
                                    resultTeam1 = scoreTeam1,
                                    resultTeam2 = scoreTeam2,
                                    status = Constant.STATUS_DONE
                                )
                            )

                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                            navController.navigate(MainScreen.HomeScreen.route)
                        }
                    } else {
                        // Check rule
                        if (indexPlayer == null) namePlayerError = true
                        if (minuteScore > maxMinutes) minuteScoreError = true
                        var countTeam1 = 0
                        var countTeam2 = 0
                        listPlayerScore.forEach { pair ->
                            if (pair.first.idTeam == team1!!.idTeam) countTeam1++ else countTeam2++
                        }
                        if (indexTeam == 0) countTeam1++ else countTeam2++
                        if (countTeam1 != scoreTeam1 || countTeam2 != scoreTeam2) {
                            numberPlayerScoreError = true
                            Toast.makeText(
                                context,
                                "Số lượng bàn thắng và số lượng cầu thủ ghi bàn mỗi đội không phù hợp",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else numberPlayerScoreError = false

                        // Thỏa mãn rule
                        if (!namePlayerError && !numberPlayerScoreError && !minuteScoreError) {
                            listPlayerScore.forEach { pair ->
                                addResultMatchViewModel.addScore(pair.second)
                            }
                            val player =
                                if (indexTeam == 0) listPlayersTeam1[indexPlayer!!] else listPlayersTeam2[indexPlayer!!]
                            addResultMatchViewModel.addScore(
                                Score(
                                    idMatch = match!!.idMatch,
                                    idPlayer = player.idPlayer,
                                    time = minuteScore,
                                    typeScore = typeScore
                                )
                            )
                            val list = mutableListOf<Player>()
                            listPlayerScore.forEach { pair ->
                                list.add(pair.first)
                            }
                            list.add(player)
                            coroutineScope.launch {
                                Log.e("Tpoo", "size: " + list.size)
                                list.forEach { player ->
                                    val deferredPlayer =
                                        async { addResultMatchViewModel.getPlayerById(player.idPlayer.toString()) }
                                    val pl = deferredPlayer.await()
                                    addResultMatchViewModel.updatePlayer(player = pl.copy(totalGoal = pl.totalGoal + 1))
                                }

                            }


                            // Update team??
                            if (scoreTeam1 == scoreTeam2) {
                                addResultMatchViewModel.updateTeam(
                                    team1!!.copy(
                                        tie = team1!!.tie + 1,
                                        totalGoal = team1!!.totalGoal + scoreTeam1
                                    )
                                )
                                addResultMatchViewModel.updateTeam(
                                    team2!!.copy(
                                        tie = team2!!.tie + 1,
                                        totalGoal = team2!!.totalGoal + scoreTeam2
                                    )
                                )
                            } else if (scoreTeam1 > scoreTeam2) {
                                addResultMatchViewModel.updateTeam(
                                    team1!!.copy(
                                        win = team1!!.win + 1,
                                        totalGoal = team1!!.totalGoal + scoreTeam1,
                                        numberDiff = team1!!.numberDiff + scoreTeam1 - scoreTeam2
                                    )
                                )
                                addResultMatchViewModel.updateTeam(
                                    team2!!.copy(
                                        lose = team2!!.lose + 1,
                                        totalGoal = team2!!.totalGoal + scoreTeam2,
                                        numberDiff = team2!!.numberDiff + scoreTeam2 - scoreTeam1
                                    )
                                )
                            } else {
                                addResultMatchViewModel.updateTeam(
                                    team1!!.copy(
                                        lose = team1!!.lose + 1,
                                        totalGoal = team1!!.totalGoal + scoreTeam1,
                                        numberDiff = team1!!.numberDiff + scoreTeam1 - scoreTeam2
                                    )
                                )
                                addResultMatchViewModel.updateTeam(
                                    team2!!.copy(
                                        win = team2!!.win + 1,
                                        totalGoal = team2!!.totalGoal + scoreTeam2,
                                        numberDiff = team2!!.numberDiff + scoreTeam2 - scoreTeam1
                                    )
                                )
                            }

                            addResultMatchViewModel.updateMatch(
                                match!!.copy(
                                    resultTeam1 = scoreTeam1,
                                    resultTeam2 = scoreTeam2,
                                    status = Constant.STATUS_DONE
                                )
                            )

                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                            navController.navigate(MainScreen.HomeScreen.route)

                        }
                    }

                }, shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(Green),
                    modifier = modifier
                        .fillMaxWidth(0.95f)
                        .padding(8.dp)) {
                    Text(text = "ADD RESULT",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = modifier.padding(8.dp))
                }

            }
        }
    }

}