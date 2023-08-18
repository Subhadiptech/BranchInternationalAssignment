package com.ersubhadip.branchinternationalassignment.core

import com.ersubhadip.branchinternationalassignment.data.local.provideDataStore
import com.ersubhadip.branchinternationalassignment.data.remote.provideAPIInstance
import com.ersubhadip.branchinternationalassignment.data.remote.provideLoggingInterceptor
import com.ersubhadip.branchinternationalassignment.data.remote.provideRetrofit
import com.ersubhadip.branchinternationalassignment.presentation.chat.ChatViewModel
import com.ersubhadip.branchinternationalassignment.presentation.home.HomeViewModel
import com.ersubhadip.branchinternationalassignment.presentation.login.LoginViewModel
import com.ersubhadip.branchinternationalassignment.repository.ChatRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val globalModules = module {
    factory { provideLoggingInterceptor() }
    factory { provideAPIInstance(get()) }
    single { provideRetrofit() }
    single { ChatRepository(get()) }
    single { provideDataStore(androidContext()) }
    viewModel {
        LoginViewModel(get())
        HomeViewModel(get())
        ChatViewModel(get())
    }
}