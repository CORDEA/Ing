package jp.cordea.ing.usecase

import android.accounts.Account
import android.content.Context
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.common.api.Scope
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class RequestAuthTokenUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun execute(account: Account, scopes: Set<Scope>) = withContext(Dispatchers.Default) {
        GoogleAuthUtil.getToken(
            context,
            account,
            "oauth2:${scopes.joinToString(" ") { it.scopeUri }}"
        )
    }
}
