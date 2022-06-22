package com.mentooutsourcing.iservice.model.produto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProdutoViewModelFactory(private val repository: ProdutoRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProdutoViewModel::class.java)) {
            return ProdutoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}