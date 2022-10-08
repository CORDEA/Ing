package jp.cordea.ing

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.cordea.ing.api.WordApi
import jp.cordea.ing.usecase.GetAuthTokenUseCase
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideJson(): Json =
        Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideClient(getAuthTokenUseCase: GetAuthTokenUseCase): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer ${getAuthTokenUseCase.execute()}"
                        )
                        .build()
                )
            }
            .let {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                } else {
                    it
                }
            }
            .build()

    @Provides
    @Singleton
    @OptIn(ExperimentalSerializationApi::class)
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://script.googleapis.com/")
            .addConverterFactory(
                json.asConverterFactory(MediaType.parse("application/json")!!)
            )
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideWordApi(retrofit: Retrofit): WordApi =
        retrofit.create(WordApi::class.java)

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context
    ): GoogleSignInClient =
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(
                    Scope("https://www.googleapis.com/auth/spreadsheets")
                )
                .build()
        )
}
