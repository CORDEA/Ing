package jp.cordea.ing.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.Reusable
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Reusable
class SignOutUseCase @Inject constructor(
    private val client: GoogleSignInClient
) {
    suspend fun execute() = suspendCoroutine<Unit> { continuation ->
        client.signOut()
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}
