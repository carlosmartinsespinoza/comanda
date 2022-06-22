package com.mentooutsourcing.iservice.model.mesa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mentooutsourcing.iservice.network.RetrofitService

class MesaViewModelFactory(private val repository: MesaRepository):
                        ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MesaViewModel::class.java)) {
            return MesaViewModel(repository) as T
        }
        throw IllegalArgumentException("Classe ViewModel Desconhecida")
    }
}