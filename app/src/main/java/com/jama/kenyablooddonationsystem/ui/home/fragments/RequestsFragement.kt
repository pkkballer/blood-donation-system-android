package com.jama.kenyablooddonationsystem.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.adapters.RequestsAdapter
import com.jama.kenyablooddonationsystem.viewModels.home.RequestsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_requests.*
import kotlinx.android.synthetic.main.fragment_requests.view.*

class RequestsFragement : Fragment() {

    private lateinit var fragementView: View
    private lateinit var requestFragmentViewModel: RequestsFragmentViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var requestsAdapter: RequestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragementView = inflater.inflate(R.layout.fragment_requests, container, false)
        requestFragmentViewModel = activity.run {
            ViewModelProviders.of(this!!)[RequestsFragmentViewModel::class.java]
        }

        requestsAdapter = RequestsAdapter(mutableListOf(), fragementView.context)
        viewManager = LinearLayoutManager(fragementView.context)
        viewAdapter = requestsAdapter

        fragementView.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(fragementView.context, DividerItemDecoration.VERTICAL))
        }

        requestFragmentViewModel.requestModelList.observe(this, Observer {
            requestsAdapter.insertData(it)
            recyclerView.smoothScrollToPosition(0)
        })

        requestFragmentViewModel.listenToRequests(activity!!)

        return fragementView
    }
}
