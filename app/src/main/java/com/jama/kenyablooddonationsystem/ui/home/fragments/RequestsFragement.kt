package com.jama.kenyablooddonationsystem.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.viewModels.home.RequestsViewModel
import kotlinx.android.synthetic.main.fragment_requests.view.*
import kotlinx.coroutines.withContext

class RequestsFragement : Fragment() {

    private lateinit var fragementView: View
    private lateinit var requestViewModel: RequestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragementView = inflater.inflate(R.layout.fragment_requests, container, false)

        requestViewModel = activity.run {
            ViewModelProviders.of(this!!)[RequestsViewModel::class.java]
        }

        fragementView.buttonGeo.setOnClickListener {
            requestViewModel.setupGoe(activity!!)
        }

        return fragementView
    }
}
