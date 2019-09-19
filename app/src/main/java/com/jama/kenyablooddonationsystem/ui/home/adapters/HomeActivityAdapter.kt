package com.jama.kenyablooddonationsystem.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jama.kenyablooddonationsystem.ui.home.fragments.EventsFragment
import com.jama.kenyablooddonationsystem.ui.home.fragments.HomeFragment
import com.jama.kenyablooddonationsystem.ui.home.fragments.NotificationFragement
import com.jama.kenyablooddonationsystem.ui.home.fragments.ProfileFragment

class HomeActivityAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> EventsFragment()
            2 -> NotificationFragement()
            3 -> ProfileFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int = 4
}