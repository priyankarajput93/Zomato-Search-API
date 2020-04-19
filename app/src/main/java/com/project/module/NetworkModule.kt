package com.project.module

import com.project.BuildConfig
import com.project.api.ApiService
import com.project.utils.Constants
import com.project.utils.Constants.APPLICATION_JSON
import com.project.utils.Constants.CONTENT_TYPE
import com.project.utils.Constants.USER_KEY
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by Priyanka on 15/04/2020
 */

val networkModule = module {
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService(
            createHttpClient(),
            RxJava2CallAdapterFactory.create(),
            Constants.BASE_URL
        )
    }
}
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(httpLoggingInterceptor)
    }
    client.addInterceptor(Interceptor { chain ->
        val request =
            chain.request().newBuilder().addHeader(USER_KEY,Constants.ZOMATO_APP_KEY).build()
        chain.proceed(request)
    })
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header(CONTENT_TYPE,APPLICATION_JSON )
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
fun createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiService::class.java)
}
