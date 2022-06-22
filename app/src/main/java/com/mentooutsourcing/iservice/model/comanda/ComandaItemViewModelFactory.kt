package com.mentooutsourcing.iservice.model.comanda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ComandaItemViewModelFactory(private val repository:
                                    ComandaItemRepository):
                                        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComandaItemViewModel::class.java)) {
            return ComandaItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Classe ViewModel Desconhecida")
    }

}