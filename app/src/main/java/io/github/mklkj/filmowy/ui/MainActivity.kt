package io.github.mklkj.filmowy.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
