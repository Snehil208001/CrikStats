package com.example.crikstatsassignment.di

import android.content.Context
import com.example.crikstatsassignment.data.MockPlayerRepository
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.EntryPoint

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSplitInstallManager(@ApplicationContext context: Context): SplitInstallManager {
        return SplitInstallManagerFactory.create(context)
    }

}


@EntryPoint
@InstallIn(SingletonComponent::class)
interface FeatureModuleDependencies {
    fun mockPlayerRepository(): MockPlayerRepository
}