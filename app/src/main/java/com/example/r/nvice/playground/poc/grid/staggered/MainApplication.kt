package com.example.r.nvice.playground.poc.grid.staggered

import android.app.Application
import timber.log.Timber

class MainApplication: Application() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}