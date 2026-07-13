package com.example.fe.ui.todoform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentDashboardBinding
import com.example.fe.databinding.FragmentQuizzEsayDanProyekBinding
import com.example.fe.ui.todo.TodosViewModel
import com.example.fe.user
import kotlin.getValue

//item_esay = untuk esay dan proyek
//item_pilgan = untuk pilgan

class QuizzEsayDanProyekFragmen : Fragment() {
    lateinit var binding: FragmentQuizzEsayDanProyekBinding // ini tolong disesiuaikan di masing masing !!!
    private val viewModel: TodoFormViewModel by activityViewModels { TodoViewModelFactory }
    private lateinit var quizzAdapter: QuizzAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizzEsayDanProyekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // === Quiz hanya boleh diakses role student ===
        if (user?.role != "student") {
            binding.rvEssayQuestions.visibility = View.GONE
            binding.btnSubmit.visibility = View.GONE
            Toast.makeText(requireContext(), "Hanya siswa yang dapat mengerjakan kuis ini", Toast.LENGTH_LONG).show()
            return
        }

        setupRecyclerView()
        setupObservers()
        setupListeners()

        viewModel.loadQuiz()
    }

    private fun setupRecyclerView() {
        quizzAdapter = QuizzAdapter(
            onOptionSelected = { questionId, optionLetter ->
                viewModel.selectAnswer(questionId, optionLetter)
            },
            onEssayTextChanged = { text ->
                viewModel.setEssayFileUrl(text)
            }
        )
        binding.rvEssayQuestions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEssayQuestions.adapter = quizzAdapter
    }

    private fun setupObservers() {
        // === tidak ada progressBar di XML, jadi loading dipakai untuk nonaktifkan tombol submit saja ===
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnSubmit.isEnabled = !isLoading
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        // === tampilkan soal essay saat quiz berhasil dimuat dan question_type = essay ===
        viewModel.quiz.observe(viewLifecycleOwner) { quizdata ->
            if (quizdata == null) return@observe

            if (quizdata.question_type == "essay") {
                val essayItem = QuizListItem.Essay(
                    quizId = quizdata.id,
                    number = 1,
                    description = "" // TODO: isi dari sumber deskripsi tugas yang sesuai
                )
                quizzAdapter.submitList(listOf(essayItem))
            }
        }

        // === tampilkan soal pilihan ganda kalau question_type = multiple_choice ===
        viewModel.questions.observe(viewLifecycleOwner) { questionList ->
            if (questionList.isNotEmpty()) {
                val items = questionList.mapIndexed { index, q ->
                    QuizListItem.MultipleChoice(q, number = index + 1)
                }
                quizzAdapter.submitList(items)
            }
        }

        // === sinkronkan jawaban pilihan ganda yang sudah dipilih ke adapter ===
        viewModel.selectedAnswers.observe(viewLifecycleOwner) { answers ->
            quizzAdapter.updateSelectedAnswers(answers)
            quizzAdapter.notifyDataSetChanged()
        }

        // === sinkronkan input link essay ke adapter ===
        viewModel.essayFileUrl.observe(viewLifecycleOwner) { url ->
            quizzAdapter.updateEssayAnswerText(url)
        }

        // === saat submit berhasil ===
        viewModel.submitSuccess.observe(viewLifecycleOwner) { submission ->
            if (submission != null) {
                val scoreText = submission.score?.toString() ?: "menunggu penilaian guru"
                Toast.makeText(
                    requireContext(),
                    "Jawaban berhasil dikirim. Skor: $scoreText",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnSubmit.setOnClickListener {
            viewModel.submit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.reset()
    }
}
