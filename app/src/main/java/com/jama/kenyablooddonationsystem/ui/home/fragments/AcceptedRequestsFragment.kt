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
import com.jama.kenyablooddonationsystem.ui.home.adapters.AcceptedRequestAdapter
import com.jama.kenyablooddonationsystem.viewModels.request.RequestsViewModel
import kotlinx.android.synthetic.main.accepted_requests_fragement.view.*

class AcceptedRequestsFragment : Fragment() {

    private lateinit var fragementView: View
    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var acceptedRequestAdapter: AcceptedRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragementView = inflater.inflate(R.layout.fragment_requests, container, false)
        requestsViewModel = activity.run {
            ViewModelProviders.of(this!!)[RequestsViewModel::class.java]
        }

        acceptedRequestAdapter =
            AcceptedRequestAdapter(
                mutableListOf(),
                fragementView.context
            )
        viewManager = LinearLayoutManager(fragementView.context)
        viewAdapter = acceptedRequestAdapter

        fragementView.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(fragementView.context, DividerItemDecoration.VERTICAL))
        }

        requestsViewModel.acceptedRequestModelList.observe(this, Observer {
            acceptedRequestAdapter.insertData(it)
            fragementView.recyclerView.smoothScrollToPosition(0)
        })

        requestsViewModel.closeQrCode.observe(this, Observer {
            println("this is $it")
        })

        requestsViewModel.getAcceptedRequests()

        return fragementView
    }
}
