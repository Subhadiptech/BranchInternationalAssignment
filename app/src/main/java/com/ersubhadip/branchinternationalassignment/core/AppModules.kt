package com.ersubhadip.branchinternationalassignment.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ersubhadip.branchinternationalassignment.data.local.Session
import com.ersubhadip.branchinternationalassignment.data.local.provideDataStore
import com.ersubhadip.branchinternationalassignment.data.remote.ChatAPI
import com.ersubhadip.branchinternationalassignment.repository.ChatRepository
import com.ersubhadip.branchinternationalassignment.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ChatAPI = retrofit.create(ChatAPI::class.java)

    @Provides
    @Singleton
    fun getRepository(api: ChatAPI, localStorage: Session) =
        ChatRepository(apis = api, localStorage = localStorage)

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context) =
        provideDataStore(context)


    @Provides
    @Singleton
    fun getSession(data: DataStore<Preferences>) = Session(data)
}