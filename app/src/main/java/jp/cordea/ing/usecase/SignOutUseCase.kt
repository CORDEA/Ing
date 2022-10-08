package jp.cordea.ing.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val client: GoogleSignInClient
) {
    fun execute() {
        client.signOut().getResult(ApiException::class.java)
    }
}
