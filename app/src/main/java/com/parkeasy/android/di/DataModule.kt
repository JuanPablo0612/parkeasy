package com.parkeasy.android.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.parkeasy.android.data.places.remote.FlagsApiService
import com.parkeasy.android.data.places.remote.PlacesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UniversalTutorialApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UniversalTutorialRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UniversalTutorialApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlagCdnRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlagCdnApiService

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("settings") })

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    @UniversalTutorialRetrofit
    fun provideUniversalTutorialRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.universal-tutorial.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @UniversalTutorialApiService
    fun providePlacesApiService(@UniversalTutorialRetrofit retrofit: Retrofit): PlacesApiService =
        retrofit.create(PlacesApiService::class.java)

    @Singleton
    @Provides
    @FlagCdnRetrofit
    fun provideFlagCdnRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://flagcdn.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @FlagCdnApiService
    fun provideFlagCdnApiService(@FlagCdnRetrofit retrofit: Retrofit): FlagsApiService =
        retrofit.create(FlagsApiService::class.java)
}