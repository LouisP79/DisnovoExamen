package com.disnovoexamen.di

import com.disnovoexamen.data.form.FormWebServices
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { provideFormServices(get()) }
}

fun provideFormServices(retrofit: Retrofit): FormWebServices = retrofit.create(FormWebServices::class.java)

