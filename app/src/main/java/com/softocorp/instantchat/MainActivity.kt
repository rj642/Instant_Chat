package com.softocorp.instantchat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.softocorp.instantchat.chat.ui.calls.CallsFragment
import com.softocorp.instantchat.chat.ui.chat.ChatFragment
import com.softocorp.instantchat.chat.ui.status.StatusFragment
import com.softocorp.instantchat.databinding.ActivityMainBinding
import com.softocorp.instantchat.messages.ui.MessageActivity
import com.softocorp.instantchat.utils.Extensions.setUpToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val toolbarController = setUpToolbar(toolbar)

            toolbarController.apply {
                title = "Instant Chat"
            }

            tabLayout.apply {
                for (i in 0..2) {
                    addTab(tabLayout.newTab().setText(
                        when (i) {
                            0 -> "Chat"
                            1 -> "Status"
                            2 -> "Calls"
                            else -> ""
                        }
                    ))
                }
            }

            val fragmentList = listOf<Fragment>(
                ChatFragment(),
                CallsFragment(),
                StatusFragment()
            )

            viewModel.setUpViewPager(dataViewPager, fragmentList, supportFragmentManager, lifecycle)

            floatingActionBtn.setImageResource(R.drawable.ic_chat)

            floatingActionBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, MessageActivity::class.java))
            }

        }

    }

}