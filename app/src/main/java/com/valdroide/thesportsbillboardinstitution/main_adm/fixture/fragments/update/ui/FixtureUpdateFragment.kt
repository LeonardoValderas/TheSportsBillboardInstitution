package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.activity.TabFixtureActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.FixtureUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di.FixtureUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.adapter.FixtureUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener_2
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.frame_recycler_refresh.*

class FixtureUpdateFragment : Fragment(), FixtureUpdateFragmentView, GenericOnItemClickListener_2 {

    private lateinit var component: FixtureUpdateFragmentComponent
    lateinit var presenter: FixtureUpdateFragmentPresenter
    lateinit var adapterFixture: FixtureUpdateFragmentAdapter
    var Fixtures: MutableList<Fixture> = arrayListOf()
    private var isRegister: Boolean = false
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getFixture(activity)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getFixtureUpdateFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterFixture = getAdapter()
    }

    fun register() {
        if (!isRegister) {
            presenter.onCreate()
            isRegister = true
        }
    }

    fun unregister() {
        if (isRegister) {
            presenter.onDestroy()
            isRegister = false
        }
    }

    fun showAlertDialog(title: String, msg: String, Fixture: Fixture) {
        val alertDilog = AlertDialog.Builder(activity).create()
        alertDilog.setTitle(title)
        alertDilog.setMessage(msg)

        alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialogInterface, i ->
            showSwipeRefreshLayout()
            presenter.deleteFixture(activity, Fixture)
        })

        alertDilog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCELAR", { dialogInterface, j ->
            alertDilog.dismiss()
        })
        alertDilog.show()
    }

    open fun getPresenterInj(): FixtureUpdateFragmentPresenter = component.getPresenter()


    open fun getAdapter(): FixtureUpdateFragmentAdapter = component.getAdapter()

    override fun onClickUpdate(position: Int, any: Any) {
        val i = Intent(activity, TabFixtureActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_fixture", (any as Fixture).ID_FIXTURE_KEY)
        startActivity(i)
    }

    override fun onClickDelete(position: Int, any: Any) {
        this.position = position
        showAlertDialog(getString(R.string.alert_title), getString(R.string.delete_simple_alerte_msg, "el fixture"), any as Fixture)
    }

    override fun updateFixtureSuccess() {
        adapterFixture.notifyDataSetChanged()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Fixture"))
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterFixture
        }
    }

    override fun setAllFixture(Fixtures: MutableList<Fixture>) {
        this.Fixtures = Fixtures
        adapterFixture.setFixture(Fixtures)
    }

    override fun deleteFixtureSuccess() {
        adapterFixture.deleteFixture(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.delete_success, "Sanción"))
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }

    private fun verifySwipeRefresh(show: Boolean) {
        try {
            if (swipeRefreshLayout != null) {
                if (show) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true)
                    }
                } else {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false)
                    }
                }
            }
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    private fun initSwipeRefreshLayout() {
        try {
            swipeRefreshLayout.setOnRefreshListener({
                presenter.getFixture(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    fun refreshAdapter() {
        presenter.getFixture(activity)
    }

    override fun onPause() {
        unregister()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        register()
//        mAd.resume(this)
//        if (mAdView != null) {
//            mAdView.resume()
//        }
    }

    override fun onStart() {
        super.onStart()
        register()
    }

    override fun onStop() {
        unregister()
        super.onStop()
    }

    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }
}