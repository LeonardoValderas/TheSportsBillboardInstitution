package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.TeamUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.TeamUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import dagger.Component
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
@Component(modules = arrayOf(TeamUpdateFragmentModule::class, LibsModule::class))
interface TeamUpdateFragmentComponent {
    fun inject(fragment:TeamUpdateFragment)
}
