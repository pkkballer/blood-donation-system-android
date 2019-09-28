package com.jama.kenyablooddonationsystem.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.adapters.EventAdapter
import com.jama.kenyablooddonationsystem.viewModels.event.EventViewModel
import kotlinx.android.synthetic.main.fragment_requests.*
import kotlinx.android.synthetic.main.fragment_requests.view.recyclerView

class EventsFragment : Fragment() {

    private lateinit var fragementView: View
    private lateinit var eventViewModel: EventViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragementView = inflater.inflate(R.layout.fragment_events, container, false)

        eventViewModel = activity.run {
            ViewModelProviders.of(this!!)[EventViewModel::class.java]
        }

        eventAdapter = EventAdapter(mutableListOf(), fragementView.context)
        viewManager = LinearLayoutManager(fragementView.context)
        viewAdapter = eventAdapter

        fragementView.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        eventViewModel.eventModelList.observe(this, Observer {
            eventAdapter.insertData(it)
            recyclerView.smoothScrollToPosition(0)
        })

        eventViewModel.listenToEvents(activity!!)

        return fragementView
    }
}
