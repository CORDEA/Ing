package jp.cordea.ing.usecase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Reusable
class HasSessionUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute() = GoogleSignIn.getLastSignedInAccount(context)?.isExpired == false
}
