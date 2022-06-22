package com.mentooutsourcing.iservice.model.mesa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MesaViewModel(private val repository: MesaRepository): ViewModel() {

    private var _listaMesas = MutableLiveData<List<Mesa>>()
    val listaMesas: LiveData<List<Mesa>> get() = _listaMesas

    private var _erro = MutableLiveData<String>()
    val erro: LiveData<String> get() = _erro

    fun leMesas() {

        val resposta = repository.leMesas()

        resposta.enqueue(object: Callback<List<Mesa>>{
            override fun onResponse(call: Call<List<Mesa>>, response: Response<List<Mesa>>) {

                if (response.isSuccessful) {
                    _listaMesas.postValue(response.body())
                    _erro.postValue(response.message())
                } else {
                    _erro.postValue("O ocorreu um erro de acesso aos dados")
                    _listaMesas.postValue(ArrayList<Mesa>())
                }
            }

            override fun onFailure(call: Call<List<Mesa>>, t: Throwable) {
                _erro.postValue(t.message)
                _listaMesas.postValue(ArrayList<Mesa>())
            }

        })
    }
}