package com.example.managefootball.nav

sealed class NavScreen (val route: String) {
    object SplashScreen: NavScreen("splash")
    object SearchScreen: NavScreen("search")
    object RegisterTeamScreen: NavScreen("register_team")
    object AddMatchScreen: NavScreen("add_match")
    object MatchDetailScreen: NavScreen("match_detail")
    object AddResultMatchScreen: NavScreen("add_result_match_screen")
}