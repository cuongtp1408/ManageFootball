package com.example.managefootball.screen.registerteam

import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.model.Player
import com.example.managefootball.model.Team
import com.example.managefootball.nav.MainScreen
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.GraySecondTextColor
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.GreenBackground
import com.example.managefootball.ui.theme.Yellow
import com.example.managefootball.util.Constant.TYPE_DOMESTIC
import com.example.managefootball.util.Constant.TYPE_FOREIGN
import com.example.managefootball.util.InputField
import com.example.managefootball.util.InputFieldWithTrailingIcon
import com.example.managefootball.util.TopBar
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.UUID
// Chưa check input, điều kiện
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTeamScreen(modifier: Modifier = Modifier, navController: NavController,
                       registerTeamViewModel: RegisterTeamViewModel = hiltViewModel() ){
    val idTeam = registerTeamViewModel.idTeam
    val context = LocalContext.current

    var nameTeam by rememberSaveable { mutableStateOf("")}
    var yardTeam by rememberSaveable { mutableStateOf("")}

    val players = remember { mutableStateListOf<Player>() }
    var namePlayer by remember { mutableStateOf("") }
    var typePlayer by remember { mutableStateOf(TYPE_DOMESTIC) }
    var notePlayer by remember { mutableStateOf("") }

    var expandedDropdown by remember { mutableStateOf(false) }
    val types = listOf(TYPE_DOMESTIC, TYPE_FOREIGN)

    var picketDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()
    var formattedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(picketDate)

    val minYears by registerTeamViewModel.minYears.collectAsState()
    val maxYears by registerTeamViewModel.maxYears.collectAsState()
    val minPlayers by registerTeamViewModel.minPlayers.collectAsState()
    val maxPlayers by registerTeamViewModel.maxPlayers.collectAsState()
    val maxForeignPlayers by registerTeamViewModel.maxForeignPlayers.collectAsState()

    var nameTeamError by remember{ mutableStateOf(false)}
    var yardTeamError by remember{ mutableStateOf(false)}
    var nameError by remember{ mutableStateOf(false)}
    var yearsError by remember{ mutableStateOf(false)}
    var numberPlayersError by remember{ mutableStateOf(false)}
    var numberForeignPlayersError by remember{ mutableStateOf(false)}

    Scaffold(
        topBar = { TopBar(title = "Đăng ký đội"){
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
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = modifier.height(3.dp))
                InputField(modifier = modifier.fillMaxWidth(),value = nameTeam,
                    onValueChange = {
                        nameTeam = it
                        nameTeamError = false},
                    labelId = "Tên đội bóng",
                    isError = nameTeamError,
                    errorValue = "Tên đội không được để trống")
                Spacer(modifier = modifier.height(3.dp))
                InputField(modifier = modifier.fillMaxWidth(),value = yardTeam,
                    onValueChange = {yardTeam = it
                        yardTeamError = false} ,labelId = "Sân nhà",
                    isError = yardTeamError,
                    errorValue = "Sân đội không được để trống")

                Text(
                    text = "- Số lượng cầu thủ của mỗi đội phải từ $minPlayers đến $maxPlayers",
                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    color = ErrorColor
                )
                Text(
                    text = "- Mỗi đội có tối đa $maxForeignPlayers cầu thủ nước ngoài",
                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    color = ErrorColor
                )

                players.forEachIndexed { index, player ->
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
                                InputField(value = player.namePlayer, onValueChange = {
                                    players[index] = player.copy(namePlayer = it)
                                }, labelId = "Tên", modifier = modifier.weight(0.7f), readOnly = true)

                                InputField(value = player.dateOfBirth, onValueChange = {
                                     players[index] = player.copy(dateOfBirth = it)
                                }, labelId = "Ngày sinh", modifier = modifier.weight(0.4f), readOnly = true)
                            }
                            Row(modifier = modifier.fillMaxWidth()) {
                                InputField(value = player.typePlayer, onValueChange = {
                                    players[index] = player.copy(typePlayer = it)
                                }, labelId = "Loại", modifier = modifier.weight(0.4f), readOnly = true
                                )

                                InputField(value = player.notePlayer, onValueChange = {
                                    players[index] = player.copy(notePlayer = it)
                                }, labelId = "Note", modifier = modifier.weight(0.7f), readOnly = true)
                            }
                        }

                        IconButton(
                            onClick = { players.removeAt(index) },
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
                    Text(text = "Cầu thủ ${players.size+1}:", fontSize = 15.sp, fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold, color = GraySecondTextColor,
                        modifier = modifier
                            .align(Alignment.Start)
                            .padding(8.dp)
                    )
                    Row(modifier = modifier.fillMaxWidth()) {
                        InputField(value = namePlayer, onValueChange = {
                            namePlayer = it
                            nameError = false
                        }, labelId = "Tên", modifier = modifier.weight(0.7f),
                            isError = nameError,
                            errorValue = "Tên không được để trống")

                        InputFieldWithTrailingIcon(value = formattedDate, onValueChange = {
                            //
                        }, labelId = "Ngày sinh", modifier = modifier.weight(0.4f),
                            trailingIcon = {
                                IconButton(onClick = {
                                    dateDialogState.show()
                                }) {
                                    Icon(imageVector = Icons.Default.DateRange, contentDescription ="date_pick" )
                                }
                            },
                            isError = yearsError,
                            errorValue = "$minYears <= Tuổi <= $maxYears"
                        )
                    }
                    Row(modifier = modifier.fillMaxWidth()) {
                        Column(modifier = modifier
                            .weight(0.4f)
                            .fillMaxWidth()) {
                            InputFieldWithTrailingIcon(value = typePlayer, onValueChange = {
                               //
                            }, labelId = "Loại", modifier = modifier.fillMaxWidth(),
                                trailingIcon = {
                                    IconButton(onClick = { expandedDropdown = true }) {
                                        Icon(imageVector = if (expandedDropdown) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                            contentDescription = "expand")
                                    }
                                },
                                readOnly = true
                            )
                            DropdownMenu(
                                expanded = expandedDropdown,
                                onDismissRequest = { expandedDropdown = false },
                                modifier = modifier.fillMaxWidth(0.4f)
                            ) {
                                types.forEach { type ->
                                    DropdownMenuItem(text = {
                                        Text(text = type)
                                    }
                                        ,onClick = {
                                            typePlayer = type
                                            expandedDropdown = false
                                        })
                                }
                            }
                        }


                        InputField(value = notePlayer, onValueChange = {
                            notePlayer = it
                        }, labelId = "Note", modifier = modifier.weight(0.7f))
                    }

                    Button(
                        onClick = {
                            if (namePlayer.isBlank()) nameError = true
                            if (formattedDate.isBlank() || Period.between(picketDate, LocalDate.now()).years<minYears
                                ||Period.between(picketDate, LocalDate.now()).years>maxYears) yearsError = true
                            if (!nameError && !yearsError) {
                                players.add(Player(namePlayer =  namePlayer, typePlayer = typePlayer,
                                    dateOfBirth = formattedDate, notePlayer = notePlayer, idTeam = idTeam))
                                namePlayer = ""
                                picketDate = LocalDate.now()
                                typePlayer = TYPE_DOMESTIC
                                notePlayer = ""
                            }
                        },
                        modifier = modifier
                            .fillMaxWidth(0.8f)
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(2.dp, GreenBackground)
                    ) {
                        Text("Thêm cầu thủ", fontSize = 15.sp, fontWeight = FontWeight.Normal, color = GreenBackground,
                            modifier = modifier.padding(8.dp))
                    }
                }
            }

            Button(onClick = {
                // Check rule
                if (nameTeam.isBlank()) nameTeamError = true
                if (yardTeam.isBlank()) yardTeamError = true
                if (namePlayer.isBlank()) nameError = true
                if (formattedDate.isBlank() || Period.between(picketDate, LocalDate.now()).years<minYears
                    ||Period.between(picketDate, LocalDate.now()).years>maxYears) yearsError = true
                if ((players.size + 1) > maxPlayers || (players.size+1) < minPlayers ){
                    numberPlayersError = true
                    Toast.makeText(context,"Số lượng cầu thủ không phù hợp quy định", Toast.LENGTH_SHORT).show()
                } else numberPlayersError = false
                var count = 0
                players.forEach{ player ->
                    if (player.typePlayer == TYPE_FOREIGN) count++
                }
                if (typePlayer == TYPE_FOREIGN) count++
                if (count>maxForeignPlayers) {
                    numberForeignPlayersError = true
                    Toast.makeText(context,"Số lượng cầu thủ nước ngoài vượt quá quy định", Toast.LENGTH_SHORT).show()
                }
                else numberForeignPlayersError = false

                // Thỏa mãn rule
                if (!nameTeamError && !yardTeamError && !nameError && !yearsError && !numberPlayersError && !numberForeignPlayersError) {
                    registerTeamViewModel.addTeam(
                        Team(
                            idTeam = idTeam,
                            nameTeam = nameTeam,
                            yardTeam = yardTeam
                        )
                    )
                    players.forEach { player ->
                        registerTeamViewModel.addPlayer(player)
                    }
                    registerTeamViewModel.addPlayer(
                        Player(
                            idTeam = idTeam,
                            namePlayer = namePlayer,
                            typePlayer = typePlayer,
                            dateOfBirth = formattedDate,
                            notePlayer = notePlayer
                        )
                    )
                    Toast.makeText(context, "Đăng kí đội thành công", Toast.LENGTH_SHORT).show()
                    navController.navigate(MainScreen.HomeScreen.route)
                }

            }, shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(GreenBackground),
                modifier = modifier
                    .fillMaxWidth(0.95f)
                    .padding(8.dp)) {
                Text(text = "Đăng ký đội",
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
                initialDate = picketDate,
                title = "Chọn ngày sinh",
                colors = DatePickerDefaults.colors(Green)
            ){
                yearsError = false
                picketDate = it
            }
        }
    }
}