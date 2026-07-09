package com.example.fe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
 import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fe.databinding.ActivityLoginPageBinding
 import com.example.fe.ui.todoform.TodoFormViewModel
import kotlin.getValue

class LoginPage : AppCompatActivity() {

    lateinit var binding: ActivityLoginPageBinding
     private val viewModel: TodoFormViewModel by viewModels {
         TodoViewModelFactory
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // === trigger pencarianb lokal di sini lek wes jadi ===
        viewModel.getUserTerakhir()

        // === observe dan listener ====
        onObserve()
        onListener()
    }

    fun onObserve() {
         viewModel.message.observe(this) { msg ->
             if (!msg.isNullOrEmpty()) {
                 Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
             }
         }
         viewModel.login.observe(this) { login ->
            if(login == true){
                // loginin dia
                startActivity(Intent(this, MainActivity::class.java))
                // reset
                viewModel.reset()
                viewModel.getUserTerakhir()
            }
         }
        viewModel.userlokal.observe(this) { lokal ->
            if(lokal != null){
                binding.etLoginIdentifier.setText(lokal.username)
                binding.etLoginPassword.setText(lokal.password)
            }
        }


    }

    fun onListener() {
        binding.btnLogin.setOnClickListener {
            val emailOrUser = binding.etLoginIdentifier.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (emailOrUser.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Isi Kredensial Dengan Benar !!!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.doLogin(emailOrUser, password)

            binding.etLoginIdentifier.setText("")
            binding.etLoginPassword.setText("")
        }

        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
        }
    }
}