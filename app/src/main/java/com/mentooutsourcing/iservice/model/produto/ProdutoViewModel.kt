package com.mentooutsourcing.iservice.model.produto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mentooutsourcing.iservice.model.comanda.ComandaItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoViewModel(private val repository: ProdutoRepository):
                                                    ViewModel() {

    private val _listaProdutos = MutableLiveData<List<Produto>>()
    val listaProdutos: LiveData<List<Produto>> get() = _listaProdutos

    val mensagem = MutableLiveData<String?>()
    val mensagemIncluirItemComanda = MutableLiveData<String>()

    private val _idcomanda = MutableLiveData<Int>()
    val idcomanda: LiveData<Int> get() = _idcomanda


    fun leProdutos() {

        val resposta = repository.getProdutos()

        resposta.enqueue(object: Callback<List<Produto>>{
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    _listaProdutos.postValue(response.body())
                } else {
                    mensagem.postValue(response.raw().body.toString())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                mensagem.postValue(t.message)
            }

        })
    }
    fun incluirProdutoComanda(comandaItem: ComandaItem,
                           onResult: (ComandaItem?) -> Unit) {

        val response = repository.incluirProdutoComanda(comandaItem)

        response.enqueue(object: Callback<ComandaItem>{

            override fun onResponse(
                call: Call<ComandaItem>,
                response: Response<ComandaItem>
            ) {
                if (response.isSuccessful) {
                    val resposta = response.body()
                    mensagemIncluirItemComanda.value = "${resposta!!.nomeProduto} Incluído"
                    _idcomanda.value = resposta.comandaId
                    onResult(response.body())
                }
            }
            override fun onFailure(call: Call<ComandaItem>, t: Throwable) {
                mensagemIncluirItemComanda.postValue(t.message)
                onResult(null)
            }
        })
    }
}