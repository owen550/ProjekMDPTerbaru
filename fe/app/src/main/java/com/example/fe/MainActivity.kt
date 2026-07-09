package com.example.fe

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        
        setupRoleBasedUI()
        
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupRoleBasedUI() {
        val currentUser = user ?: return
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        when (currentUser.role.lowercase()) {
            "admin" -> {
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.inflateMenu(R.menu.admin_menu)
                applyColorTheme(R.color.admin_green_primary, R.color.admin_green_secondary)
                navGraph.setStartDestination(R.id.adminPaymentFragment)
            }
            "teacher" -> {
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.inflateMenu(R.menu.teacher_menu)
                applyColorTheme(R.color.admin_green_primary, R.color.admin_green_secondary)
                navGraph.setStartDestination(R.id.dashboardFragmen)
            }
            "student" -> {
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.inflateMenu(R.menu.student_menu)
                applyColorTheme(R.color.user_orange_primary, R.color.user_orange_secondary)
                navGraph.setStartDestination(R.id.dashboardFragmen)
            }
        }
        navController.graph = navGraph
    }

    private fun applyColorTheme(primaryColorRes: Int, secondaryColorRes: Int) {
        val primaryColor = ContextCompat.getColor(this, primaryColorRes)
        val secondaryColor = ContextCompat.getColor(this, secondaryColorRes)
        
        binding.bottomNavigationView.setBackgroundColor(secondaryColor)
        
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )
        
        val colors = intArrayOf(
            primaryColor,
            ContextCompat.getColor(this, R.color.gray_text)
        )
        
        val colorStateList = ColorStateList(states, colors)
        binding.bottomNavigationView.itemIconTintList = colorStateList
        binding.bottomNavigationView.itemTextColor = colorStateList
    }
}
