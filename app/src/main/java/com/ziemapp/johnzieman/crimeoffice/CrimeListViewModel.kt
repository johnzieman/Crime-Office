package com.ziemapp.johnzieman.crimeoffice

import androidx.lifecycle.ViewModel
import com.ziemapp.johnzieman.crimeoffice.models.Crime

class CrimeListViewModel: ViewModel() {
    private val crimeRepository = CrimeRepository.get()
//
//    val crimes = mutableListOf<Crime>()
//    init{
//        for(i in 0..3){
//            val crime = Crime()
//            crime.title = "Crime #$i"
//            crime.isSolved = i % 2 == 0
//            crimes.add(crime)
//        }
//        crimeRepository.addCrimes(crimes)
//    }

    val crimeListLiveData = crimeRepository.getCrimes()

    fun addCrime(crime:Crime) {
        crimeRepository.addCrime(crime)
    }
}