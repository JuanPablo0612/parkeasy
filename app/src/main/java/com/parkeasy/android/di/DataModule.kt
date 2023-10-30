package com.parkeasy.android.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
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

/**
 * UniversalTutorialApiKey class.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UniversalTutorialApiKey

/**
 * UniversalTutorialRetrofit class.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UniversalTutorialRetrofit

/**
 * UniversalTutorialApiService class.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UniversalTutorialApiService

/**
 * FlagCdnRetrofit class.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlagCdnRetrofit

/**
 * FlagCdnApiService class.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlagCdnApiService

/**
 * DataModule object.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    /**
     * Provides an instance of [FirebaseAuth] by using the [Firebase.auth] property.
     *
     * @return The [FirebaseAuth] instance.
     */
    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    /**
     * This method is declared in the `DataModule` class and is annotated with
     * `@Singleton` and `@Provides`. It provides an instance of `FirebaseFirestore` by
     * calling the `Firebase.firestore` property.
     */
    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    /**
     * Provides a singleton instance of the settings DataStore.
     *
     * @param context The application context.
     * @return The settings DataStore instance.
     */
    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("settings") })

    /**
     * Provides a [FusedLocationProviderClient] instance.
     *
     * @param context The application context.
     * @return The [FusedLocationProviderClient] instance.
     */
    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Provides an instance of Retrofit for Universal Tutorial API.
     *
     * @return Retrofit instance for Universal Tutorial API.
     */
    @Singleton
    @Provides
    @UniversalTutorialRetrofit
    fun provideUniversalTutorialRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.universal-tutorial.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Provides an instance of [PlacesApiService] using the provided [retrofit] instance.
     *
     * @param retrofit The [Retrofit] instance used for creating the [PlacesApiService].
     * @return An instance of [PlacesApiService] created using the [retrofit] instance.
     */
    @Singleton
    @Provides
    @UniversalTutorialApiService
    fun providePlacesApiService(@UniversalTutorialRetrofit retrofit: Retrofit): PlacesApiService =
        retrofit.create(PlacesApiService::class.java)

    /**
     * Provides a singleton instance of Retrofit for FlagCdn API.
     *
     * @return Retrofit instance for FlagCdn API.
     */
    @Singleton
    @Provides
    @FlagCdnRetrofit
    fun provideFlagCdnRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://flagcdn.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Provides the [FlagsApiService] instance using the provided [retrofit] instance.
     *
     * @param retrofit The [Retrofit] instance used for creating the [FlagsApiService].
     * @return The [FlagsApiService] instance.
     */
    @Singleton
    @Provides
    @FlagCdnApiService
    fun provideFlagCdnApiService(@FlagCdnRetrofit retrofit: Retrofit): FlagsApiService =
        retrofit.create(FlagsApiService::class.java)
}