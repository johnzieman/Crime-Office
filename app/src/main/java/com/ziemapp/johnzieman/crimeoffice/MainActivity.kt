package com.ziemapp.johnzieman.crimeoffice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ziemapp.johnzieman.crimeoffice.databinding.ActivityMainBinding
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), Callbacks {
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host)
    }


    override fun onCrimeSelected(crimeId: UUID) {
        val action = CrimeListFragmentDirections.actionCrimeListFragment2ToCrimeFragment2(crimeId)
        navController.navigate(action)
    }
}