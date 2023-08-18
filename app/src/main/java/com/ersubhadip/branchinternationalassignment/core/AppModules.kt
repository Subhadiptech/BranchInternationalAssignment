package com.ersubhadip.branchinternationalassignment.core

import com.ersubhadip.branchinternationalassignment.data.remote.provideAPIInstance
import com.ersubhadip.branchinternationalassignment.data.remote.provideLoggingInterceptor
import com.ersubhadip.branchinternationalassignment.data.remote.provideRetrofit
import com.ersubhadip.branchinternationalassignment.repository.ChatRepository
import org.koin.dsl.module

val globalModules = module {
    factory { provideLoggingInterceptor() }
    factory { provideAPIInstance(get()) }
    single { provideRetrofit() }
    single { ChatRepository(get()) }
}