package com.ziemapp.johnzieman.crimeoffice

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ziemapp.johnzieman.crimeoffice.database.CrimeDatabase
import com.ziemapp.johnzieman.crimeoffice.database.migration_1_2
import com.ziemapp.johnzieman.crimeoffice.models.Crime
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime_db"

class CrimeRepository private constructor(context: Context) {

    private val database: CrimeDatabase = Room.databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
    ).addMigrations(migration_1_2).build()

    private val crimeDao = database.getCrimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)
    fun addCrimes(crimes: List<Crime>) {
        executor.execute {
            crimeDao.addCrimes(crimes)
        }
    }
    fun updateCrime(crime: Crime){
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }
    fun addCrime(crime: Crime){
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}