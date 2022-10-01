package jp.cordea.ing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private const val TAG_SIGN_IN = "sign_in"
private const val TAG_HOME = "home"

@Composable
fun NavGraph() {
    NavHost(navController = rememberNavController(), startDestination = TAG_SIGN_IN) {
        composable(route = TAG_SIGN_IN) {
            SignIn(hiltViewModel())
        }
        composable(route = TAG_HOME) {
            Home(hiltViewModel())
        }
    }
}
