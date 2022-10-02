package jp.cordea.ing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.cordea.ing.usecase.SignInRequest
import jp.cordea.ing.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _event = MutableSharedFlow<SignInEvent>()
    val event = _event.asSharedFlow()

    fun onSignInClicked() {
        val request = signInUseCase.execute()
        viewModelScope.launch {
            _event.emit(SignInEvent.StartSignIn(request))
        }
    }

    fun onSignInSucceeded(account: GoogleSignInAccount) {

    }

    fun onSignInFailed(throwable: Throwable) {
    }
}

sealed class SignInEvent {
    class StartSignIn(val request: SignInRequest) : SignInEvent()
}
