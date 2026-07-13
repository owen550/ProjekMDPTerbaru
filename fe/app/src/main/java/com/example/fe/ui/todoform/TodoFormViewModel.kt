package com.example.fe.ui.todoform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.data.Course
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.data.source.local.UserEntity
import com.example.fe.orderid
import com.example.fe.statusUser
import com.example.fe.user
import kotlinx.coroutines.launch
import com.example.fe.data.QuestionWithOptions
import com.example.fe.courseTopic
import com.example.fe.data.QuizQuestion
import com.example.fe.data.QuizQuestionOption
import com.example.fe.data.Quizzes
import com.example.fe.data.StudentSubmission

var currentUserId: Int? = null

class TodoFormViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _login = MutableLiveData<Boolean>(false)
    val login: LiveData<Boolean> = _login

    private val _userlokal = MutableLiveData<User>()
    val userlokal: LiveData<User> = _userlokal

    private val _quiz = MutableLiveData<Quizzes?>(null)
    val quiz: LiveData<Quizzes?> = _quiz

    private val _questions = MutableLiveData<List<QuestionWithOptions>>(emptyList())
    val questions: LiveData<List<QuestionWithOptions>> = _questions

    private val _selectedAnswers = MutableLiveData<Map<Int, String>>(emptyMap())
    val selectedAnswers: LiveData<Map<Int, String>> = _selectedAnswers

    private val _essayFileUrl = MutableLiveData<String>("")
    val essayFileUrl: LiveData<String> = _essayFileUrl

    private val _submitSuccess = MutableLiveData<StudentSubmission?>(null)
    val submitSuccess: LiveData<StudentSubmission?> = _submitSuccess

    fun updateUser(
        name: String,
        username: String,
    ){
        _loading.value = true
        viewModelScope.launch {
            try {
                val updatedUsers = user?.copy(
                    username = username,
                    name = name
                )
                var result = todoRepository.updateUser(updatedUsers!!)
                    .onSuccess {
                        user = updatedUsers // ganti update usernya
                        _message.value = "Berhasil Mengupate User"
                    }
                    .onFailure { error ->
                        _message.value = "Gagal : " + error.message
                    }
            } catch (e: Exception){
                _message.value = "" // jangan munculin apa apa
            }
            finally {
                _loading.value = false
            }
        }
    }
    fun getUserTerakhir(){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.getLastUserDESC()
                if(result != null){ // kalau ngak kosonh
                    _userlokal.value = result
                }
            } catch (e: Exception){
                _message.value = "" // jangan munculin apa apa
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun doLogin(usernameoremail:String, password:String){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.doLogin(usernameoremail,password)

                result
                    .onSuccess { userdata ->
                        // lakukan login
                        user = userdata
                        currentUserId = userdata.id
                        _login.value = true
                    }
                    .onFailure {
                        _message.value = "Username/Email atau Password Salah !!!"
                    }
            } catch (e: Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun rechekPayment() {
        // 1. Langsung ambil dari properti class (misal bernama 'orderid')
        // Gunakan safe let untuk memastikan dia tidak null dan tidak kosong
        val currentOrderId = orderid

        if (!currentOrderId.isNullOrEmpty()) {
            Log.d("tes", "Isi Order ID: $currentOrderId")
            _loading.value = true

            viewModelScope.launch {
                try {
                    val result = todoRepository.rechekPayment(currentOrderId)
                    result
                        .onSuccess { status ->
                            Log.d("tes", "Status transaksi saat ini: $status")
                        }
                        .onFailure {
                            Log.e("tes", "Gagal recheck status")
                        }
                } catch (e: Exception) {
                    Log.e("tes", "Error: ${e.message}")
                } finally {
                    _loading.value = false
                }
            }
        } else {
            Log.d("tes", "Order ID ternyata null atau kosong!")
        }
    }

    fun cekStatusPremiFreeUser(){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.cekStatusFreePre(user!!.id!!.toInt())
                result
                    .onSuccess { userdata ->
                        statusUser = userdata // kalau false berarti free, kalau true berarti premi
                    }
                    .onFailure {
                        statusUser = false
                    }
            } catch (e: Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun register(
        name:String,
        username:String,
        email:String,
        password:String,
        birthdayDate:String
    ){

        val userBaru = User(
            id = 0,
            name = name,
            username = username,
            email = email,
            password = password,
            birthday_date = birthdayDate,
            role = "student",
            tier = "free",
            google_id = "",
            created_at = "",
            updated_at = "",
            deleted_at = "",
            points = 0
        )

        _loading.value = true

        viewModelScope.launch {
            try {

                val result = todoRepository.addUser(userBaru)

                result
                    .onSuccess { userdata ->
                        user = userdata
                        _message.value = "User berhasil register"
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal register"
                    }

            } catch(e:Exception){
                _message.value = e.message
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.doGoogleAuth(idToken)
                result
                    .onSuccess { userdata ->
                        user = userdata
                        currentUserId = userdata.id
                        _login.value = true
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Google Sign-In Gagal!"
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Koneksi Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadQuiz(){
        val currentUser = user
        val topic = courseTopic

        if (currentUser == null || currentUserId == null) {
            _message.value = "Sesi login tidak ditemukan, silakan login ulang"
            return
        }

        if (topic == null) {
            _message.value = "Topic belum dipilih"
            return
        }

        if (currentUser.role != "student") {
            _message.value = "Hanya siswa yang dapat mengerjakan kuis ini"
            return
        }

        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.getQuizById(currentUserId!!, topic.id)
                result
                    .onSuccess { quizdata ->
                        _quiz.value = quizdata

                        if (quizdata.question_type == "multiple_choice") {
                            loadQuestionsAndOptions(currentUserId!!, quizdata.id)
                        } else {
                            _loading.value = false
                        }
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal memuat kuis"
                        _loading.value = false
                    }
            } catch (e: Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
                _loading.value = false
            }
        }
    }

    private suspend fun loadQuestionsAndOptions(userId: Int, quizId: Int){
        try {
            val questionResult = todoRepository.getAllQuestions(userId, quizId)
            questionResult
                .onSuccess { quizQuestions ->
                    viewModelScope.launch {
                        val optionResult = todoRepository.getAllOptions(userId, quizId)
                        optionResult
                            .onSuccess { allOptions ->
                                val questionsWithOptions = quizQuestions.map { question ->
                                    QuestionWithOptions(
                                        question = question,
                                        options = allOptions
                                            .filter { it.quiz_question_id == question.id }
                                            .sortedBy { it.option_letter }
                                    )
                                }
                                _questions.value = questionsWithOptions
                                _loading.value = false
                            }
                            .onFailure { error ->
                                _message.value = error.message ?: "Gagal memuat opsi jawaban"
                                _loading.value = false
                            }
                    }
                }
                .onFailure { error ->
                    _message.value = error.message ?: "Gagal memuat soal"
                    _loading.value = false
                }
        } catch (e: Exception){
            _message.value = "Terjadi Kesalahan Pada Backend"
            _loading.value = false
        }
    }

    fun selectAnswer(questionId: Int, optionLetter: String){
        val updated = _selectedAnswers.value.orEmpty().toMutableMap()
        updated[questionId] = optionLetter
        _selectedAnswers.value = updated
    }

    fun setEssayFileUrl(url: String){
        _essayFileUrl.value = url
    }

    fun submit(){
        val currentUser = user
        val quizdata = _quiz.value

        if (currentUser == null || currentUserId == null) {
            _message.value = "Sesi login tidak ditemukan, silakan login ulang"
            return
        }

        if (quizdata == null) {
            _message.value = "Kuis belum dimuat"
            return
        }

        _loading.value = true
        viewModelScope.launch {
            try {
                val result = if (quizdata.question_type == "multiple_choice") {
                    submitMultipleChoice(quizdata, currentUserId!!)
                } else {
                    submitEssay(quizdata, currentUserId!!)
                }

                result
                    .onSuccess { submission ->
                        _submitSuccess.value = submission
                        _message.value = "Jawaban berhasil dikirim"
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal mengirim jawaban"
                    }
            } catch (e: Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    // === Pilihan ganda: dinilai OTOMATIS, bandingkan dengan correct_answer (huruf opsi) ===
    private suspend fun submitMultipleChoice(quizdata: Quizzes, userId: Int): Result<StudentSubmission> {
        val currentQuestions = _questions.value.orEmpty()
        val currentAnswers = _selectedAnswers.value.orEmpty()
        val totalQuestions = currentQuestions.size

        if (totalQuestions == 0) {
            return Result.failure(Exception("Tidak ada soal untuk dinilai"))
        }

        var correctCount = 0
        currentQuestions.forEach { qWithOptions ->
            val studentAnswer = currentAnswers[qWithOptions.question.id]
            val correctAnswer = qWithOptions.question.correct_answer
            if (studentAnswer != null && correctAnswer != null && studentAnswer.equals(correctAnswer, ignoreCase = true)) {
                correctCount++
            }
        }

        val score = ((correctCount.toDouble() / totalQuestions.toDouble()) * 100).toInt()

        return todoRepository.insertSubmission(
            userId = userId,
            quizId = quizdata.id,
            studentId = userId,
            essayAnswer = null,
            fileUrl = null,
            score = score,
            teacherComment = null,
            status = "graded"
        )
    }

    // === Essay: siswa kirim link/url tugas, guru menilai belakangan ===
    private suspend fun submitEssay(quizdata: Quizzes, userId: Int): Result<StudentSubmission> {
        val fileUrl = _essayFileUrl.value.orEmpty()

        if (fileUrl.isBlank()) {
            return Result.failure(Exception("Link tugas belum diisi"))
        }

        return todoRepository.insertSubmission(
            userId = userId,
            quizId = quizdata.id,
            studentId = userId,
            essayAnswer = null,
            fileUrl = fileUrl,
            score = null,
            teacherComment = null,
            status = "submitted"
        )
    }

    fun reset(){
        _message.value = ""
        _login.value = false
        _loading.value = false
        _quiz.value = null
        _questions.value = emptyList()
        _selectedAnswers.value = emptyMap()
        _essayFileUrl.value = ""
        _submitSuccess.value = null
    }
}