package com.example.androidsocialnetwork.di

import com.example.androidsocialnetwork.data.SimpleOkHttpCookieJar
import com.example.androidsocialnetwork.ui.LoginFragment
import com.example.androidsocialnetwork.ui.UserFragment
import com.example.androidsocialnetwork.ui.UsersListFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(fragment: UsersListFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: UserFragment)
}

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://social-network.samuraijs.com/api/1.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .cookieJar(SimpleOkHttpCookieJar())
            .addInterceptor{chain ->
                chain.proceed(
                    chain.request().newBuilder().
                    addHeader(
                        "API-KEY",
                        "41d75a6f-e250-4501-b54b-f819ffc5379d"
                        ).build()
                )
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}