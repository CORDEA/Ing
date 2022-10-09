package jp.cordea.ing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.cordea.ing.usecase.GetWordsUseCase
import jp.cordea.ing.usecase.SignOutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _items = MutableStateFlow<List<HomeItemViewModel>>(emptyList())
    val items get() = _items.asStateFlow()

    private lateinit var words: List<Word>

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            runCatching {
                val response = getWordsUseCase.execute()
                words = response
                _items.value = toItems(response)
            }.onFailure {
                // TODO
                it.printStackTrace()
            }
        }
    }

    private fun onItemClicked(id: Int) {
        val word = words.first { it.id == id }
        _items.value = _items.value.map {
            if (id == it.id) {
                it.copy(title = word.answer)
            } else {
                it
            }
        }
    }

    fun onRefreshClicked() {
        _items.value = toItems(words)
    }

    fun onSignOutClicked() {
        viewModelScope.launch {
            signOutUseCase.execute()
            _event.emit(HomeEvent.Back)
        }
    }

    private fun toItems(words: List<Word>) =
        words.map {
            HomeItemViewModel(it.id, it.question) {
                onItemClicked(it.id)
            }
        }
}

data class HomeItemViewModel(
    val id: Int,
    val title: String,
    val onClick: () -> Unit
)

sealed class HomeEvent {
    object Back : HomeEvent()
}
