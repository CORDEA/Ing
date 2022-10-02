package jp.cordea.ing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private const val TAG_SIGN_IN = "sign_in"
const val TAG_HOME = "home"

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TAG_SIGN_IN) {
        composable(route = TAG_SIGN_IN) {
            SignIn(hiltViewModel(), navController)
        }
        composable(route = TAG_HOME) {
            Home(hiltViewModel())
        }
    }
}
