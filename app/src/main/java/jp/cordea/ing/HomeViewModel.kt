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

    private val _loadingState = MutableStateFlow(LoadingState.LOADING)
    val loadingState get() = _loadingState.asStateFlow()

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
                _loadingState.value = LoadingState.LOADED
            }.onFailure {
                it.printStackTrace()
                _loadingState.value = LoadingState.FAILED
            }
        }
    }

    private fun onItemClicked(id: Int) {
        val word = words.first { it.id == id }
        _items.value = _items.value.map {
            if (id == it.id) {
                it.copy(
                    title = if (it.title == word.question) {
                        word.answer
                    } else {
                        word.question
                    }
                )
            } else {
                it
            }
        }
    }

    private fun onItemIconClicked(id: Int) {
        viewModelScope.launch {
            _event.emit(HomeEvent.OpenLink(words.first { it.id == id }.link))
        }
    }

    fun onRefreshClicked() {
        _items.value = toItems(words)
    }

    fun onReloadClicked() {
        _loadingState.value = LoadingState.LOADING
        refresh()
    }

    fun onSignOutClicked() {
        viewModelScope.launch {
            signOutUseCase.execute()
            _event.emit(HomeEvent.Back)
        }
    }

    private fun toItems(words: List<Word>) =
        words.map {
            HomeItemViewModel(it.id, it.question, onClick = {
                onItemClicked(it.id)
            }, onIconClick = {
                onItemIconClicked(it.id)
            })
        }
}

data class HomeItemViewModel(
    val id: Int,
    val title: String,
    val onClick: () -> Unit,
    val onIconClick: () -> Unit,
)

sealed class HomeEvent {
    data object Back : HomeEvent()
    class OpenLink(val link: String) : HomeEvent()
}

enum class LoadingState {
    LOADING,
    LOADED,
    FAILED
}
