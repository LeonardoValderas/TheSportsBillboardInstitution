package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fixture_item.view.*

class FixtureFragmentAdapter(private var fixtures: MutableList<Fixture>?, private var listener: OnItemClickListener, fragment: Fragment) : RecyclerView.Adapter<FixtureFragmentAdapter.ViewHolder>() {
    private val fragment: Fragment

    init {
        this.fragment = fragment as FixtureFragment
    }

    companion object {
        private val FIXTURE = 0
        private val BUTTON_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View? = null
        if (viewType == FIXTURE)
            view = LayoutInflater.from(parent.context).inflate(R.layout.fixture_item, parent, false)
        else if (viewType == BUTTON_MORE)
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_button_add_more, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ((position + 1) < itemCount)
            holder.bindData(fixtures!!.get(position), position, listener, fragment)
        else
            holder.bindButton(listener)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(fixture: Fixture, position: Int, listener: OnItemClickListener, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(fixture) {
                itemView.textViewDateHour.text = DATE_MATCH + " - " + HOUR_MATCH +" hs."
                Utils.setPicasso(fragment.activity, URL_LOCAL_TEAM, R.drawable.lacumbre, itemView.imageViewLocalTeam)
                Utils.setPicasso(fragment.activity, URL_VISIT_TEAM, R.drawable.lacumbre, itemView.imageViewVisitTeam)
                itemView.textViewLocalResult.text = RESULT_LOCAL
                itemView.textViewVisitResult.text = RESULT_VISIT
                itemView.textViewLocalName.text = NAME_LOCAL_TEAM
                itemView.textViewVisitName.text = NAME_VISITA_TEAM
                itemView.textViewFieldMatch.text = NAME_FIELD
                itemView.textViewAddress.text = ADDRESS
                if (OBSERVATION != null && !OBSERVATION.isEmpty()) {
                    itemView.textViewObservation.visibility = View.VISIBLE
                    itemView.textViewObservation.text = OBSERVATION
                } else {
                    itemView.textViewObservation.visibility = View.GONE
                }
            }
            setOnItemClickListener(listener, position, fixture)
        }

        fun bindButton(listener: OnItemClickListener) {
            itemView!!.setOnClickListener({ view ->
                listener.onClickButtonAddMore()
            })
        }

        fun setOnItemClickListener(listener: OnItemClickListener, position: Int, fixture: Fixture) {
            itemView.setOnClickListener {
                //                    listener.onClickFixture(position, fixture);
            }
        }
    }

    override fun getItemCount(): Int =
            fixtures!!.size

    override fun getItemViewType(position: Int): Int {
        if ((position + 1) < itemCount)
            return FIXTURE
        else
            return BUTTON_MORE
    }

    fun setFixtures(fixtures: MutableList<Fixture>, isClick: Boolean) {
        if (isClick || (itemCount == 0 && fixtures.isNotEmpty()))
            this.fixtures = addFixture(fixtures)
        else
            this.fixtures = fixtures
        notifyDataSetChanged()
    }

    fun addFixture(fixtures: MutableList<Fixture>): MutableList<Fixture> {
        fixtures.add(fixtures.size, Fixture())
        return fixtures
    }
}
