package jp.cordea.ing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Home(viewModel: HomeViewModel, navController: NavController) {
    val event by viewModel.event.collectAsState(initial = null)
    val uriHandler = LocalUriHandler.current
    LaunchedEffect(event) {
        when (val e = event) {
            HomeEvent.Back -> {
                navController.popBackStack()
            }

            is HomeEvent.OpenLink -> {
                uriHandler.openUri(e.link)
            }

            null -> {}
        }
    }
    val loadingState by viewModel.loadingState.collectAsState()
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Ing") },
                actions = {
                    when (loadingState) {
                        LoadingState.LOADED -> {
                            IconButton(onClick = {
                                viewModel.onRefreshClicked()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Refresh"
                                )
                            }
                        }

                        LoadingState.LOADING -> {}
                        LoadingState.FAILED -> {}
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
        when (loadingState) {
            LoadingState.LOADING -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LoadingState.LOADED -> Body(viewModel, padding)
            LoadingState.FAILED -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                ElevatedButton(
                    onClick = { viewModel.onReloadClicked() },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "Reload")
                }
            }
        }
    }
}

@Composable
private fun Body(viewModel: HomeViewModel, padding: PaddingValues) {
    val items by viewModel.items.collectAsState()
    LazyColumn(contentPadding = padding) {
        items.map {
            item {
                Item(it)
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
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = viewModel.title
        )
        IconButton(
            onClick = {
                viewModel.onIconClick()
            }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.OpenInNew,
                contentDescription = "Open on the web"
            )
        }
    }
}
