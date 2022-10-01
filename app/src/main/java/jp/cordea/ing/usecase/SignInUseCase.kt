package jp.cordea.ing.usecase

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val options =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    fun execute() = SignInRequest(GoogleSignIn.getClient(context, options))
}

class SignInRequest(private val client: GoogleSignInClient) {
    fun launchWith(launcher: ActivityResultLauncher<Intent>) =
        launcher.launch(client.signInIntent)
}
