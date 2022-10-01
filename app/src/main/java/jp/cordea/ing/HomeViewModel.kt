package jp.cordea.ing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.cordea.ing.repository.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {
    private val _items = MutableStateFlow<List<HomeItemViewModel>>(emptyList())
    val items get() = _items.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            val words = repository.findAll()
            _items.value = words.map { HomeItemViewModel(it.id, it.question) }
        }
    }
}

data class HomeItemViewModel(
    val id: Int,
    val title: String
)
