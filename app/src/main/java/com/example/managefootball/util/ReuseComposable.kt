@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.managefootball.util


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.managefootball.nav.MainScreen
import com.example.managefootball.ui.theme.Black
import com.example.managefootball.ui.theme.ErrorColor
import com.example.managefootball.ui.theme.GrayBackground
import com.example.managefootball.ui.theme.GraySecondTextColor
import com.example.managefootball.ui.theme.Green
import com.example.managefootball.ui.theme.GreenBackground

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val navigationItems = listOf(
        MainScreen.HomeScreen,
        MainScreen.MatchScreen,
        MainScreen.PlayerScreen,
        MainScreen.SettingScreen
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomBarDestination = navigationItems.any { it.route == currentRoute }

    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Black,
            modifier = modifier
        ) {
            navigationItems.forEach { item ->
                val selectedNavItem = currentRoute?.contains(item.route) == true
                BottomNavigationItem(
                    icon = {
                      Icon(imageVector = item.icon, contentDescription = item.title, tint = if (currentRoute == item.route) {
                          GreenBackground
                      } else Color.Black.copy(0.4f))
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.SemiBold,
                            color = if (currentRoute == item.route) {
                                GreenBackground
                            } else Color.Black.copy(0.4f)
                        )
                    },
                    selectedContentColor = Green,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    selected = selectedNavItem,
                    onClick = {
                        if (!selectedNavItem) {

                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar( modifier: Modifier = Modifier,title: String,  onBackClick: () -> Unit = {}){
    TopAppBar(
        title = {
            Text(text = title, fontSize = 26.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold,
            color = Color.Black
            ) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Black,
            navigationIconContentColor = Black),
        modifier = modifier.padding(5.dp),
        navigationIcon = {

                IconButton(onClick = {onBackClick()}) {

                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back",
                        modifier = modifier.size(30.dp))

                }

        }
    )
}


@Composable
fun InputFieldWithTrailingIcon(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable() (() -> Unit)? ={},
    readOnly: Boolean = true,
    isError: Boolean = false,
    errorValue: String = "",
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    Column(
        modifier =  modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,

            onValueChange = { vl ->
                onValueChange.invoke(vl)
            },
            singleLine = isSingleLine,
            maxLines = 1,
            trailingIcon = trailingIcon,
            readOnly = readOnly,
            placeholder = {
                Text(
                    text = labelId,
                    color = GraySecondTextColor
                )
            },
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            modifier = Modifier
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Black,
                unfocusedBorderColor = if (isError) ErrorColor else GraySecondTextColor.copy(0.3f)
            )
        )
        if (isError) {
            Text(
                text = errorValue,
                modifier = Modifier.padding(bottom = 5.dp, start = 10.dp, end = 10.dp),
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                color = ErrorColor
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorValue: String = "",
    labelId: String,
    textAlign: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    Column(
        modifier =  modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { vl ->
                onValueChange.invoke(vl)
            },
            singleLine = isSingleLine,
            maxLines = 1,
            readOnly = readOnly,
            placeholder = {
                Text(
                    text = labelId,
                    color = GraySecondTextColor
                )
            },
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground,
                textAlign = textAlign),
            modifier = Modifier
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Black,
                unfocusedBorderColor = if (isError) ErrorColor else GraySecondTextColor.copy(0.3f)
            )
        )
        if (isError) {
            Text(
                text = errorValue,
                modifier = Modifier.padding(bottom = 5.dp, start = 10.dp, end = 10.dp),
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                color = ErrorColor
            )
        }
    }

}

@Composable
fun EmptyContent(
    modifier: Modifier
) {
    Column(
        modifier = modifier,
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
}
