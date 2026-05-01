package ky.apelaralash.fontines.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ky.apelaralash.fontines.data.repository.FontRepositoryImpl
import ky.apelaralash.fontines.domain.repository.FontRepository
import javax.inject.Singleton

/**
 * Hilt модуль для привязки репозиториев
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFontRepository(
        fontRepositoryImpl: FontRepositoryImpl
    ): FontRepository
}