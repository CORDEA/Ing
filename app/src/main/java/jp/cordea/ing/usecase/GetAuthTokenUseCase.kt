package jp.cordea.ing.usecase

import dagger.Reusable
import jp.cordea.ing.repository.TokenRepository
import jp.cordea.ing.repository.TokenRequest
import javax.inject.Inject

@Reusable
class GetAuthTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    fun execute() = repository.find(TokenRequest.Local)
}
