package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.TournamentActivityPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TournamentActivityModule::class, LibsModule::class))
interface TournamentActivityComponent {
    fun getPresenter(): TournamentActivityPresenter
}