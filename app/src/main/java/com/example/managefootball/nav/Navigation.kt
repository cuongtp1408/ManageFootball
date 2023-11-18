package com.example.managefootball.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.managefootball.screen.addmatch.AddMatchScreen
import com.example.managefootball.screen.home.HomeScreen
import com.example.managefootball.screen.match.AllMatchesScreen
import com.example.managefootball.screen.matchdetail.MatchDetailScreen
import com.example.managefootball.screen.players.PlayersScreen
import com.example.managefootball.screen.registerteam.RegisterTeamScreen
import com.example.managefootball.screen.resultmatch.AddResultMatchScreen
import com.example.managefootball.screen.setting.SettingScreen

@Composable
fun MainNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreen.HomeScreen.route){
        composable(route = MainScreen.HomeScreen.route){
            HomeScreen(navController = navController)
        }
        
        composable(route = NavScreen.RegisterTeamScreen.route){
            RegisterTeamScreen(navController = navController)
        }
        
        composable(route = MainScreen.MatchScreen.route){
            AllMatchesScreen(navController = navController)
        }
        
        composable(route = NavScreen.AddMatchScreen.route){
            AddMatchScreen(navController = navController)
        }

        composable(route = "${NavScreen.MatchDetailScreen.route}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )){ navBackStackEntry ->
                navBackStackEntry.arguments?.getString("id").let {

                    MatchDetailScreen(navController = navController, matchId = it.toString())
                }
        }

        composable(route = "${NavScreen.AddResultMatchScreen.route}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
        ){navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id").let {
                AddResultMatchScreen(navController = navController, matchId = it.toString())
            }
        }

        composable(route = MainScreen.PlayerScreen.route){
            PlayersScreen(navController = navController)
        }

        composable(route = MainScreen.SettingScreen.route){
            SettingScreen(navController = navController)
        }
    }
}