package com.valdroide.thesportsbillboardinstitution.main_user.navigation

import android.content.Context

class NavigationActivityInteractorImpl(private val repository: NavigationActivityRepository) : NavigationActivityInteractor {

    override fun getMenusAndSubMenus(context: Context) {
        repository.getMenusAndSubMenus(context)
    }
}
