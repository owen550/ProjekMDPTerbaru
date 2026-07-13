package com.example.fe.ui.todoform

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.QuestionWithOptions
import com.example.fe.databinding.ItemEsayBinding
import com.example.fe.databinding.ItemPilganBinding

// === representasi item di dalam RecyclerView ===
sealed class QuizListItem {
    data class MultipleChoice(
        val questionWithOptions: QuestionWithOptions,
        val number: Int
    ) : QuizListItem()

    data class Essay(
        val quizId: Int,
        val number: Int,
        val description: String
    ) : QuizListItem()
}

class QuizzAdapter(
    private val onOptionSelected: (questionId: Int, optionLetter: String) -> Unit,
    private val onEssayTextChanged: (text: String) -> Unit
) : ListAdapter<QuizListItem, RecyclerView.ViewHolder>(QuizDiffCallback()) {

    private var selectedAnswers: Map<Int, String> = emptyMap()
    private var essayAnswerText: String = ""

    fun updateSelectedAnswers(answers: Map<Int, String>) {
        selectedAnswers = answers
    }

    fun updateEssayAnswerText(text: String) {
        essayAnswerText = text
    }

    companion object {
        private const val VIEW_TYPE_PILGAN = 1
        private const val VIEW_TYPE_ESSAY = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuizListItem.MultipleChoice -> VIEW_TYPE_PILGAN
            is QuizListItem.Essay -> VIEW_TYPE_ESSAY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_PILGAN -> {
                val binding = ItemPilganBinding.inflate(inflater, parent, false)
                PilganViewHolder(binding, onOptionSelected)
            }
            VIEW_TYPE_ESSAY -> {
                val binding = ItemEsayBinding.inflate(inflater, parent, false)
                EssayViewHolder(binding, onEssayTextChanged)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is QuizListItem.MultipleChoice -> {
                val selectedLetter = selectedAnswers[item.questionWithOptions.question.id]
                (holder as PilganViewHolder).bind(item, selectedLetter)
            }
            is QuizListItem.Essay -> {
                (holder as EssayViewHolder).bind(item, essayAnswerText)
            }
        }
    }

    // ============================================================
    // ViewHolder untuk soal pilihan ganda (item_pilgan.xml)
    // ============================================================
    class PilganViewHolder(
        private val binding: ItemPilganBinding,
        private val onOptionSelected: (questionId: Int, optionLetter: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuizListItem.MultipleChoice, selectedLetter: String?) {
            val question = item.questionWithOptions.question

            // === jaga-jaga dari data kotor, hanya ambil opsi A/B/C/D ===
            val validOptions = item.questionWithOptions.options
                .filter { it.option_letter in listOf("A", "B", "C", "D") }
            val optionsMap = validOptions.associateBy { it.option_letter }

            binding.txtPilganNumber.text = "Pertanyaan ${item.number}:"
            binding.txtPilganQuestion.text = question.question_text

            val radioButtons = mapOf(
                "A" to binding.rbOptionA,
                "B" to binding.rbOptionB,
                "C" to binding.rbOptionC,
                "D" to binding.rbOptionD
            )

            // === lepas listener dulu supaya set isChecked di bawah tidak memicu callback salah ===
            binding.rgOptions.setOnCheckedChangeListener(null)

            radioButtons.forEach { (letter, radioButton) ->
                val option = optionsMap[letter]
                if (option != null) {
                    radioButton.visibility = View.VISIBLE
                    radioButton.text = "$letter. ${option.option_text}"
                } else {
                    radioButton.visibility = View.GONE
                }
            }

            // === RadioGroup otomatis memastikan cuma 1 pilihan aktif per soal ===
            binding.rgOptions.clearCheck()
            radioButtons[selectedLetter]?.isChecked = true

            binding.rgOptions.setOnCheckedChangeListener { _, checkedId ->
                val letter = radioButtons.entries.firstOrNull { it.value.id == checkedId }?.key
                if (letter != null) {
                    onOptionSelected(question.id, letter)
                }
            }
        }
    }

    // ============================================================
    // ViewHolder untuk tugas esai (item_esay.xml)
    // ============================================================
    class EssayViewHolder(
        private val binding: ItemEsayBinding,
        private val onEssayTextChanged: (text: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentWatcher: TextWatcher? = null

        fun bind(item: QuizListItem.Essay, currentText: String) {
            binding.txtQuestionNumber.text = "Soal ${item.number}"
            binding.txtEssayQuestion.text = item.description

            // === lepas watcher lama supaya tidak dobel/salah trigger saat recycle ===
            currentWatcher?.let { binding.etEssayInput.removeTextChangedListener(it) }

            if (binding.etEssayInput.text.toString() != currentText) {
                binding.etEssayInput.setText(currentText)
            }

            val watcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    onEssayTextChanged(s.toString())
                }
            }
            binding.etEssayInput.addTextChangedListener(watcher)
            currentWatcher = watcher
        }
    }

    class QuizDiffCallback : DiffUtil.ItemCallback<QuizListItem>() {
        override fun areItemsTheSame(oldItem: QuizListItem, newItem: QuizListItem): Boolean {
            return when {
                oldItem is QuizListItem.MultipleChoice && newItem is QuizListItem.MultipleChoice ->
                    oldItem.questionWithOptions.question.id == newItem.questionWithOptions.question.id
                oldItem is QuizListItem.Essay && newItem is QuizListItem.Essay ->
                    oldItem.quizId == newItem.quizId
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: QuizListItem, newItem: QuizListItem): Boolean {
            return oldItem == newItem
        }
    }
}