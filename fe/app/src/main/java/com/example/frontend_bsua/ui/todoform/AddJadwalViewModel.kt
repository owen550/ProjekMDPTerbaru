package com.example.frontend_bsua.ui.todoform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_bsua.data.JadwalGlobal
import com.example.frontend_bsua.data.repositories.TodoRepository
import kotlinx.coroutines.launch

class AddJadwalViewModel(private val todoRepository: TodoRepository): ViewModel() {
    private var _loading = MutableLiveData<Boolean>(false)
    var loading: LiveData<Boolean> = _loading

    private var _message = MutableLiveData<String>("")
    var message: LiveData<String> = _message

    fun insertData(title: String,date:Long){
        if(title.isEmpty() || title.isBlank()){
            _message.value = "Isi Title !!!"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                var newJadwalGlobal = JadwalGlobal(
                    0,
                    title,
                    date,
                    "BelumSelesai",
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    null
                )
                var insertNewData = todoRepository.insert(newJadwalGlobal)
                _message.value = "Berhasil Menambahkan Jadwal !!!"
            }
            catch (e: Exception){
                _message.value = "Terjadi Kesalahan : ${e.message}"
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun updateData(id: Int,title: String,date: Long){
        viewModelScope.launch {
            _loading.value = true
            try {
                var cariJadwalLokal = todoRepository.getById(id)
                if(cariJadwalLokal != null){
                    var tanggalsekarang = System.currentTimeMillis()
                    var jadwalGlobal = JadwalGlobal(
                        cariJadwalLokal.JadwalId,
                        title,
                        date,
                        cariJadwalLokal.JadwalStatus,
                        cariJadwalLokal.createdAt,
                        tanggalsekarang,
                        cariJadwalLokal.deletedAt
                    )
                    todoRepository.update(jadwalGlobal)
                    _message.value = "Berhasil Update !!!"
                }
                else{
                    _message.value = "Gagal Update: ID Tidak Ditemukan !!!"
                }
            }
            catch (e: Exception){
                _message.value = e.message
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun reset(){
        _message.value = ""
    }

}