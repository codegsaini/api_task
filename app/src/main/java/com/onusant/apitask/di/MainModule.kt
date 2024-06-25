package com.onusant.apitask.di

import android.app.Application
import androidx.room.Room
import com.onusant.apitask.data.repository.PropertyRepository
import com.onusant.apitask.data.repository.LoginRepository
import com.onusant.apitask.data.service.PropertyService
import com.onusant.apitask.data.service.LoginService
import com.onusant.apitask.room.Database
import com.onusant.apitask.room.dao.PropertyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(app: Application) : Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "local_database",
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesKtorClient() : HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json { prettyPrint = true }
                )
            }
        }
    }

    @Provides
    @Singleton
    fun providesPropertyDao(database: Database) : PropertyDao {
        return database.propertyDao()
    }

    @Provides
    @Singleton
    fun providesHomeService(httpClient: HttpClient) : PropertyService {
        return PropertyService(httpClient)
    }

    @Provides
    @Singleton
    fun providesLoginService(httpClient: HttpClient) : LoginService {
        return LoginService(httpClient)
    }

    @Provides
    @Singleton
    fun providesHomeRepository(propertyService: PropertyService, dao: PropertyDao) : PropertyRepository {
        return PropertyRepository(propertyService, dao)
    }


    @Provides
    @Singleton
    fun providesLoginRepository(loginService: LoginService) : LoginRepository {
        return LoginRepository(loginService)
    }

}