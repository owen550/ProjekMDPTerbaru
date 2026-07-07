package com.example.fe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fe.databinding.ActivityLoginPageBinding
import com.example.fe.databinding.ActivityRegisterPageBinding
import kotlinx.coroutines.selects.RegistrationFunction

class RegisterPage : AppCompatActivity() {
    lateinit var binding: ActivityRegisterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // semua kode taruh di bawah !!!



    }
}