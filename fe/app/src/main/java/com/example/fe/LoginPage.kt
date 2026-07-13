package com.example.fe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityLoginPageBinding
 import com.example.fe.ui.todoform.TodoFormViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import kotlin.getValue
import android.util.Log

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    private val viewModel: TodoFormViewModel by viewModels {
        TodoViewModelFactory
    }

    // Inisialisasi properti Credential Manager
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instansiasi Credential Manager
        credentialManager = CredentialManager.create(this)

        // === trigger pencarian lokal di sini lek wes jadi ===
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
            if (login == true) {
                // loginin dia
                viewModel.cekStatusPremiFreeUser()

                // pindah halaman
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Menutup login page agar tidak bisa di-back

                // reset
                viewModel.reset()
                viewModel.getUserTerakhir()
            }
        }
        viewModel.userlokal.observe(this) { lokal ->
            if (lokal != null) {
                binding.etLoginIdentifier.setText(lokal.username)
                binding.etLoginPassword.setText(lokal.password)
            }
        }
    }

    fun onListener() {
        binding.btnLogin.setOnClickListener {
            // langsung cek order id klo ada
            viewModel.rechekPayment()

            // coba login
            val emailOrUser = binding.etLoginIdentifier.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (emailOrUser.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi Kredensial Dengan Benar !!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.doLogin(emailOrUser, password)

            binding.etLoginIdentifier.setText("")
            binding.etLoginPassword.setText("")
        }

        // LISTENER UNTUK TOMBOL GOOGLE SIGN-IN MENGGUNAKAN CREDENTIAL MANAGER
        binding.btnGoogleLogin.setOnClickListener {
            processGoogleAuth()
        }

        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
        }
    }

    // Fungsi Utama Metode Credential Manager & OAuth 2.0
    private fun processGoogleAuth() {
        // 1. Konfigurasi Google ID Option (OAuth 2.0)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id)) // Web Client ID dari Google Console
            .setFilterByAuthorizedAccounts(false) // Set 'false' agar pengguna bisa mendaftar dengan akun baru secara seamless
            .setAutoSelectEnabled(false) // Menampilkan pemilih akun (Account Chooser)
            .build()

        // 2. Bungkus Opsi ke dalam GetCredentialRequest
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        // 3. Panggil dialog Credential Manager di dalam Lifecycle Scope
        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(this@LoginPage, request)
                val credential = result.credential

                // 4. Ekstrak Token JWT jika tipe kredensialnya adalah Google ID Token
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    // Kirim OAuth 2.0 Token (JWT) ini ke backend melalui ViewModel kamu
                    viewModel.loginWithGoogle(idToken)
                }
            } catch (e: GetCredentialException) {
                // Menangani jika user membatalkan dialog login atau error lainnya
                Log.e("GOOGLE_AUTH_ERROR", "Pesan Error: ${e.message}", e)
                Toast.makeText(this@LoginPage, "Gagal: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}