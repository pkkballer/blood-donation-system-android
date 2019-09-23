package com.jama.kenyablooddonationsystem.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.adapters.RequestsAdapter
import com.jama.kenyablooddonationsystem.viewModels.home.RequestsViewModel
import kotlinx.android.synthetic.main.fragment_requests.view.*
import kotlinx.coroutines.withContext

class RequestsFragement : Fragment() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val dataSet = arrayOf("Jama", "Mohamed")

    private lateinit var fragementView: View
    private lateinit var requestViewModel: RequestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragementView = inflater.inflate(R.layout.fragment_requests, container, false)
        viewManager = LinearLayoutManager(fragementView.context)
        viewAdapter = RequestsAdapter(dataSet)

        requestViewModel = activity.run {
            ViewModelProviders.of(this!!)[RequestsViewModel::class.java]
        }

        fragementView.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return fragementView
    }
}
