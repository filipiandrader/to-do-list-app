package com.far.to_dolistapp.di

import android.app.Application
import androidx.room.Room
import com.far.to_dolistapp.data.TodoDatabase
import com.far.to_dolistapp.data.TodoRepository
import com.far.to_dolistapp.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(application, TodoDatabase::class.java, "todo_db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database.dao)
    }

}