package jp.cordea.ing.usecase

import dagger.Reusable
import jp.cordea.ing.repository.TokenRepository
import javax.inject.Inject

@Reusable
class StoreAuthTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    fun execute(token: String) = repository.insert(token)
}
