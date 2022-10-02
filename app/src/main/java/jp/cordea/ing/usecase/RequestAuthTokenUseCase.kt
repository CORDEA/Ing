package jp.cordea.ing.usecase

import android.accounts.Account
import com.google.android.gms.common.api.Scope
import dagger.Reusable
import jp.cordea.ing.repository.TokenRepository
import jp.cordea.ing.repository.TokenRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class RequestAuthTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend fun execute(account: Account, scopes: Set<Scope>) =
        withContext(Dispatchers.Default) {
            repository.find(TokenRequest.Remote(account, scopes))
        }
}
