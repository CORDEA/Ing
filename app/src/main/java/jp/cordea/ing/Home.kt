package jp.cordea.ing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Home(viewModel: HomeViewModel, navController: NavController) {
    val event by viewModel.event.collectAsState(initial = null)
    LaunchedEffect(event) {
        when (event) {
            HomeEvent.Back -> {
                navController.popBackStack()
            }
            null -> {}
        }
    }
    val items by viewModel.items.collectAsState()
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Ing") },
                actions = {
                    IconButton(onClick = {
                        viewModel.onRefreshClicked()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onSignOutClicked()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sign out"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items.map {
                item {
                    Item(it)
                }
            }
        }
    }
}

@Composable
private fun Item(viewModel: HomeItemViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)
            .clickable { viewModel.onClick() }
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically),
            text = viewModel.title
        )
    }
}
