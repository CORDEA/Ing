package jp.cordea.ing

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignIn(viewModel: SignInViewModel, navController: NavController) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            runCatching {
                GoogleSignIn
                    .getSignedInAccountFromIntent(result.data)
                    .getResult(ApiException::class.java)
            }
                .onSuccess(viewModel::onSignInSucceeded)
                .onFailure(viewModel::onSignInFailed)
        }
    )
    val event by viewModel.event.collectAsState(initial = null)
    LaunchedEffect(event) {
        when (val e = event) {
            is SignInEvent.StartSignIn -> {
                e.request.launchWith(launcher)
            }
            SignInEvent.ToHome -> {
                navController.navigate(Tag.HOME.value)
            }
            null -> {}
        }
    }
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AndroidView(
                modifier = Modifier.align(Alignment.Center),
                factory = { context ->
                    SignInButton(context).apply {
                        setSize(SignInButton.SIZE_WIDE)
                        setOnClickListener {
                            viewModel.onSignInClicked()
                        }
                    }
                }
            )
        }
    }
}
