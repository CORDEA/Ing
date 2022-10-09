package jp.cordea.ing

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.cordea.ing.usecase.HasSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    hasSessionUseCase: HasSessionUseCase
) : ViewModel() {
    private val _tag = MutableStateFlow<Tag?>(null)
    val tag get() = _tag.asStateFlow()

    init {
        _tag.value = if (hasSessionUseCase.execute()) Tag.HOME else Tag.SIGN_IN
    }
}
