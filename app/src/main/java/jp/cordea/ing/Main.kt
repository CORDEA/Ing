package jp.cordea.ing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.cordea.ing.ui.theme.IngTheme

enum class Tag(val value: String) {
    SIGN_IN("sign_in"),
    HOME("home")
}

@Composable
fun Main() {
    IngTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavGraph(hiltViewModel(), rememberNavController())
        }
    }
}

@Composable
private fun NavGraph(viewModel: MainViewModel, navController: NavHostController) {
    val tag by viewModel.tag.collectAsState()
    tag?.let {
        NavHost(navController = navController, startDestination = it.value) {
            composable(route = Tag.SIGN_IN.value) {
                SignIn(hiltViewModel(), navController)
            }
            composable(route = Tag.HOME.value) {
                Home(hiltViewModel(), navController)
            }
        }
    }
}
