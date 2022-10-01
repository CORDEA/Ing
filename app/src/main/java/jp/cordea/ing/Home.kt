package jp.cordea.ing

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Home(viewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Ing") }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item { Item() }
        }
    }
}

@Composable
private fun Item() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically),
            text = ""
        )
    }
}
