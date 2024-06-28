package com.example.diet_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.diet_application.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val bundle = bundleOf("login" to intent.getStringExtra("login"), "userID" to intent.getIntExtra("id", 0))

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController
//            .setGraph(R.navigation.mobile_navigation, intent.extras)

        CurrentUser.setId(intent.getIntExtra("id", 0))

        val navView: BottomNavigationView = binding.navView
//
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.mobile_navigation)
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when(destination.id) {
//                R.id.navigation_home -> {
//                    val argument = NavArgument.Builder().setDefaultValue(666).build()
//                    destination.addArgument("userId", argument)
//                }
//            }
//        }
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_products, R.id.navigation_profile
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        val navArgument1=NavArgument.Builder().setDefaultValue(intent.getIntExtra("id", 666)).build()
//        val navArgument2=NavArgument.Builder().setDefaultValue(intent.getStringExtra("login")).build()
//        graph.addArgument("userId",navArgument1)
//        graph.addArgument("login",navArgument2)
        navController.graph=graph
        navView.setupWithNavController(navController)
    }
}