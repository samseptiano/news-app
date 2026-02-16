package com.samuelseptiano.mvvmroom.di

import android.content.Context
import androidx.room.Room
import com.samuelseptiano.mvvmroom.data.datasource.local.NewsDatabase
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.BookmarkNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.BookmarkRemoteKeysDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.HeadlineNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.HeadlineRemoteKeysDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.SearchNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.SearchRemoteKeysDao
import com.samuelseptiano.mvvmroom.data.repository.local.LocalBookmarkNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.local.LocalBookmarkNewsRepositoryImpl
import com.samuelseptiano.mvvmroom.data.repository.local.LocalNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.local.LocalNewsRepositoryImpl
import com.samuelseptiano.mvvmroom.data.repository.local.LocalSearchNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.local.LocalSearchNewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAlertDatabase(@ApplicationContext context: Context): NewsDatabase {
        val passphrase = SQLiteDatabase.getBytes("secure-key-samuel".toCharArray())
        val factory = SupportFactory(passphrase)

        // Build encrypted Room database
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.db"
        )
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): HeadlineNewsDao {
        return newsDatabase.headlineNewsDao()
    }

    @Singleton
    @Provides
    fun provideHeadlineRemoteKeysDao(
        newsDatabase: NewsDatabase
    ): HeadlineRemoteKeysDao {
        return newsDatabase.headlineRemoteKeysDao()
    }


    @Singleton
    @Provides
    fun provideLocalNewsRepository(
        headlineNewsDao: HeadlineNewsDao
    ): LocalNewsRepository =
        LocalNewsRepositoryImpl(headlineNewsDao)


    @Singleton
    @Provides
    fun provideBookmarkNewsDao(
        newsDatabase: NewsDatabase
    ): BookmarkNewsDao {
        return newsDatabase.bookmarkNewsDao()
    }

    @Singleton
    @Provides
    fun provideBookmarkRemoteKeysDao(
        newsDatabase: NewsDatabase
    ): BookmarkRemoteKeysDao {
        return newsDatabase.bookmarkRemoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideLocalBookmarkNewsRepository(
        bookmarkNewsDao: BookmarkNewsDao
    ): LocalBookmarkNewsRepository =
        LocalBookmarkNewsRepositoryImpl(bookmarkNewsDao)


    @Singleton
    @Provides
    fun provideSearchNewsDao(
        newsDatabase: NewsDatabase
    ): SearchNewsDao {
        return newsDatabase.searchNewsDao()
    }

    @Singleton
    @Provides
    fun provideSearchRemoteKeysDao(
        newsDatabase: NewsDatabase
    ): SearchRemoteKeysDao {
        return newsDatabase.searchRemoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideLocalSearchNewsRepository(
        searchNewsDao: SearchNewsDao
    ): LocalSearchNewsRepository =
        LocalSearchNewsRepositoryImpl(searchNewsDao)
}