package com.mentooutsourcing.iservice.model.produto

import com.mentooutsourcing.iservice.model.comanda.ComandaItem
import com.mentooutsourcing.iservice.model.mesa.Mesa
import com.mentooutsourcing.iservice.network.RetrofitService


class ProdutoRepository(private val retrofit: RetrofitService) {

    fun getProdutos() = retrofit.getProdutos()

    fun incluirProdutoComanda(comandaItem: ComandaItem) =
        retrofit.incluirComandaItem(comandaItem)
}