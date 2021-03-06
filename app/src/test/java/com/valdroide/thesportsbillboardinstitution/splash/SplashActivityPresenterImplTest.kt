package com.valdroide.thesportsbillboardinstitution.splash

import android.content.Context
import com.nhaarman.mockito_kotlin.whenever
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import org.junit.Before
import com.valdroide.thesportsbillboardinstitution.main_user.splash.SplashActivityPresenterImpl
import com.valdroide.thesportsbillboardinstitution.main_user.splash.ui.SplashActivityView
import org.mockito.Mock
import com.valdroide.thesportsbillboardinstitution.main_user.splash.SplashActivityInteractor
import com.valdroide.thesportsbillboardinstitution.main_user.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import org.junit.Test
import org.mockito.Mockito.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull

class SplashActivityPresenterImplTest : BaseTest() {

    var presenter: SplashActivityPresenterImpl? = null
    @Mock
    var view: SplashActivityView? = null
    @Mock
    var eventBus: EventBus? = null
    @Mock
    var interactor: SplashActivityInteractor? = null
    @Mock
    lateinit var context: Context
    @Mock
    var login: Login? = null
    @Mock
    var event: SplashActivityEvent? = null
    @Mock
    lateinit var dateData: DateData
    @Before
    override fun setUp() {
        super.setUp()
        presenter = SplashActivityPresenterImpl(view!!, eventBus!!, interactor!!)
    }

    @Test
    fun onCreateTest() {
        presenter?.onCreate()
        verify(eventBus)?.register(this)
    }

    @Test
    fun onDestroyTest() {
        presenter?.onDestroy()
        verify(eventBus)?.unregister(this)
    }

    @Test
    fun getDateClubTest() {
        interactor?.getDate(context)
        verify(interactor)?.getDate(context)
    }

    @Test
    fun validateDateClubTest() {
        interactor?.validateDate(context, dateData)
        verify(interactor)?.validateDate(context, dateData)
    }

    @Test
    fun getLoginTest() {
        interactor?.getLogin(context)
        verify(interactor)?.getLogin(context)
    }

    @Test
    fun validateLoginTest() {
        interactor?.validateLogin(context, login!!)
        verify(interactor)?.validateLogin(context, login!!)
    }

//    override fun sendEmail(context: Context, comment: String) {
//        interactor.sendEmail(context, comment)
//    }

    @Test
    fun getSplashViewTest() {
        assertEquals(view, presenter?.getSplashView())
    }

    @Test
    fun onEventMainThreadGoToLogTest() {
        whenever(event?.type).thenReturn(SplashActivityEvent.GOTOLOG)
        presenter?.onEventMainThread(event!!)
        assertNotNull(presenter?.getSplashView())
        verify(view)?.hideDialogProgress()
        verify(view)?.goToLog()
    }

    @Test
    fun onEventMainThreadErrorTest() {
        whenever(event?.type).thenReturn(SplashActivityEvent.ERROR)
        presenter?.onEventMainThread(event!!)
        assertNotNull(presenter?.getSplashView())
        verify(view)?.hideDialogProgress()
        verify(view)?.setError(event!!.error!!)
    }

    @Test
    fun onEventMainThreadGoToNavTest() {
        whenever(event?.type).thenReturn(SplashActivityEvent.GOTONAV)
        presenter?.onEventMainThread(event!!)
        assertNotNull(presenter?.getSplashView())
        verify(view)?.hideDialogProgress()
        verify(view)?.goToNav()
    }
}