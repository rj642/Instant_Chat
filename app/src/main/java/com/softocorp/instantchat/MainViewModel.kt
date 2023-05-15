package com.softocorp.instantchat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.softocorp.instantchat.utils.Extensions.setUpAdapter

class MainViewModel: ViewModel() {

    fun setUpViewPager(viewPager: ViewPager2, list: List<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) {
        viewPager.setUpAdapter(list, fm, lifecycle)
    }

}