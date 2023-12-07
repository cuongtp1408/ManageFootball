@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.managefootball.screen.players

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.managefootball.model.Player
import com.example.managefootball.model.Team
import com.example.managefootball.ui.theme.BlackBackground
import com.example.managefootball.ui.theme.BlackBar
import com.example.managefootball.ui.theme.BlueCard
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.GraySecondTextColor
import com.example.managefootball.ui.theme.GreenBackground
import com.example.managefootball.ui.theme.Yellow
import com.example.managefootball.util.BottomBar
import com.example.managefootball.util.EmptyContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreen(modifier : Modifier = Modifier, navController: NavController, playersViewModel: PlayersViewModel = hiltViewModel()){
    val listPlayers by playersViewModel.listPlayers.collectAsState()
    val listPlayersWithTeams by playersViewModel.listPlayersWithTeams.collectAsState()
    val isLoading = playersViewModel.isLoading

    LaunchedEffect(key1 = listPlayers){
        playersViewModel.getPlayersWithTeam()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController)}
    ) { paddingValues ->
//        if (isLoading){
//            CircularProgressIndicator()
//        } else {
            Column(
                modifier = modifier
                    .background(BlackBackground)
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
                    SearchBar { query ->
                        playersViewModel.searchPlayers(query)
                    }

                    Spacer(modifier = modifier.height(10.dp))

                    Text(text = "Danh sách cầu thủ", fontWeight = FontWeight.SemiBold, fontSize = 30.sp, color = Color.White,
                        modifier = modifier.padding(3.dp))


                }


                Card(modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    , shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BlackBar
                    )) {
                    Row(
                        modifier = modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "STT",
                            fontSize = 17.sp,
                            modifier = modifier
                                .weight(0.1f)
                                .padding(horizontal = 3.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Cầu thủ",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.3f),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Đội",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.3f),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Loại",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.2f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Bàn thắng",
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.2f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                if (listPlayersWithTeams.isEmpty()) {
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
                    LazyColumn(modifier = modifier
                        .fillMaxSize()
                        .weight(0.85f),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(listPlayersWithTeams.size) { index ->
                            PlayerCard( stt = index + 1,player = listPlayersWithTeams[index])
                        }
                    }
                }

            }
        }

    //}
}

@Composable
fun PlayerCard(modifier: Modifier = Modifier,stt: Int, player: Pair<Player, Team>) {
    Card(modifier = modifier
        .fillMaxWidth()
        .height(50.dp)
        , shape = RoundedCornerShape(0.dp),

        colors = CardDefaults.cardColors(
            containerColor = if (stt%2==0) BlackBar else GreenBackground
        )) {
        Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$stt",
                fontSize = 17.sp,
                modifier = modifier
                    .weight(0.1f)
                    .padding(horizontal = 3.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.W600
            )
            Text(
                text = player.first.namePlayer,
                fontSize = 17.sp,
                modifier = modifier.weight(0.3f),
                textAlign = TextAlign.Left,
                color = Color.White,
                fontWeight = FontWeight.W600
            )
            Text(
                text = player.second.nameTeam,
                fontSize = 17.sp,
                modifier = modifier.weight(0.3f),
                textAlign = TextAlign.Left,
                color = Color.White,
                fontWeight = FontWeight.W600
            )
            Text(
                text = player.first.typePlayer,
                fontSize = 17.sp,
                modifier = modifier.weight(0.2f),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "${player.first.totalGoal}",
                fontSize = 17.sp,
                modifier = modifier.weight(0.2f),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.W600
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit) {
    val query = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = query.value, onValueChange = {
        query.value = it
        onSearch(query.value)
    },
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(24.dp))
        ,
        placeholder = {
            Text(
                text = "Search",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = GraySecondTextColor
            )
        }, trailingIcon = {
            IconButton(onClick = { onSearch(query.value) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search",
                    tint = BlackBar)
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.Black,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            onSearch(query.value)
            keyboardController?.hide()
        },
    )

}