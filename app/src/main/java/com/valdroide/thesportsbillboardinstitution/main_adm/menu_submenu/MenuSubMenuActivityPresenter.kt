package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.events.MenuSubMenuActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface MenuSubMenuActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getMenuSubMenu(context: Context)
    fun saveMenu(context: Context, menu: MenuDrawer)
    fun updateMenu(context: Context, menu: MenuDrawer)
    fun activeOrUnActiveMenu(context: Context, menu: MenuDrawer)
    fun deleteMenu(context: Context, menu: MenuDrawer)
    fun saveSubMenu(context: Context, subMenu: SubMenuDrawer)
    fun updateSubMenu(context: Context, subMenu: SubMenuDrawer)
    fun activeOrUnActiveSubMenu(context: Context, SubMenu: SubMenuDrawer)
    fun deleteSubMenu(context: Context, subMenu: SubMenuDrawer)
    fun onEventMainThread(event: MenuSubMenuActivityEvent)
}