package com.example.managefootball.screen.addmatch

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.managefootball.model.Match
import com.example.managefootball.model.Player
import com.example.managefootball.model.Team
import com.example.managefootball.nav.MainScreen
import com.example.managefootball.nav.NavScreen
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.GreenBackground
import com.example.managefootball.util.AddMatchWorker
import com.example.managefootball.util.BottomBar
import com.example.managefootball.util.Constant
import com.example.managefootball.util.InputField
import com.example.managefootball.util.InputFieldWithTrailingIcon
import com.example.managefootball.util.TopBar

import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit


// Chưa check input, điều kiện

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMatchScreen(modifier: Modifier = Modifier, navController: NavController, addMatchViewModel: AddMatchViewModel = hiltViewModel()){
    val context = LocalContext.current

    val listTeams by addMatchViewModel.listTeams.collectAsState()

    var expandedDropdownRound by remember { mutableStateOf(false) }
    val listRound = (1..(listTeams.size-1)*2).map { "Vòng $it" }
    var round by remember { mutableStateOf("Vòng 1") }
    var yard by remember { mutableStateOf("---") }

    var expandedDropdownTeam1 by remember { mutableStateOf(false) }
    var expandedDropdownTeam2 by remember { mutableStateOf(false) }
    var team1 by remember { mutableStateOf<Int?>(null) }
    var team2 by remember { mutableStateOf<Int?>(null) }

    var picketDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()
    var formattedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(picketDate)
    var picketTime by remember { mutableStateOf(LocalTime.now()) }
    val timeDialogState = rememberMaterialDialogState()
    var formattedTime = DateTimeFormatter.ofPattern("hh:mm").format(picketTime)

    val idMatch: UUID = UUID.randomUUID()

    val listTeamInRound by addMatchViewModel.listTeamInRound.collectAsState()
    val listMatchInRound by addMatchViewModel.listMatchInRound.collectAsState()
    val listTeamsSatisfied = listTeams.filterNot { team-> listTeamInRound.contains(team.idTeam.toString()) }
    val listAllMatches by addMatchViewModel.listAllMatches.collectAsState()
    val listPairIdTeam = listAllMatches.map { match -> Pair(match.idTeam1.toString(),match.idTeam2.toString()) }

    var nameTeam1Error by remember { mutableStateOf(false) }
    var nameTeam2Error by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true){
        addMatchViewModel.getMatchInRound(round)
    }
    
    LaunchedEffect(key1 = listMatchInRound){
        addMatchViewModel.getTeamInRound()
    }


    Scaffold(
        topBar = { TopBar(title = "Tạo trận đấu"){
            navController.popBackStack()
        } }
    ) { paddingValue ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .weight(0.85f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = modifier.height(3.dp))

                InputFieldWithTrailingIcon(value = round, onValueChange = {
                    //
                }, labelId = "Vòng đấu", modifier = modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { expandedDropdownRound = true }) {
                            Icon(imageVector = if (expandedDropdownRound) Icons.Default.KeyboardArrowUp
                            else Icons.Default.KeyboardArrowDown,
                                contentDescription = "expand")
                        }
                    },
                    readOnly = true
                )
                DropdownMenu(
                    expanded = expandedDropdownRound,
                    onDismissRequest = { expandedDropdownRound = false },
                    modifier = modifier.fillMaxWidth(0.4f)
                ) {
                    listRound.forEach { r ->
                        DropdownMenuItem(text = {
                            Text(text = r)
                        }
                            ,onClick = {
                                round = r
                                addMatchViewModel.getMatchInRound(round)
                                team1 = null
                                team2 = null
                                expandedDropdownRound = false
                            })
                    }
                }

                Row(modifier = modifier.fillMaxWidth()) {
                    Column(modifier = modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                        horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Đội 1",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = modifier.padding(8.dp)
                        )

                        InputFieldWithTrailingIcon(value = team1?.let { listTeamsSatisfied[it].nameTeam } ?: "", onValueChange = {
                            //
                        }, labelId = "Đội 1", trailingIcon = {
                            IconButton(onClick = { expandedDropdownTeam1 = true }) {
                                Icon(imageVector = if (expandedDropdownTeam1) Icons.Default.KeyboardArrowUp
                                else Icons.Default.KeyboardArrowDown,
                                    contentDescription = "expand")
                            }
                        },
                            isError = nameTeam1Error,
                            errorValue = "Vui lòng chọn đội phù hợp")

                        DropdownMenu(
                            expanded = expandedDropdownTeam1,
                            onDismissRequest = { expandedDropdownTeam1 = false },
                            modifier = modifier.fillMaxWidth(0.5f)
                        ) {
                            listTeamsSatisfied.forEachIndexed { index, team ->
                                DropdownMenuItem(text = {
                                    Text(text = team.nameTeam)
                                }
                                    ,onClick = {
                                        yard = team.yardTeam
                                        team1 = index
                                        expandedDropdownTeam1 = false
                                        nameTeam1Error = false
                                    })
                            }
                        }
                    }

                    Column(modifier = modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                        horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Đội 2",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = modifier.padding(8.dp)
                        )

                        InputFieldWithTrailingIcon(value = team2?.let { listTeamsSatisfied[it].nameTeam }?: "", onValueChange = {

                        }, labelId = "Đội 2", trailingIcon = {
                            IconButton(onClick = { expandedDropdownTeam2 = true }) {
                                Icon(imageVector = if (expandedDropdownTeam2) Icons.Default.KeyboardArrowUp
                                else Icons.Default.KeyboardArrowDown,
                                    contentDescription = "expand")
                            }
                        }, isError = nameTeam2Error,
                            errorValue = "Vui lòng chọn đội phù hợp"
                        )

                        DropdownMenu(
                            expanded = expandedDropdownTeam2,
                            onDismissRequest = { expandedDropdownTeam2 = false },
                            modifier = modifier.fillMaxWidth(0.5f)
                        ) {
                            listTeamsSatisfied.forEachIndexed { index, team ->
                                DropdownMenuItem(text = {
                                    Text(text = team.nameTeam)
                                }
                                    ,onClick = {
                                        team2 = index
                                        nameTeam2Error = false
                                        expandedDropdownTeam2 = false
                                    })
                            }
                        }
                    }
                }

                InputField(value = yard, onValueChange = {
                          //
                }, labelId = "Sân", readOnly = true)

                Row(modifier = modifier.fillMaxWidth()) {
                    InputFieldWithTrailingIcon(value = formattedDate, onValueChange = {
                        //
                    }, labelId = "Date", modifier = modifier.weight(0.5f),
                        trailingIcon = {
                            IconButton(onClick = {
                                dateDialogState.show()
                            }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription ="date_pick" )
                            }
                        }
                    )

                    InputFieldWithTrailingIcon(value = formattedTime, onValueChange = {
                        //
                    }, labelId = "Time", modifier = modifier.weight(0.5f),
                        trailingIcon = {
                            IconButton(onClick = {
                                timeDialogState.show()
                            }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription ="time_pick" )
                            }
                        }
                    )
                }

                Text(
                    text = "- Trong 1 vòng mỗi đội tham gia đúng 1 trận, đội 1 là đội đá trên sân nhà",
                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    color = ErrorColor
                )
                Text(
                    text = "- Trong cả giải mỗi đội tham gia đúng 2 lần với đội khác (một lần sân nhà, một lần sân khách)",
                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    color = ErrorColor
                )

            }

            Button(onClick = {
                if (team1 == null) nameTeam1Error = true
                if (team2 == null) nameTeam2Error = true
                if (team1 == team2) {
                    nameTeam1Error = true
                    nameTeam2Error = true
                }
                if (!nameTeam1Error && !nameTeam2Error) {
                    val pairCompare = Pair(
                        listTeamsSatisfied[team1!!].idTeam.toString(),
                        listTeamsSatisfied[team2!!].idTeam.toString()
                    )
                    val isFound = listPairIdTeam.any { it == pairCompare }

                    if (isFound) {
                        Toast.makeText(
                            context,
                            "Không thể tạo trận đấu, đã gặp nhau",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(context, "Tạo trận đấu thành công", Toast.LENGTH_SHORT)
                            .show()
                        addMatchViewModel.addMatch(
                            Match(
                                idMatch = idMatch,
                                round = round, idTeam1 = listTeamsSatisfied[team1!!].idTeam,
                                idTeam2 = listTeamsSatisfied[team2!!].idTeam, day = formattedDate,
                                time = formattedTime
                            )
                        )
                        team1 = null
                        team2 = null
                        navController.navigate(MainScreen.HomeScreen.route)
                        //Log.e("Tpoo: ","idTeam1($team1) : " +  listTeamsSatisfied[team1!!].idTeam + "- idTeam2($team2): " + listTeamsSatisfied[team2!!].idTeam)
//                    val worker = OneTimeWorkRequestBuilder<AddMatchWorker>()
//                    val data = Data.Builder()
//                    val startDate =
//                        LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toEpochSecond()
//                    val endDate = convertStringToDate(formattedDate).time / 1000
//
//
//                    var time = endDate - startDate
//                    if (time < 0) time = 0
//
//                    Log.e("Tpoo", "time: " + time)
//
//                    data.putString("idMatch", idMatch.toString())
//                    data.putString("idTeam1", listTeams[team1!!].idTeam.toString())
//                    data.putString("idTeam2", listTeams[team2!!].idTeam.toString())
//                    worker.setInputData(data.build())
//                    WorkManager.getInstance(context).enqueue(
//                        worker.setInitialDelay(time, TimeUnit.SECONDS)
//                            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofSeconds(15))
//                            .build()
//                    )
                    }
                }

            }, shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(GreenBackground),
                modifier = modifier
                    .fillMaxWidth(0.95f)
                    .padding(8.dp)) {
                Text(text = "Thêm trận đấu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = modifier.padding(8.dp))
            }
        }
        MaterialDialog (
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Chọn")
                negativeButton(text = "Đóng")
            },
            backgroundColor = GrayBackground,
            shape = RoundedCornerShape(15.dp)
        ){
            datepicker(
                initialDate = LocalDate.now(),
                title = "Chọn ngày thi đấu",
                colors = DatePickerDefaults.colors(Green)
            ){
                picketDate = it
            }
        }
        MaterialDialog (
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Chọn")
                negativeButton(text = "Đóng")
            },
            backgroundColor = GrayBackground,
            shape = RoundedCornerShape(15.dp)
        ){
            timepicker(
                initialTime = LocalTime.now(),
                title = "Chọn giờ thi đấu",
                colors = TimePickerDefaults.colors(Green)
            ){
                picketTime = it
            }
        }
    }
}