package io.github.mklkj.filmowy.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FragmentLifecycleLogger @Inject constructor() : FragmentManager.FragmentLifecycleCallbacks() {

    private fun checkSavedState(savedInstanceState: Bundle?) = if (savedInstanceState == null) "(STATE IS NULL)" else ""

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        Timber.d("${f::class.java.simpleName} VIEW CREATED ${checkSavedState(savedInstanceState)}")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} STOPPED")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        Timber.d("${f::class.java.simpleName} CREATED ${checkSavedState(savedInstanceState)}")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} RESUMED")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        Timber.d("${f::class.java.simpleName} ATTACHED")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} DESTROYED")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        Timber.d("${f::class.java.simpleName} SAVED INSTANCE STATE")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} STARTED")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} VIEW DESTROYED")
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        Timber.d("${f::class.java.simpleName} ACTIVITY CREATED ${checkSavedState(savedInstanceState)}")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} PAUSED")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        Timber.d("${f::class.java.simpleName} DETACHED")
    }
}
