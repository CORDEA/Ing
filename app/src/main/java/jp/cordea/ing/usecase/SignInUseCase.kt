package jp.cordea.ing.usecase

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val client: GoogleSignInClient
) {
    fun execute() = SignInRequest(client)
}

class SignInRequest(private val client: GoogleSignInClient) {
    fun launchWith(launcher: ActivityResultLauncher<Intent>) =
        launcher.launch(client.signInIntent)
}
