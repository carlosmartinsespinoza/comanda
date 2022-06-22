package com.mentooutsourcing.iservice.model.comanda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mentooutsourcing.iservice.model.mesa.Mesa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComandaItemViewModel(private val repository:
                            ComandaItemRepository): ViewModel() {

    private var _listaComandaItem = MutableLiveData<List<ComandaItem>>()
    val listaComandaItem: LiveData<List<ComandaItem>> get() = _listaComandaItem

    private val _mensagemErro = MutableLiveData<String>()
    val mensagemErro: LiveData<String> get() = _mensagemErro

    private var _mesa = MutableLiveData<List<Mesa>>()
    val mesa: LiveData<List<Mesa>> get() = _mesa

    private val _mensagemLeituraMesa = MutableLiveData<String>()
    val mensagemLeituraMesa: LiveData<String> get() = _mensagemLeituraMesa

    fun leComandaItem(comandaId: String) {

        val response = repository.leComandaItem(comandaId)
        response.enqueue(object: Callback<List<ComandaItem>> {
            override fun onResponse(
                call: Call<List<ComandaItem>>,
                response: Response<List<ComandaItem>>
            ) {
                if (response.isSuccessful) {
                    _listaComandaItem.postValue(response.body())
                    _mensagemErro.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<List<ComandaItem>>, t: Throwable) {
                _mensagemErro.postValue(t.message)
                _listaComandaItem.postValue(ArrayList<ComandaItem>())
            }

        })
    }
    fun leDadosMesa(mesaId: String) {
        val response = repository.leDadosMesa(mesaId)
        response.enqueue(object: Callback<List<Mesa>>{
            override fun onResponse(call: Call<List<Mesa>>, response: Response<List<Mesa>>) {
                if (response.isSuccessful) {
                    _mesa.postValue(response.body())
                    _mensagemLeituraMesa.postValue(response.message())
                } else {
                    _mensagemLeituraMesa.postValue("Ocorreu um Erro ao Ler Dados da Mesa")
                    _mesa.postValue(ArrayList<Mesa>())
                }
            }
            override fun onFailure(call: Call<List<Mesa>>, t: Throwable) {
                _mensagemLeituraMesa.postValue(t.message)
                _mesa.postValue(ArrayList<Mesa>())
            }
        })
    }
}