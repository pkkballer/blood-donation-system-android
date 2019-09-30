package com.jama.kenyablooddonationsystem.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jama.kenyablooddonationsystem.ui.home.fragments.EventsFragment
import com.jama.kenyablooddonationsystem.ui.home.fragments.RequestsFragment
import com.jama.kenyablooddonationsystem.ui.home.fragments.AcceptedRequestsFragment
import com.jama.kenyablooddonationsystem.ui.home.fragments.ProfileFragment

class HomeActivityAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> RequestsFragment()
            2 -> AcceptedRequestsFragment()
            1 -> EventsFragment()
            3 -> ProfileFragment()
            else -> RequestsFragment()
        }
    }

    override fun getCount(): Int = 4
}