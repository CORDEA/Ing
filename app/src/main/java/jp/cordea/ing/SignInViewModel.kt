package jp.cordea.ing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.cordea.ing.usecase.RequestAuthTokenUseCase
import jp.cordea.ing.usecase.SignInRequest
import jp.cordea.ing.usecase.SignInUseCase
import jp.cordea.ing.usecase.StoreAuthTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val storeAuthTokenUseCase: StoreAuthTokenUseCase,
    private val requestAuthTokenUseCase: RequestAuthTokenUseCase
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
        account.account?.let {
            viewModelScope.launch {
                val token = requestAuthTokenUseCase.execute(it, account.grantedScopes)
                storeAuthTokenUseCase.execute(token)
                _event.emit(SignInEvent.ToHome)
            }
        }
    }

    fun onSignInFailed(throwable: Throwable) {
        val message = throwable.localizedMessage ?: throwable.message ?: return
        viewModelScope.launch {
            _event.emit(SignInEvent.ShowError(message))
        }
    }
}

sealed class SignInEvent {
    class StartSignIn(val request: SignInRequest) : SignInEvent()
    data object ToHome : SignInEvent()
    class ShowError(val message: String) : SignInEvent()
}
