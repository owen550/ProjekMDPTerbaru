package com.example.fe

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityRegisterPageBinding
import com.example.fe.ui.todoform.TodoFormViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import android.util.Log
import java.util.Calendar

class RegisterPage : AppCompatActivity() {
    private val viewModel: TodoFormViewModel by viewModels {
        TodoViewModelFactory
    }

    private lateinit var binding: ActivityRegisterPageBinding

    // Inisialisasi properti Credential Manager
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instansiasi Credential Manager
        credentialManager = CredentialManager.create(this)

        onObserve()
        onListener()

        // Tampilkan DatePicker saat EditText diklik
        binding.etBirthday.setOnClickListener {
            showDatePicker()
        }
    }

    private fun onObserve() {
        viewModel.message.observe(this) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }

        // TAMBAHAN OBSERVER: Jika registrasi Google sukses, langsung arahkan ke MainActivity
        viewModel.login.observe(this) { login ->
            if (login == true) {
                viewModel.cekStatusPremiFreeUser()

                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity() // Hapus tumpukan login & register dari memori

                viewModel.reset()
                viewModel.getUserTerakhir()
            }
        }
    }

    private fun onListener() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val birthdayDate = binding.etBirthday.text.toString().trim()

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || birthdayDate.isEmpty()) {
                Toast.makeText(this, "Isi Kredensial Dengan Benar !!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register Manual
            viewModel.register(name, username, email, password, birthdayDate)

            // Reset Form
            binding.etName.setText("")
            binding.etUsername.setText("")
            binding.etEmail.setText("")
            binding.etPassword.setText("")
            binding.etBirthday.setText("")
        }

        // LISTENER UNTUK TOMBOL GOOGLE REGISTER MENGGUNAKAN CREDENTIAL MANAGER
        binding.btnGoogleRegister.setOnClickListener {
            processGoogleAuth()
        }

        // back ke login
        binding.tvLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }
    }

    // Fungsi Utama Metode Credential Manager & OAuth 2.0 (Sama seperti di Login Page)
    private fun processGoogleAuth() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false) // Agar user baru bisa klik & daftar instan
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(this@RegisterPage, request)
                val credential = result.credential

                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    // Mengirimkan Token ke fungsi loginWithGoogle di ViewModel
                    viewModel.loginWithGoogle(idToken)
                }
            } catch (e: GetCredentialException) {
                Log.e("GOOGLE_AUTH_ERROR", "Pesan Error: ${e.message}", e)
                Toast.makeText(this@RegisterPage, "Gagal: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.etBirthday.setText(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}