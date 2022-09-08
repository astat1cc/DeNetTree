package com.github.astat1cc.denettree.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.astat1cc.denettree.database.NodeDao
import com.github.astat1cc.denettree.database.NodeDatabase
import com.github.astat1cc.denettree.repository.NodeRepository
import com.github.astat1cc.denettree.ui.recyclerview.NodeAdapter
import com.github.astat1cc.denettree.ui.viewmodel.NodeViewModel
import com.github.astat1cc.denettree.utils.AppResourceProvider
import com.github.astat1cc.denettree.utils.Consts
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single {
        AppResourceProvider(androidContext())
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    single {
        Room.databaseBuilder(androidContext(), NodeDatabase::class.java, Consts.DATABASE_NAME)
            .build()
    }
    single {
        provideDao(database = get())
    }
    single {
        NodeRepository(sharedPref = get(), dao = get())
    }
    viewModel {
        NodeViewModel(repository = get())
    }
}

fun provideDao(database: NodeDatabase) = database.nodeDao()
