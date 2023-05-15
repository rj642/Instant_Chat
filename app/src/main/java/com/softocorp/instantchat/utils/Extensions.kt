package com.softocorp.instantchat.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.softocorp.instantchat.chat.ui.adapter.ViewPagerAdapter

object Extensions {

    @JvmStatic
    val Context.mAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    @JvmStatic
    val Context.mDatabase: FirebaseDatabase
        get() = FirebaseDatabase.getInstance()

    @JvmStatic
    val Context.mStorage: FirebaseStorage
        get() = FirebaseStorage.getInstance()

    /**
     * Use this function for setting up toolbar
     */
    @JvmStatic
    fun AppCompatActivity.setUpToolbar(toolbar: Toolbar): Toolbar {
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowTitleEnabled(false)
        }
        return toolbar
    }

    @JvmStatic
    fun Context.log(message: String) {
        Log.d(this.packageName, message)
    }

    @JvmStatic
    fun ViewPager2.setUpAdapter(list: List<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) {
        this.adapter = ViewPagerAdapter(list, fm, lifecycle)
    }

    @JvmStatic
    fun RecyclerView.scrollRecycler(size: Int) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
            this.scrollToPosition(size)
            }, 400L
        )
    }

}