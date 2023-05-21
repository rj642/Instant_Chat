package com.softocorp.instantchat.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.softocorp.instantchat.R
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
    fun AppCompatActivity.setUpToolbar(
        toolbar: Toolbar,
        showTitle: Boolean = false,
        showBackButton: Boolean = false
    ): Toolbar {
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(showBackButton)
            it.setDisplayShowHomeEnabled(showBackButton)
            it.setDisplayShowTitleEnabled(showTitle)
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
                this.smoothScrollToPosition(size)
            }, 400L
        )
    }

    @JvmStatic
    fun Context.shortHandler(DELAY: Long, todo: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(todo, DELAY)
    }

    @JvmStatic
    fun AppCompatActivity.hideSoftKeyboard() {
        val imm: InputMethodManager =
            this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(
                this.currentFocus?.windowToken,
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            )
        }
    }

    @JvmStatic
    fun View.setSnackBar(
        message: String,
        actionAvailable: Boolean = false,
        text: String = "",
        action: (() -> Unit)? = null
    ) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        if (actionAvailable) {
            snackBar.setAction(text) {
                action?.invoke()
            }
            snackBar.show()
        } else {
            snackBar.show()
        }
    }

    @JvmStatic
    fun Context.createBottomSheet(view: View): BottomSheetDialog {
        val bm = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bm.let {
            it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            it.setContentView(view)
            it.setOnCancelListener {
                bm.dismiss()
            }
            it.setOnDismissListener {
                bm.dismiss()
            }
        }
        return bm
    }

    @JvmStatic
    fun Context.showBottomSheet(bottomSheet: BottomSheetDialog) {
        bottomSheet.create()
        bottomSheet.show()
    }

}