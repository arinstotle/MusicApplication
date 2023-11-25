package com.example.musicapplication.chatioandroid.extensions

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.example.musicapplication.chatioandroid.data.model.response.ApiResponse
import com.example.musicapplication.chatioandroid.utils.DataState
import com.example.musicapplication.chatioandroid.utils.livedata.Event
import com.example.musicapplication.chatioandroid.utils.livedata.EventObserver

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.createMenu(@MenuRes menuRes: Int, onItemSelected: (Int) -> Unit) {
    val menuHost: MenuHost = requireActivity()

    menuHost.addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onItemSelected(menuItem.itemId)
            return true
        }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}
//
//fun <T> Fragment.observeLiveData(
//    liveData: LiveData<DataState<ApiResponse<T>>>,
//    showProgress: Boolean = true,
//    progressBar: ProgressBar? = null,
//    swipeRefreshLayout: SwipeRefreshLayout? = null,
//    onSuccess: (T) -> Unit
//) {
//    liveData.observe(viewLifecycleOwner) { response ->
//        when (response) {
//            is DataState.Error -> {
//                progressBar?.isVisible = false
//                toast(response.message)
//
//                Timber.tag(TAG).d(response.message)
//
//            }
//            is DataState.Success -> {
//                progressBar?.isVisible = false
//                onSuccess(response.data.data)
//            }
//
//            is DataState.Loading -> {
//                progressBar?.isVisible = showProgress
//                swipeRefreshLayout?.isRefreshing=false
//
//            }
//
//        }
//
//    }
//}

fun <T> Fragment.observeLiveDataEvent(
    liveData: LiveData<Event<DataState<ApiResponse<T>>>>,
    showProgress: Boolean = true,
    progressBar: ProgressBar? = null,
    onSuccess: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, EventObserver { response ->
        when (response) {
            is DataState.Error -> {
                progressBar?.isVisible = false
                toast(response.message)
            }
            is DataState.Success -> {
                progressBar?.isVisible = false
                onSuccess(response.data.data)
            }

            is DataState.Loading -> {
                progressBar?.isVisible = showProgress
            }

        }

    })
}


private const val TAG = "FragmentExt"

