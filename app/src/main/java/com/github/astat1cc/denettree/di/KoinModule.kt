package com.github.astat1cc.denettree.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.github.astat1cc.denettree.data.NodeRepositoryImpl
import com.github.astat1cc.denettree.data.local.NodeDatabase
import com.github.astat1cc.denettree.domain.NodeInteractor
import com.github.astat1cc.denettree.domain.NodeRepository
import com.github.astat1cc.denettree.presentation.viewmodel.NodeViewModel
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
    single<NodeRepository> {
        NodeRepositoryImpl(sharedPref = get(), dao = get())
    }
    single<NodeInteractor> {
        NodeInteractor.Impl(repository = get())
    }
    viewModel {
        NodeViewModel(interactor = get())
    }
}

fun provideDao(database: NodeDatabase) = database.nodeDao()