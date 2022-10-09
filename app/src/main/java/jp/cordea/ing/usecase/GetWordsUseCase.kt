package jp.cordea.ing.usecase

import dagger.Reusable
import jp.cordea.ing.repository.WordRepository
import javax.inject.Inject

@Reusable
class GetWordsUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend fun execute() = repository.findAll()
}
