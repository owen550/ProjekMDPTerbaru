package com.example.fe

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fe.databinding.ActivityRegisterPageBinding
import com.example.fe.ui.todoform.TodoFormViewModel
import java.util.Calendar

class RegisterPage : AppCompatActivity() {

    private val viewModel: TodoFormViewModel by viewModels {
        TodoViewModelFactory
    }

    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    private fun onListener() {
        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val birthdayDate = binding.etBirthday.text.toString().trim()

            if (name.isEmpty() ||
                username.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                birthdayDate.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Isi Kredensial Dengan Benar !!!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Register
            viewModel.register(
                name,
                username,
                email,
                password,
                birthdayDate
            )

            // Reset Form
            binding.etName.setText("")
            binding.etUsername.setText("")
            binding.etEmail.setText("")
            binding.etPassword.setText("")
            binding.etBirthday.setText("")
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
                val date = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                binding.etBirthday.setText(date)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}