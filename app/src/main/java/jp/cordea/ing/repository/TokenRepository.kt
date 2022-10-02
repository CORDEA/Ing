package jp.cordea.ing.repository

import android.accounts.Account
import android.content.Context
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private companion object {
        const val NAME = "ing"
        const val KEY = "key_token"
    }

    private val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun find(request: TokenRequest): String = when (request) {
        TokenRequest.Local -> preferences.getString(KEY, "") ?: ""
        is TokenRequest.Remote ->
            GoogleAuthUtil.getToken(
                context,
                request.account,
                "oauth2:${request.scopes.joinToString(" ") { it.scopeUri }}"
            )
    }

    fun insert(token: String) =
        preferences.edit().putString(KEY, token).apply()
}

sealed class TokenRequest {
    class Remote(
        val account: Account,
        val scopes: Set<Scope>
    ) : TokenRequest()

    object Local : TokenRequest()
}
