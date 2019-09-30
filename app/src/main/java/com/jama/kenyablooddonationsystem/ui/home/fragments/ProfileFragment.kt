package com.jama.kenyablooddonationsystem.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.services.LeafletWebviewClient
import com.jama.kenyablooddonationsystem.viewModels.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var fragementView: View
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragementView = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel = activity.run {
            ViewModelProviders.of(this!!)[ProfileViewModel::class.java]
        }

        fragementView.webView.settings.javaScriptEnabled = true

        profileViewModel.userModel.observe(this, Observer {
            fragementView.textViewFullNames.text = it.fullName
            fragementView.textViewGender.text = it.gender
            fragementView.textViewEmail.text = it.email
            fragementView.textViewPhone.text = it.phone
            fragementView.textViewNationaID.text = it.nationalId
            fragementView.textViewBloodType.text = it.bloodType
        })

        profileViewModel.donationDetailsModel.observe(this, Observer {
            fragementView.textViewNoOFDonations.text = it.noOfDonations.toString()
            fragementView.textViewLastDonated.text = it.lastDonated
            fragementView.textViewHname.text = it.hname
            fragementView.textViewPlace.text = it.placeOfDonation
            fragementView.webView.loadUrl("file:///android_asset/leaflet/leaflet.html")
            fragementView.webView.webViewClient = LeafletWebviewClient(fragementView.webView,
                it.latlng["lat"]!!,
                it.latlng["lng"]!!
            )
        })

        profileViewModel.getUserProfile()

        return  fragementView
    }
}
