package com.ziemapp.johnzieman.crimeoffice

import android.app.Application

class CrimeOfficeApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}