package com.mentooutsourcing.iservice.model.comanda
import com.mentooutsourcing.iservice.network.RetrofitService


class ComandaItemRepository(private val retrofit: RetrofitService) {

    fun leComandaItem(comandaId: String)
            = retrofit.getComandaItem(comandaId)
    fun leDadosMesa(mesaId: String) = retrofit.leMesa(mesaId)
}