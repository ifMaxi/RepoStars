package com.maxidev.repostars.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maxidev.repostars.data.remote.RepoStarsApiService
import com.maxidev.repostars.utils.ConstantUtils.API_KEY
import com.maxidev.repostars.utils.ConstantUtils.AUTHORIZATION
import com.maxidev.repostars.utils.ConstantUtils.BASE_URL
import com.maxidev.repostars.utils.ConstantUtils.CACHE_CONTROL
import com.maxidev.repostars.utils.ConstantUtils.CACHE_MAX_AGE
import com.maxidev.repostars.utils.ConstantUtils.CONTENT_TYPE
import com.maxidev.repostars.utils.ConstantUtils.FILE_CACHE_NAME
import com.maxidev.repostars.utils.ConstantUtils.TIMER_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    CONTENT_TYPE.toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {

        return Interceptor { chain ->
            val response  = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(CACHE_MAX_AGE, TimeUnit.HOURS)
                .build()

            response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .addHeader(AUTHORIZATION, "Bearer $API_KEY")
                .build()
        }
    }

    @Provides
    @Singleton
    fun providesCache(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor
    ): OkHttpClient {
        val file = File(context.cacheDir, FILE_CACHE_NAME)
        val cache = Cache(file, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(interceptor)
            .connectTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): RepoStarsApiService {
        return retrofit.create(RepoStarsApiService::class.java)
    }
}